package com.whatsfordinner.whatsfordinner;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        /*
        ImageView imageView  = (ImageView) findViewById(R.id.displayInfo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                TestFragment dialogFragment = new TestFragment ();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });
        */

        // Set up click listeners for all the elements
        ImageView imageView  = (ImageView) findViewById(R.id.displayInfo);
        imageView.setOnClickListener(this);

        View mealsIcon = findViewById(R.id.meals_icon);
        mealsIcon.setOnClickListener(this);

        View recipesIcon = findViewById(R.id.recipes_icon);
        recipesIcon.setOnClickListener(this);

        View groceriesIcon = findViewById(R.id.groceries_icon);
        groceriesIcon.setOnClickListener(this);

        View newIcon = findViewById(R.id.new_icon);
        newIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.displayInfo:
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                TestFragment dialogFragment = new TestFragment ();
                dialogFragment.show(fm, "Sample Fragment");
                break;
            case R.id.meals_icon:
                i = new Intent(this, MealsActivity.class);
                startActivity(i);
                break;
            case R.id.recipes_icon:
                i = new Intent(this, RecipesActivity.class);
                startActivity(i);
                break;
            case R.id.groceries_icon:
                i = new Intent(this, GroceriesActivity.class);
                startActivity(i);
                break;
            case R.id.new_icon:
                i = new Intent(this, NewDishActivity.class);
                startActivity(i);
                break;
        }
    }

}
