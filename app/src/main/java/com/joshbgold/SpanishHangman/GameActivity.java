package com.joshbgold.SpanishHangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends MainActivity {

    private String word = "";  //stores the mystery word the user is trying to guess
    private String englishDef = "";
    private char wordChars[];
    private String hiddenWord = ""; //Shows the number of characters to the user to aid in guessing
    private char hiddenWordChars[];
    private String userGuess =""; //stores user's gueseed letter
    char[] userGuessChar;
    private String guessedletters = ""; //stores ALL user's guessed letters
    private int guessesRemaining = 7;
    private int wordIndex = 0;  //used to help retrieve random word from the arrays
    boolean match = false;
    private int wordListChoice = 0; //choose either verb list (0), or nouns list (1)
    private String userWordGuess = "";  //user can guess puzzle solution early, instead of 1 letter at a time
    
    private int wins = 0;
    private int totalAttempts = 0;
    private int win_streak = 0;
    private int ten_game_record[];

    private TextView someAnswer;
    private Button checkButton;
    private Button hintButton;
    private Button solveButton;
    private Button exitButton;
    private EditText guessText;
    private ImageView skelatonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        someAnswer = (TextView) findViewById(R.id.answerText);
        checkButton = (Button) findViewById(R.id.submitGuessButton);
        hintButton = (Button) findViewById(R.id.hintButton);
        solveButton = (Button) findViewById(R.id.solveButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        guessText = (EditText) findViewById(R.id.letterGuess);
        skelatonImage = (ImageView) findViewById(R.id.skeleton);

        wordListChoice = LoadPreferences("choice", wordListChoice);

        playGame();

        //check Word button
        View.OnClickListener checkWord = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        };

        //get a hint button
        View.OnClickListener getHint = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEnglish();
            }
        };

        //try to solve the puzzle
        View.OnClickListener solve = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkIfSolved();
            }
        };

       //exit the application when exit button is pressed
       View.OnClickListener salida = new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       };

       checkButton.setOnClickListener(checkWord);
       hintButton.setOnClickListener(getHint);
        solveButton.setOnClickListener(solve);
       exitButton.setOnClickListener(salida);
   }

    void playGame(){

        WordList wordList = new WordList();

        totalAttempts++; //used to calculate winning percentage

        //get a random integer less than the length of the list of words
        if(wordListChoice == 0) {
            wordIndex = wordList.getWordIndex();
            word = wordList.getVerb(wordIndex);
            englishDef = wordList.getEnglishDef(wordIndex);
        }
        else{
            wordIndex = wordList.getCommonWordIndex();
            word = wordList.getCommonWords(wordIndex);
            englishDef = wordList.getCommonDefs(wordIndex);
        }

        //create a series of dashes or asterisks to represent the number of letters
        for (int i=0; i < word.length(); i++){
            hiddenWord += '*';
        }

        someAnswer.setText(hiddenWord);

        //For testing, will remove this line later
        //Toast.makeText(MainActivity.this, hiddenWord + " " + word, Toast.LENGTH_SHORT).show();
    }


    void resetImages(){
        if(guessesRemaining == 7){
            return;
        }
        else{
            ((BitmapDrawable) skelatonImage.getDrawable()).getBitmap().recycle();
            skelatonImage = (ImageView) findViewById(R.id.skeleton);
            skelatonImage.setImageResource(R.drawable.skeleton1);
        }
    }

    void resetGame(){
        word = "";
        hiddenWord = "";
        userGuess ="";
        match = false;
        guessesRemaining = 7;
        guessedletters = "";
        englishDef = "";
        wordIndex = 0;
        guessText.setHint("Type a letter here");
        guessText.setText("");
    }


    private void checkAnswer(){
        //take the user's letter guess
        userGuess = guessText.getText().toString().toLowerCase();

        userGuessChar = userGuess.toCharArray();
        wordChars = word.toCharArray();

        //checks if user entered a valid letter
        if (!isEmptyGuess(userGuess)){
            return;
        }

        if ((!isLetter(userGuess)) || (userGuess.length() > 1)){
            Toast.makeText(GameActivity.this, "Lo siento, your guess must be a single letter.", Toast.LENGTH_SHORT).show();
            guessText.setHint("Type a letter here");
            guessText.setText("");
            return;
        }

        //The next few lines handle accidental repeat guessesz
        if (guessedletters.contains(userGuess)) {
            Toast.makeText(GameActivity.this, "You already guessed that letter.",
                    Toast.LENGTH_SHORT).show();
            guessText.setHint("Type a letter here");
            guessText.setText("");
            return;
        }
        else {
            guessedletters = guessedletters + userGuess;
            //Toast.makeText(MainActivity.this, "These are the guessed letters:" + guessedletters,  Toast.LENGTH_SHORT).show();
        }

        for (int j = 0; j < word.length(); j++) {

            // see if word contains the matching letter, replace asterisks with any matches for the guessed letter
            if (word.contains(userGuess)) {
                hiddenWordChars = hiddenWord.toCharArray();
                if (wordChars[j] == userGuessChar[0]) {
                    hiddenWordChars[j] = userGuessChar[0];
                    hiddenWord = String.valueOf(hiddenWordChars);
                    someAnswer.setText(hiddenWord);
                }
                match = true;
            }
        }

        if (match){
            gameIsWon();
            userGuess="";
            match = false;
        }
        else {
            //decrement the guesses remaining
            guessesRemaining--;

            drawNewImage(); //updates the hanging skeleton image
            checkForLoss(); //checks if user is out of guesses

            Toast.makeText(GameActivity.this, "'" + userGuess + "' is not in the mystery " +
                    "word. You have " + guessesRemaining + " tries left to solve",
                    Toast.LENGTH_SHORT).show();
            userGuess="";
        }

        resetEditText();

    }

    private void drawNewImage(){
        skelatonImage = (ImageView) findViewById(R.id.skeleton);

        //draw a new image when a guess is wrong
        if (guessesRemaining == 6) {
            if(skelatonImage != null) {
                ((BitmapDrawable) skelatonImage.getDrawable()).getBitmap().recycle();
                skelatonImage = (ImageView) findViewById(R.id.skeleton);
                skelatonImage.setImageResource(R.drawable.skeleton2);
            }
            else {
                skelatonImage = (ImageView) findViewById(R.id.imageView);
                skelatonImage.setImageResource(R.drawable.skeleton1);
            }
        }

        else if (guessesRemaining == 5) {
            if(skelatonImage != null) {
                ((BitmapDrawable) skelatonImage.getDrawable()).getBitmap().recycle();
                skelatonImage = (ImageView) findViewById(R.id.skeleton);
                skelatonImage.setImageResource(R.drawable.skeleton3);
            }
            else {
                skelatonImage = (ImageView) findViewById(R.id.imageView);
                skelatonImage.setImageResource(R.drawable.skeleton1);
            }
        }

        else if (guessesRemaining == 4) {
            if(skelatonImage != null) {
                ((BitmapDrawable) skelatonImage.getDrawable()).getBitmap().recycle();
                skelatonImage = (ImageView) findViewById(R.id.skeleton);
                skelatonImage.setImageResource(R.drawable.skeleton4);
            }
            else {
                skelatonImage = (ImageView) findViewById(R.id.imageView);
                skelatonImage.setImageResource(R.drawable.skeleton1);
            }
        }

        else if (guessesRemaining == 3) {
            if(skelatonImage != null) {
                ((BitmapDrawable) skelatonImage.getDrawable()).getBitmap().recycle();
                skelatonImage = (ImageView) findViewById(R.id.skeleton);
                skelatonImage.setImageResource(R.drawable.skeleton5);
            }
            else {
                skelatonImage = (ImageView) findViewById(R.id.imageView);
                skelatonImage.setImageResource(R.drawable.skeleton1);
            }
        }

        else if (guessesRemaining == 2) {
            if(skelatonImage != null) {
                ((BitmapDrawable) skelatonImage.getDrawable()).getBitmap().recycle();
                skelatonImage = (ImageView) findViewById(R.id.skeleton);
                skelatonImage.setImageResource(R.drawable.skeleton6);
            }
            else {
                skelatonImage = (ImageView) findViewById(R.id.imageView);
                skelatonImage.setImageResource(R.drawable.skeleton1);
            }
        }

        else if (guessesRemaining == 1) {
            if(skelatonImage != null) {
                ((BitmapDrawable) skelatonImage.getDrawable()).getBitmap().recycle();
                skelatonImage = (ImageView) findViewById(R.id.skeleton);
                skelatonImage.setImageResource(R.drawable.skeleton7);
            }
            else {
                skelatonImage = (ImageView) findViewById(R.id.imageView);
                skelatonImage.setImageResource(R.drawable.skeleton1);
            }
        }

        else if (guessesRemaining == 0) {
            if(skelatonImage != null) {
                ((BitmapDrawable) skelatonImage.getDrawable()).getBitmap().recycle();
                skelatonImage = (ImageView) findViewById(R.id.skeleton);
                skelatonImage.setImageResource(R.drawable.skeleton7);
            }
            else {
                skelatonImage = (ImageView) findViewById(R.id.imageView);
                skelatonImage.setImageResource(R.drawable.skeleton1);
            }
        }

        else {
            if(skelatonImage != null) {
                ((BitmapDrawable) skelatonImage.getDrawable()).getBitmap().recycle();
                skelatonImage = (ImageView) findViewById(R.id.skeleton);
                skelatonImage.setImageResource(R.drawable.skeleton1);
            }
            else {
                skelatonImage = (ImageView) findViewById(R.id.imageView);
                skelatonImage.setImageResource(R.drawable.skeleton1);
            }
        }

    }

    private void showEnglish(){
        Toast.makeText(GameActivity.this, "The English definition is: " + englishDef + ".", Toast.LENGTH_SHORT).show();
    }

    private void resetEditText(){  //clears the letter from EditText area,re-adds hint text
        guessText.setHint("Type a letter here");
        guessText.setText("");
    }

    boolean isEmptyGuess(String userGuess){
        if (userGuess.length() == 0){
            Toast.makeText(GameActivity.this, "You did not enter a letter.  Please type a " +
                    "letter and press submit button", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    boolean isLetter (String userGuess){
        char someChar = userGuess.charAt(0);
        if ((!(Character.isLetter(someChar)) && (someChar !=(' ')))){
            Toast.makeText(GameActivity.this, "You did not enter a letter.  Please type a " +
                    "letter and press submit button", Toast.LENGTH_SHORT).show();

            guessText.setHint("Type a letter here");
            guessText.setText("");
            return false;
        }
        else{
            return true;
        }
    }

    void checkForLoss(){
        if (guessesRemaining == 0){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("You ran out of attempts");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("The answer was " + word + " (" + englishDef + "). Play again?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    resetImages();
                    resetGame();
                    playGame();
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                    finish();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

        else{
            return;
        }
    }

    void checkIfSolved(){

        userWordGuess = guessText.getText() + "";

        if(userWordGuess.equals("")){
            guessText.setHint("Type answer, hit solve");
        }
        else {
            if (userWordGuess.equals(word)) {

                wins++; //used to calculate winning percentage

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Felicidades / Congratulations");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Buen trabajo! The answer was " + word + " (" + englishDef + "). Play again?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resetImages();
                        resetGame();
                        playGame();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                        finish();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Toast.makeText(GameActivity.this, "Lo siento, your guess no esta correct.", Toast.LENGTH_SHORT).show();
                guessText.setHint("Type a letter here");
                guessText.setText("");
            }
        }
    }

    void gameIsWon(){
        if (word.equals(hiddenWord)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Felicidades / Congratulations");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Buen trabajo! The answer was " + word + " (" + englishDef + "). Play again?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    resetImages();
                    resetGame();
                    playGame();
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                    finish();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }

        else{
            return;
        }
    }

    //save prefs
    public void savePrefs(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    //get prefs
    private int LoadPreferences(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getInt(key, value);
    }

}
