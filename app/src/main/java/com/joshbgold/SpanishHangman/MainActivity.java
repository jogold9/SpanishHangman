package com.joshbgold.SpanishHangman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WordList wordList = new WordList();
    private String word = "";
    private char wordChars[];
    private String hiddenWord = "";
    private char hiddenWordChars[];
    private String userGuess ="";
    char[] userGuessChar;
    boolean match = false;
    private int guessesRemaining = 6;

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

        //For testing, will remove this line later
         Toast.makeText(MainActivity.this, hiddenWord + " " + word, Toast.LENGTH_SHORT).show();
    }

    void resetVars(){
        word = "";
        hiddenWord = "";
        userGuess ="";
        match = false;
        //matchPosition = 0;
        guessesRemaining = 6;
    }

    private void checkAnswer(){
        //take the user's letter guess
        userGuess = guessText.getText().toString().toLowerCase();

        //checks if user entered a valid letter
        if (isValidGuess(userGuess)==false){
            return;
        }

        userGuessChar = userGuess.toCharArray();
        wordChars = word.toCharArray();

        for (int j = 0; j < word.length(); j++) {

            //if there were matches, get index of match(es) locations


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
            Toast.makeText(MainActivity.this, "'" + userGuess + "' is in the mystery word.  You " +
                    "have " + guessesRemaining + " tries left to solve",
                    Toast.LENGTH_SHORT).show();
            match = false;
        }
        else {
            Toast.makeText(MainActivity.this, "'" + userGuess + "' is not in the mystery " +
                    "word. You have " + guessesRemaining + " tries left to solve",
                    Toast.LENGTH_SHORT).show();
            //decrement the guesses remaining
            guessesRemaining--;

            checkForLoss(); //checks if user is out of guesses

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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sorry you ran out of guesses")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("The answer was " + word + ".")
                    .setCancelable(false)
                    .setNegativeButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            resetVars();
            playGame();

        }
        else{
            return;
        }
    }


}
