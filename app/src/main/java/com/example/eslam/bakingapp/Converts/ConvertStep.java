package com.example.eslam.bakingapp.Converts;

import android.arch.persistence.room.TypeConverter;

import com.example.eslam.bakingapp.Model.Ingredients;
import com.example.eslam.bakingapp.Model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ConvertStep {
    @TypeConverter // note this annotation
    public String fromOptionValuesList(List<Step> steps) {
        if (steps == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        String json = gson.toJson(steps, type);
        return json;
    }

    @TypeConverter
    public List<Step> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Step>>() {
        }.getType();
        List<Step> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
