package com.whatsfordinner.whatsfordinner;

import android.provider.BaseColumns;


public interface DBConstants extends BaseColumns {

    public static final String TABLE_GROCERIES = "groceries";
    // Columns in the 'groceries' table
    public static final String GROCERIES_ITEM_NAME = "item_name";
    public static final String GROCERIES_QUANTITY = "quantity";
    public static final String GROCERIES_UNIT = "unit";

    public static final String TABLE_RECIPES = "recipes";
    // Columns in the 'recipes' table
    public static final String RECIPES_NAME = "recipe_name";
    public static final String RECIPES_IMG_URL = "img_url";
    public static final String RECIPES_IMG_PATH = "img_path";
    public static final String RECIPES_INGREDIENTS_LIST = "ingredients_list";
    public static final String RECIPES_DIRECTIONS = "directions";
    public static final String RECIPES_COUNT_AVAILABLE = "count_available_for_meal_planning";
}
