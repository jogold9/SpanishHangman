package com.joshbgold.SpanishHangman;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private WordList wordList = new WordList();
    private String word = "";
    private String hiddenWord = "";
    private char hiddenWordChars[];
    private String userGuess ="";
    private char userGuessChar;
    private TextView someAnswer;
    private Button checkButton;
    private Button exitButton;
    private EditText guessText;
    private int matchPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //get the word we are guessing letters for
    word = wordList.getWord();

    //create a series of dashes or asterisks to represent the number of letters
    for (int i=0; i < word.length(); i++){
        hiddenWord += '*';
    }
        someAnswer = (TextView) findViewById(R.id.answerText);
        checkButton = (Button) findViewById(R.id.submitGuessButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        guessText = (EditText) findViewById(R.id.letterGuess);

        someAnswer.setText(hiddenWord);

        Toast.makeText(MainActivity.this, hiddenWord + " " + word, Toast.LENGTH_LONG).show();

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

    private void checkAnswer(){
        //take the user's letter guess
        userGuess = guessText.getText().toString().toLowerCase();
        userGuessChar[] = userGuess.toCharArray();

        //if there were matches, get index of match(es) locations
        matchPosition = word.indexOf(userGuess);

        if (matchPosition != -1){
            hiddenWordChars = hiddenWord.toCharArray();
            hiddenWordChars[matchPosition] = userGuessChar;

            Toast.makeText(MainActivity.this, "'" + userGuess + "' is in the mystery word.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this, "'" + userGuess + "' is not in the mystery word.",
                    Toast.LENGTH_LONG).show();
        }

        //display any matches for the guess in the mystery word



        //Optional: display the incorrect letter guesses

        //clear the letter from the edit text area
        guessText.setHint("");
        guessText.setText("");

        //check if the word is solved, Alert message if the word is solved, with English definition

        //When user clicks OK on the alert message, clear all guessed letters, int guesses clears
        //and they get a new mystery verb
    }

}
