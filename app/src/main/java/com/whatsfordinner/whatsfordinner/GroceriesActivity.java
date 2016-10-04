package com.whatsfordinner.whatsfordinner;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.ContentValues;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

/**
 * Created by Vikram Deshmukh on 9/24/2016.
 */

public class GroceriesActivity extends ListActivity implements View.OnClickListener {


    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    private DBHelper dbHelper;

    private static String[] FROM = { "_id", DBConstants.GROCERIES_ITEM_NAME, DBConstants.GROCERIES_QUANTITY, DBConstants.GROCERIES_UNIT };
    private static int[] TO = { R.id.grocery_item_id, R.id.grocery_item, R.id.grocery_quantity, R.id.grocery_unit };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);

        dbHelper = new DBHelper(this);
        try {

            // creating test data
//            addGroceries("chicken", 2, "lb(s)");
//            addGroceries("milk", 20, "oz(s)");
//            addGroceries("chocolate", 5, "piece(s)");

            Cursor cursor = getGroceries();
            showGroceries(cursor);
        } finally {
            dbHelper.close();
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;
    }

  /*  private void addGroceries(String name, int qty, String unit) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.GROCERIES_ITEM_NAME, name);
        values.put(DBConstants.GROCERIES_QUANTITY, qty);
        values.put(DBConstants.GROCERIES_UNIT, unit);
        db.insertOrThrow(DBConstants.TABLE_GROCERIES, null, values);
    }*/

     private Cursor getGroceries() {
        // Perform a managed query. The Activity will handle closing
        // and re-querying the cursor when needed.
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBConstants.TABLE_GROCERIES, FROM, null, null, null, null, null);
        startManagingCursor(cursor);
        return cursor;
    }

    private void showGroceries(Cursor cursor) {
        // Set up data binding
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            listItems.add(cursor.getString(cursor.getColumnIndex(DBConstants.GROCERIES_ITEM_NAME))
                            +"\t\t\t"+cursor.getString(cursor.getColumnIndex(DBConstants.GROCERIES_QUANTITY))
                            +"\t\t\t"+cursor.getString(cursor.getColumnIndex(DBConstants.GROCERIES_UNIT)));
            cursor.moveToNext();
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
