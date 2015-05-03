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

    private String word = "";
    private String englishDef = "";
    private char wordChars[];
    private String hiddenWord = "";
    private char hiddenWordChars[];
    private String userGuess ="";
    char[] userGuessChar;
    private String guessedletters = "";
    private int guessesRemaining = 7;
    private int wordIndex = 0;
    boolean match = false;

    private TextView someAnswer;
    private Button checkButton;
    private Button exitButton;
    private EditText guessText;
    private ImageView skelatonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        someAnswer = (TextView) findViewById(R.id.answerText);
        checkButton = (Button) findViewById(R.id.submitGuessButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        guessText = (EditText) findViewById(R.id.letterGuess);
        skelatonImage = (ImageView) findViewById(R.id.skeleton);

        playGame();

        //check Word button
        View.OnClickListener checkWord = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
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
       exitButton.setOnClickListener(salida);
   }

    void playGame(){

        WordList wordList = new WordList();

        //get a random integer less than the length of the list of words
        wordIndex = wordList.getWordIndex();

        //get Spanish word and English definition
        word = wordList.getVerb(wordIndex);
        englishDef = wordList.getEnglishDef(wordIndex);

        //create a series of dashes or asterisks to represent the number of letters
        for (int i=0; i < word.length(); i++){
            hiddenWord += '*';
        }

        someAnswer.setText(hiddenWord);

        //For testing, will remove this line later
        //Toast.makeText(MainActivity.this, hiddenWord + " " + word, Toast.LENGTH_SHORT).show();
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

    private void checkAnswer(){
        //take the user's letter guess
        userGuess = guessText.getText().toString().toLowerCase();

        userGuessChar = userGuess.toCharArray();
        wordChars = word.toCharArray();

        //checks if user entered a valid letter
        if (isEmptyGuess(userGuess)==false){
            return;
        }

        if (isLetter(userGuess)== false){
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
            checkForWin();
            userGuess="";
            match = false;
        }
        else {
            //decrement the guesses remaining
            guessesRemaining--;

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

            checkForLoss(); //checks if user is out of guesses

            Toast.makeText(GameActivity.this, "'" + userGuess + "' is not in the mystery " +
                    "word. You have " + guessesRemaining + " tries left to solve",
                    Toast.LENGTH_SHORT).show();
            userGuess="";
        }

        resetEditText();

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
        if (!(Character.isLetter(someChar))){
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

    void checkForWin(){
        if (word.equals(hiddenWord)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Felicidades / Congratulations");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Buen trabajo! The word was " + word + " (" + englishDef + "). Play again?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
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
        int data = sharedPreferences.getInt(key, value);
        return data;
    }

}
