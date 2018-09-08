package com.example.eslam.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.eslam.bakingapp.HelperMethod.SharedPre;
import com.example.eslam.bakingapp.Model.Ingredients;
import com.example.eslam.bakingapp.R;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static List<Ingredients> mIngredients;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        mIngredients = SharedPre.getList(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        StringBuilder Ingredient = new StringBuilder();

        for (Ingredients ingredients : mIngredients) {
            Ingredient.append(String.valueOf(ingredients.getIngredient())).append("  ")
                    .append(ingredients.getMeasure()).append("  ")
                    .append(String.valueOf(ingredients.getQuantity())).append("\n");
        }
        views.setTextViewText(R.id.appwidget_ingrediants, Ingredient);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

