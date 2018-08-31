package com.example.eslam.bakingapp.Model;


import java.util.List;

import android.arch.persistence.room.Entity;


import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;


import com.example.eslam.bakingapp.Converts.ConvertStep;
import com.example.eslam.bakingapp.Converts.ConvertsIngred;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "recipe")
public class Recipe implements Parcelable {

    public final static Parcelable.Creator<Recipe> CREATOR = new Creator<Recipe>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return (new Recipe[size]);
        }

    };
    @PrimaryKey
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @TypeConverters(ConvertsIngred.class)
    @SerializedName("ingredients")
    @Expose
    private List<Ingredients> ingredients = null;
    @TypeConverters(ConvertStep.class)
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;

    public Recipe(int id, String name, List<Ingredients> ingredients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    private Recipe(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.ingredients, (com.example.eslam.bakingapp.Model.Ingredients.class.getClassLoader()));
        in.readList(this.steps, (com.example.eslam.bakingapp.Model.Step.class.getClassLoader()));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public List<Ingredients> getIngredients() {
        return ingredients;
    }


    public List<Step> getSteps() {
        return steps;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeList(ingredients);
        dest.writeList(steps);

    }

    public int describeContents() {
        return 0;
    }

}

