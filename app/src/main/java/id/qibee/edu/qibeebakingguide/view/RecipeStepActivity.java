package id.qibee.edu.qibeebakingguide.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.qibee.edu.qibeebakingguide.R;
import id.qibee.edu.qibeebakingguide.adapter.ViewPagerAdapter;
import id.qibee.edu.qibeebakingguide.model.RecipeModel;

import static id.qibee.edu.qibeebakingguide.view.MainActivity.EXTRA_RECIPE;

public class RecipeStepActivity extends AppCompatActivity {
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

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecipeModel recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                recipe.getSteps());

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if (getResources().getBoolean(R.bool.isTablet)) {
            tabLayout.setVisibility(View.GONE);
        } else getResources().getConfiguration();if (!getResources().getBoolean(R.bool.isTablet)
                && getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            tabLayout.setVisibility(View.GONE);
        } else {

            tabLayout.setVisibility(View.VISIBLE);
        }
    }
}
