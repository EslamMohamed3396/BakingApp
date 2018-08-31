package com.example.eslam.bakingapp.Converts;

import android.arch.persistence.room.TypeConverter;

import com.example.eslam.bakingapp.Model.Ingredients;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ConvertsIngred {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<Ingredients> ingredients) {
        if (ingredients == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredients>>() {
        }.getType();
        String json = gson.toJson(ingredients, type);
        return json;
    }

    @TypeConverter
    public List<Ingredients> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredients>>() {
        }.getType();
        List<Ingredients> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
