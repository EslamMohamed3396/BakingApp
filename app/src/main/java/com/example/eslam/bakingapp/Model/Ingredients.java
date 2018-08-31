
package com.example.eslam.bakingapp.Model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Ingredients implements Parcelable {


    public final static Parcelable.Creator<Ingredients> CREATOR = new Creator<Ingredients>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        public Ingredients[] newArray(int size) {
            return (new Ingredients[size]);
        }

    };
    @SerializedName("quantity")
    @Expose
    public Double quantity;
    @SerializedName("measure")
    @Expose
    public String measure;
    @PrimaryKey
    @SerializedName("ingredient")
    @Expose
    @NonNull
    public String ingredient;

    public Ingredients(Double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    private Ingredients(Parcel in) {
        this.quantity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.measure = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quantity);
        dest.writeValue(measure);
        dest.writeValue(ingredient);
    }

    public int describeContents() {
        return 0;
    }

}