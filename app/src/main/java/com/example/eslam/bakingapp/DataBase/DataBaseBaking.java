package com.example.eslam.bakingapp.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.eslam.bakingapp.Converts.ConvertStep;
import com.example.eslam.bakingapp.Converts.ConvertsIngred;
import com.example.eslam.bakingapp.Dao.DaoBaking;
import com.example.eslam.bakingapp.Model.Ingredients;
import com.example.eslam.bakingapp.Model.Recipe;
import com.example.eslam.bakingapp.Model.Step;

@Database(entities = {Recipe.class, Ingredients.class, Step.class}, version = 1, exportSchema = false)
@TypeConverters({ConvertsIngred.class, ConvertStep.class})
public abstract class DataBaseBaking extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "baking";
    private static DataBaseBaking sINSTANCE;

    public static DataBaseBaking getINSTANCE(Context context) {
        if (sINSTANCE == null) {
            synchronized (LOCK) {
                sINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        DataBaseBaking.class,
                        DataBaseBaking.DATABASE_NAME)
                        .build();
            }
        }
        return sINSTANCE;
    }

    public abstract DaoBaking daoBaking();
}
