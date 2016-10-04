package com.whatsfordinner.whatsfordinner;

//import android.app.ListFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class RecipeListFragment extends ListFragment {

    OnLineSelectedListener mCallback;
    private DBHelper dbHelper;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


    private static String[] FROM = { "_id", DBConstants.RECIPES_NAME};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_recipe_list_fragment, null);
    }

    // Container Activity must implement this interface
    public interface OnLineSelectedListener {
        public void onListItemSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter=new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);

        dbHelper = new DBHelper(this.getContext());
        try {

            // creating test data
//            addRecipes("Chicken Sandwich", "", "", "chicken, bread, cheese", "Add all between breads.", 2);
//            addRecipes("Veg Sandwich", "", "", "Veggies, bread, cheese", "Add all between breads.", 2);
//            addRecipes("Chicken Salad", "", "", "chicken, veggies, cheese", "Mix all ingredients.", 2);

            showRecipes();
        } finally {
            dbHelper.close();
        }
    }

    private void addRecipes(String name, String imgUrl, String imgPath, String ingredientsList, String directions, int count) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.RECIPES_NAME, name);
        values.put(DBConstants.RECIPES_IMG_URL, imgUrl);
        values.put(DBConstants.RECIPES_IMG_PATH, imgPath);
        values.put(DBConstants.RECIPES_INGREDIENTS_LIST, ingredientsList);
        values.put(DBConstants.RECIPES_DIRECTIONS, directions);
        values.put(DBConstants.RECIPES_COUNT_AVAILABLE, count);
        db.insertOrThrow(DBConstants.TABLE_RECIPES, null, values);
    }

    private void showRecipes() {
        // Perform a managed query. The Activity will handle closing
        // and re-querying the cursor when needed.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBConstants.TABLE_RECIPES, FROM, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listItems.add(cursor.getString(cursor.getColumnIndex(DBConstants.RECIPES_NAME)));
            cursor.moveToNext();
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
