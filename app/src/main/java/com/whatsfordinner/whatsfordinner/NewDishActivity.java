package com.whatsfordinner.whatsfordinner;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.thedeveloperworldisyours.listviewinsidescrollview.view.NonScrollListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewDishActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private static String[] FROM = {"_id", DBConstants.RECIPES_NAME};
    private static String[] FROM_GRO = { "_id", DBConstants.GROCERIES_ITEM_NAME, DBConstants.GROCERIES_QUANTITY, DBConstants.GROCERIES_UNIT};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish);

        dbHelper = new DBHelper(this);
        // Get reference of widgets from XML activity_new_dish
        final Button btn = (Button) findViewById(R.id.btn);
        final Button btn_save_dish = (Button) findViewById(R.id.btn_save_dish);
        // Construct the data source
        final ArrayList<Ingredient> arrayOfIngredient = new ArrayList<Ingredient>();
        //arrayOfIngredient.add(new Ingredient("Tomato", 1, "Pounds(lbs)"));
        //arrayOfIngredient.add(new Ingredient("Potatoes", 2, "Pounds(lbs)"));
        //arrayOfIngredient.add(new Ingredient("Bell Peppers", 3, "Pieces(pcs)"));

        // Create the adapter to convert the array to views
        final ListAdapter adapter = new ListAdapter(this, arrayOfIngredient);

        // Attach the adapter to a ListView
        final NonScrollListView listView = (NonScrollListView) findViewById(R.id.ll);
        listView.setAdapter(adapter);

        final Context ctx = this.getApplicationContext();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new Items to List

                //  Save dish in db
                int itemsCount = listView.getChildCount();
                arrayOfIngredient.clear();
                for (int i = 0; i < itemsCount; i++) {
                    View view = listView.getChildAt(i);
                    String itemquantity = ((EditText) view.findViewById(R.id.quantity)).getText().toString();
                    String itemdesc = ((EditText) view.findViewById(R.id.name)).getText().toString();
                    String itemprice = ((Spinner) view.findViewById(R.id.unit)).getSelectedItem().toString();
                    //Log.d("DEBUG", "adapter data" + itemdesc + "\t" + itemprice + "\t" + itemquantity);

                    //addRecipes("Chicken Sandwich", "", "", "chicken, bread, cheese", "Add all between breads.", 2);
                    if (Float.parseFloat(itemquantity) <= 0) {
                        Toast.makeText(ctx, "Invalid Ingredient quantity value.\nCannot Save.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (itemdesc.isEmpty()) {
                        Toast.makeText(ctx, "Ingredient name cannot be empty.\nPlease fix.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    arrayOfIngredient.add(new Ingredient(itemdesc, Float.parseFloat(itemquantity), itemprice));
                }
                arrayOfIngredient.add(new Ingredient("", 0, "Pounds(lbs)"));
                adapter.notifyDataSetChanged();
            }
        });
        btn_save_dish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipe_name = ((EditText) findViewById(R.id.dish_name)).getText().toString();
                String recipe_directions = ((EditText) findViewById(R.id.dish_directions)).getText().toString();
                if (recipe_name.isEmpty()) {
                    Toast.makeText(ctx, "Recipe Name cannot be empty.\nCannot Save.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (recipe_directions.isEmpty()) {
                    Toast.makeText(ctx, "Please enter the directions for preparing the dish.\nCannot Save.", Toast.LENGTH_LONG).show();
                    return;
                }
                //  Save dish in db
                int itemsCount = listView.getChildCount();
                String ingredients = "";
                for (int i = 0; i < itemsCount; i++) {
                    View view = listView.getChildAt(i);
                    String itemquantity = ((EditText) view.findViewById(R.id.quantity)).getText().toString();
                    String itemdesc = ((EditText) view.findViewById(R.id.name)).getText().toString();
                    String itemprice = ((Spinner) view.findViewById(R.id.unit)).getSelectedItem().toString();
                    //Log.d("DEBUG", "adapter data" + itemdesc + "\t" + itemprice + "\t" + itemquantity);

                    //addRecipes("Chicken Sandwich", "", "", "chicken, bread, cheese", "Add all between breads.", 2);
                    if (Float.parseFloat(itemquantity) <= 0) {
                        Toast.makeText(ctx, "Invalid Ingredient quantity value.\nCannot Save.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (itemdesc.isEmpty()) {
                        Toast.makeText(ctx, "Ingredient name cannot be empty.\nPlease fix.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ingredients += itemdesc + "," + itemquantity + "," + itemprice + "\n";
                }
                addRecipes(recipe_name,"","",ingredients,recipe_directions,0);
                addIngredientsToGroceriesList(ingredients);
                finish();
                Toast.makeText(ctx, "Recipe Added Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void addIngredientsToGroceriesList(String ingredients) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DBConstants.TABLE_GROCERIES, FROM_GRO,null, null, null, null, null);
        cursor.moveToFirst();
        JSONArray groceries1 = getJSONFromCursor(cursor);
        List<GroceryItem> merged = new ArrayList<GroceryItem>();
        String[] ingreList = ingredients.split("\n");

        for(String item : ingreList) {
            String[] itemDetails = item.split(",");
            merged.add(new GroceryItem(itemDetails[0], itemDetails[1], itemDetails[2]));
        }
        boolean found;
        for (int i=0; i<groceries1.length(); i++) {
            try {
                JSONObject item1 = groceries1.getJSONObject(i);
                Log.d("DEBUG", item1.toString());
                String g_name = item1.getString("item_name");
                String g_qty = item1.getString("quantity");
                String g_unit = item1.getString("unit");
                found = false;
                for(GroceryItem g : merged) {
                    Log.d("DEBUG", g.name+" :: "+ g_name);
                    if(g.name.equals(g_name)) {
                        g.qty = String.valueOf(Float.parseFloat(g.qty) + Float.parseFloat(g_qty));
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    merged.add(new GroceryItem(g_name, g_qty, g_unit));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        db.execSQL("delete from "+ DBConstants.TABLE_GROCERIES);
        for(GroceryItem g : merged) {
            ContentValues values = new ContentValues();
            values.put(DBConstants.GROCERIES_ITEM_NAME, g.name);
            values.put(DBConstants.GROCERIES_QUANTITY, g.qty);
            values.put(DBConstants.GROCERIES_UNIT, g.unit);
            db.insertOrThrow(DBConstants.TABLE_GROCERIES, null, values);
        }
    }
    public class GroceryItem{
        public String name, qty, unit;

        public GroceryItem(String name, String qty, String unit){
            this.name = name;
            this.qty = qty;
            this.unit = unit;
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

    private JSONArray getJSONFromCursor(Cursor cursor) {
        JSONArray resultSet     = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        return resultSet;
    }

    private class ListAdapter extends ArrayAdapter<Ingredient> {

        public ListAdapter(Context context, ArrayList<Ingredient> users) {
            super(context, 0, users);

        }


        @Override

        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            Ingredient ingredient = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            }

            // Lookup view for data population
            EditText etName = (EditText) convertView.findViewById(R.id.name);
            EditText etQty = (EditText) convertView.findViewById(R.id.quantity);

            // Populate the data into the template view using the data object
            etName.setText(ingredient.getName());
            etQty.setText(Float.toString(ingredient.getQuantity()));

            // Spinner element
            Spinner spinner = (Spinner) convertView.findViewById(R.id.unit);

            // Spinner click listener
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            // Spinner Drop down elements
            List<String> categories = new ArrayList<String>();
            categories.add("Pieces(pcs)");
            categories.add("Pounds(lbs)");
            categories.add("Ounces(oz)");
            categories.add("Litres(L)");
            categories.add("Dozens(Dz)");
            categories.add("Grams(g)");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);

            // Drop down activity_new_dish style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);


            // Return the completed view to render on screen

            return convertView;

        }

    }
}
