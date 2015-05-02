package com.joshbgold.SpanishHangman;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

/**
 * Created by JoshG on 4/28/2015.
 */
public class MainActivity extends Activity{

    private Button verbsButton;
    private Button commonWordsButton;
    private Button exitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        verbsButton = (Button) findViewById(R.id.verbsButton);
        commonWordsButton = (Button) findViewById(R.id.commonWordsButton);
        exitButton = (Button) findViewById(R.id.exitButton);


        //start game with verbs
        View.OnClickListener verbs = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameWithVerbs();
            }
        };

        //start game with common words
        View.OnClickListener commonWords = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameWithCommonWords();
            }
        };

        //exit the application when exit button is pressed
        View.OnClickListener salida = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };

        verbsButton.setOnClickListener(verbs);
        commonWordsButton.setOnClickListener(commonWords);
        exitButton.setOnClickListener(salida);

    }


    void startGameWithVerbs() {
        //value of zero means to use verb word list
       // savePrefs("choice", 0);
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    void startGameWithCommonWords(){
        //value of one means to use common words list
       // savePrefs("choice", 1);
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    //save prefs
    public void savePrefs(String key, int value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
