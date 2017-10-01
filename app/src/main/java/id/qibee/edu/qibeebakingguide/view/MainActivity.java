package id.qibee.edu.qibeebakingguide.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.qibee.edu.qibeebakingguide.InterfaceOnItemClickListener;
import id.qibee.edu.qibeebakingguide.R;
import id.qibee.edu.qibeebakingguide.adapter.RecipeAdapter;
import id.qibee.edu.qibeebakingguide.model.RecipeModel;
import id.qibee.edu.qibeebakingguide.widget.BakingWidgetService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.qibee.edu.qibeebakingguide.network.ApiUtils.getApiService;

public class MainActivity extends AppCompatActivity implements InterfaceOnItemClickListener {

    public static final String EXTRA_RECIPE = "id.qibee.edu.qibeebakingguide.extra_recipe";
    private static final String RECIPES_STATE_KEY = "RECIPES_STATE_KEY";

    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerViewRecipes;

    private ArrayList<RecipeModel> recipes = new ArrayList<>();
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        recipeAdapter = new RecipeAdapter(this, recipes, this);
        recyclerViewRecipes.setAdapter(recipeAdapter);

        if (getResources().getBoolean(R.bool.isTablet)) {
            recyclerViewRecipes.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(
                    this,
                    LinearLayoutManager.VERTICAL,
                    false
            ));
        }

        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList(RECIPES_STATE_KEY);
            setRecipeAdapter();
        } else {
            loadRecipes();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPES_STATE_KEY, recipes);
    }

    private void setRecipeAdapter() {
        recipeAdapter = new RecipeAdapter(MainActivity.this, recipes, this);
        recyclerViewRecipes.setAdapter(recipeAdapter);

    }

    private void loadRecipes() {
        Call<ArrayList<RecipeModel>> arrayListCall = getApiService().getRecipeResult();
        arrayListCall.enqueue(new Callback<ArrayList<RecipeModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeModel>> call, Response<ArrayList<RecipeModel>> response) {
                recipes=response.body();
                setRecipeAdapter();
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Load Recipes failed", Toast.LENGTH_SHORT).show();
                Log.d("Main-loadRecipes()",t.getMessage());

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipes.get(position));
        BakingWidgetService.startUpdateWidget(this, recipes.get(position));
        startActivity(intent);

    }
}
