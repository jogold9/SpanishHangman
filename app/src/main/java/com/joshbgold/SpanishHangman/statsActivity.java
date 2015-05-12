package com.joshbgold.SpanishHangman;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by JoshG on 5/11/2015.
 */
public class statsActivity extends MainActivity {

    private Button exitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        exitButton = (Button) findViewById(R.id.exitButton);

        //exit the application when exit button is pressed
        View.OnClickListener salida = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };

        exitButton.setOnClickListener(salida);
    }
}
