package id.qibee.edu.qibeebakingguide.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import id.qibee.edu.qibeebakingguide.model.RecipeModel;

/**
 * Created by masq2 on 17/09/2017.
 */

public class BakingWidgetService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET
            = "id.qibee.edu.qibeebakingguide.action_update_widget";

    public static final String BUNDLE_RECIPE_WIDGET_DATA
            = "id.qibee.edu.qibeebakingguide.baking_widget_data";

    public BakingWidgetService() {
        super("BakingWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)
                    && intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA) != null) {
                RecipeModel recipe = intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA);
                handleActionUpdateWidget(recipe);
            }
        }

    }

    private void handleActionUpdateWidget(RecipeModel recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int [] appWidgets = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, BakingWidgetProvider.class)
        );
        BakingWidgetProvider.updateBakingWidget(this, appWidgetManager, recipe, appWidgets);
    }

    public static void startUpdateWidget(Context context, RecipeModel recipeModel) {
        Intent intent = new Intent(context, BakingWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(BUNDLE_RECIPE_WIDGET_DATA, recipeModel);
        context.startService(intent);
    }
}
