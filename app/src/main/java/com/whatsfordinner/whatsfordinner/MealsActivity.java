package com.whatsfordinner.whatsfordinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Vikram Deshmukh on 9/24/2016.
 */

public class MealsActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

    }

    @Override
    public void onClick(View v) {
        Intent i;
    }
}
