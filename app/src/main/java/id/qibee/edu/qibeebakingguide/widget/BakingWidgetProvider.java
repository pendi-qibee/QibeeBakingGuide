package id.qibee.edu.qibeebakingguide.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import id.qibee.edu.qibeebakingguide.R;
import id.qibee.edu.qibeebakingguide.model.IngredientModel;
import id.qibee.edu.qibeebakingguide.model.RecipeModel;
import id.qibee.edu.qibeebakingguide.view.RecipeDetailActivity;

/**
 * Created by masq2 on 17/09/2017.
 */

public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                RecipeModel recipeModel, int appWidgetId) {

        Intent intent = RecipeDetailActivity.getIntent(context, recipeModel);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        remoteViews.removeAllViews(R.id.layout_baking_widget_ingredient_list);
        remoteViews.setTextViewText(R.id.text_baking_widget_title, recipeModel.getName());
        remoteViews.setOnClickPendingIntent(R.id.baking_widget_holder, pendingIntent);

        for (IngredientModel ingredientModel : recipeModel.getIngredients()) {
            RemoteViews ingredientRemoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.baking_widget_list_item);
            ingredientRemoteViews.setTextViewText(R.id.text_recipe_widget_ingredient_item,
                    ingredientModel.getIngredient());
            remoteViews.addView(R.id.layout_baking_widget_ingredient_list, ingredientRemoteViews);

        }

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    public static void updateBakingWidget(Context context,
                                          AppWidgetManager appWidgetManager,
                                          RecipeModel recipeModel,
                                          int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeModel, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
//        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
//        super.onDisabled(context);
    }
}
