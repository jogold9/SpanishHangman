package com.joshbgold.SpanishHangman;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by JoshG on 5/11/2015.
 */
public class statsActivity extends MainActivity {

    private TextView overallWinPercent;
    private TextView Streak;
    private Button exitButton;
    private float winPercentage;
    private String truncatedWinPercentage;
    private int wins;
    private int attempts;
    private int win_streak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        Streak = (TextView)findViewById(R.id.winStreakText);
        overallWinPercent = (TextView)findViewById(R.id.percentText);
        exitButton = (Button) findViewById(R.id.exitButton);

        //exit the application when exit button is pressed
        View.OnClickListener salida = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };

        exitButton.setOnClickListener(salida);

        wins = LoadPreferences("Wins", wins);
        attempts = LoadPreferences("Attempts", attempts);
        win_streak = LoadPreferences("Streak", win_streak);

        //calculate win percentage
        if (attempts == 0) {
            //do nothing, as we don't want to divide by zero
        } else {
            float floatWins = wins;
            float floatTotalAttempts = attempts;
            winPercentage = ((floatWins / floatTotalAttempts) * 100);
            truncatedWinPercentage = new DecimalFormat("0.00").format(winPercentage);
            overallWinPercent.setText("Overall Win %: " + truncatedWinPercentage);

            Streak.setText("Win streak: " + win_streak + " wins");

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
