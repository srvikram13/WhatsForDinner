package com.whatsfordinner.whatsfordinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;


public class RecipesActivity extends FragmentActivity implements View.OnClickListener, RecipeListFragment.OnLineSelectedListener {

    RecipeListFragment recipeListFragment;
    RecipeDetailFragment recipeDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        recipeListFragment = new RecipeListFragment();
        recipeDetailFragment = new RecipeDetailFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.recipe_list_fragment, recipeListFragment);
        transaction.add(R.id.recipe_detail_fragment, recipeDetailFragment);

        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        Intent i;
    }

    @Override
    public void onListItemSelected(int position) {
        Log.d("DEBUG", "Selected item at position: " + position);
    }
}
