package com.example.eslam.bakingapp.HelperMethod;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.eslam.bakingapp.Model.Ingredients;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPre {
    private static final String KEY_SHARED = "shared";
    private static final String KEY_SHARED_PERE = "shared_PRE";

    public static void saveArrayList(List<Ingredients> list, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(KEY_SHARED_PERE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(KEY_SHARED, json);
        editor.apply();
    }

    public static void ClearArrayList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(KEY_SHARED_PERE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public static List<Ingredients> getList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(KEY_SHARED_PERE, context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY_SHARED, null);
        Type type = new TypeToken<List<Ingredients>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
