package id.qibee.edu.qibeebakingguide.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import id.qibee.edu.qibeebakingguide.R;
import id.qibee.edu.qibeebakingguide.model.RecipeModel;

import static id.qibee.edu.qibeebakingguide.view.MainActivity.EXTRA_RECIPE;

public class RecipeDetailActivity extends AppCompatActivity {

    public static Intent getIntent(Context context, RecipeModel recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE,recipe);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        RecipeModel recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(recipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getResources().getBoolean(R.bool.isTablet)) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstance(recipe);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container,detailFragment)
                    .commit();

            RecipeStepFragment2 stepFragment = RecipeStepFragment2.newInstance(recipe.getSteps().get(0));
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_step_container, stepFragment)
                    .commit();

        } else {
            //fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

            if (fragment == null) {
                fragment = RecipeDetailFragment.newInstance(recipe);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
        }
        return true;
    }
}
