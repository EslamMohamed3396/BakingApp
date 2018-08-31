package com.example.eslam.bakingapp.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.eslam.bakingapp.Model.Recipe;

import java.util.List;

@Dao
public interface DaoBaking {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> loadAllRecipe();


    @Query("SELECT * FROM recipe WHERE id=:mId")
    LiveData<List<Recipe>> loadAllRecipeByID(int mId);


    @Query("DELETE FROM recipe WHERE id = :favRecipeId")
    int deleteRecipeById(int favRecipeId);
}
