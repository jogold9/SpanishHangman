package com.joshbgold.SpanishHangman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WordList wordList = new WordList();
    private String word = "";
    private String englishDef = "";
    private char wordChars[];
    private String hiddenWord = "";
    private char hiddenWordChars[];
    private String userGuess ="";
    char[] userGuessChar;
    private String guessedletters = "";
    private int guessesRemaining = 7;
    private int index = 0;
    boolean match = false;

    private TextView someAnswer;
    private Button checkButton;
    private Button exitButton;
    private EditText guessText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        someAnswer = (TextView) findViewById(R.id.answerText);
        checkButton = (Button) findViewById(R.id.submitGuessButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        guessText = (EditText) findViewById(R.id.letterGuess);

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
        //get the word we are guessing letters for
        word = wordList.getWord();


        //create a series of dashes or asterisks to represent the number of letters
        for (int i=0; i < word.length(); i++){
            hiddenWord += '*';
        }

        someAnswer.setText(hiddenWord);

        //For testing, will remove this line later
        //Toast.makeText(MainActivity.this, hiddenWord + " " + word, Toast.LENGTH_SHORT).show();
    }

    void resetVars(){
        word = "";
        hiddenWord = "";
        userGuess ="";
        match = false;
        guessesRemaining = 7;
        guessedletters = "";
        englishDef = "";
        index = 0;
    }

    private void checkAnswer(){
        //take the user's letter guess
        userGuess = guessText.getText().toString().toLowerCase();

        userGuessChar = userGuess.toCharArray();
        wordChars = word.toCharArray();

        //checks if user entered a valid letter
        if (isValidGuess(userGuess)==false){
            return;
        }

        //The next few lines handle accidental repeat guessesz
        if (guessedletters.contains(userGuess)) {
            Toast.makeText(MainActivity.this, "You already guessed that letter.",
                    Toast.LENGTH_SHORT).show();
            guessText.setHint("Type a letter here to guess");
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

            checkForLoss(); //checks if user is out of guesses

            Toast.makeText(MainActivity.this, "'" + userGuess + "' is not in the mystery " +
                    "word. You have " + guessesRemaining + " tries left to solve",
                    Toast.LENGTH_SHORT).show();
            userGuess="";
        }

        resetEditText();
        //Display the incorrect letter guesses

        //check if the word is solved, Alert message if the word is solved, with English definition

        //When user clicks OK on the alert message, clear all guessed letters, int guesses clears
        //and they get a new mystery verb
    }

    private void resetEditText(){  //clears the letter from EditText area,re-adds hint text
        guessText.setHint("Type a letter here to guess");
        guessText.setText("");
    }

    boolean isValidGuess(String userGuess){
        if (userGuess.length() == 0){
            Toast.makeText(MainActivity.this, "You did not enter a letter.  Please type a " +
                    "letter and press submit button", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    void checkForLoss(){
        if (guessesRemaining == 0){

            WordList wordList = new WordList();

            //if below doesn't work I might have to use preferences instead
       /*     index = LoadPreferences("index", index);
            englishDef = wordList.getEnglishDefs(index);*/


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("You ran out of attempts");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("The answer was " + word + ". Play again?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    resetVars();
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
            builder.setMessage("Buen trabajo! The word was " + word + ". Play again?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    resetVars();
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
