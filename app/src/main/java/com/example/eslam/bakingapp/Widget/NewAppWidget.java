package com.example.eslam.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.eslam.bakingapp.Model.Ingredients;
import com.example.eslam.bakingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private List<Ingredients> mIngredientsList;

    public List<Ingredients> getList(String key, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<List<Ingredients>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        String restoredText = "";
        String restoredTe = "";
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        mIngredientsList = getList("in", context);
        for (int i = 0; i < mIngredientsList.size(); i++) {
            restoredText = mIngredientsList.get(i).getIngredient();
            restoredTe = mIngredientsList.get(i).getMeasure();
            views.setTextViewText(R.id.appwidget_text, restoredText + "\n" + restoredTe + "\n");
        }


        // Construct the RemoteViews object

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

