package id.qibee.edu.qibeebakingguide.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.qibee.edu.qibeebakingguide.InterfaceOnItemClickListener;
import id.qibee.edu.qibeebakingguide.R;
import id.qibee.edu.qibeebakingguide.adapter.StepAdapter;
import id.qibee.edu.qibeebakingguide.model.IngredientModel;
import id.qibee.edu.qibeebakingguide.model.RecipeModel;
import id.qibee.edu.qibeebakingguide.model.StepModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment implements InterfaceOnItemClickListener{

    public static final String EXTRA_STEP = "id.qibee.edu.qibeebakingguide.extra_step";

    public static final String EXTRA_RECIPE = "id.qibee.edu.qibeebakingguide.extra_recipe";
    private static final String ARGUMENT_RECIPE = "argument_recipe";

    @BindView(R.id.recycler_view_steps)
    RecyclerView recyclerViewSteps;

    @BindView(R.id.text_list_of_ingredients)
    TextView textListOfIngredients;

    private StepAdapter stepAdapter;

    private List<StepModel> steps = new ArrayList<>();

    private RecipeModel recipe;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //data from arg
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(ARGUMENT_RECIPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);
        stepAdapter = new StepAdapter(getContext(), steps, this);
        recyclerViewSteps.setAdapter(stepAdapter);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerViewSteps.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        loadIngredientAndSteps();
        return view;
    }

    private void loadIngredientAndSteps() {
        List<IngredientModel> ingredientModelList = recipe.getIngredients();
        String listOfIngredients ="";


        for (IngredientModel ingredientModel: ingredientModelList ) {
            listOfIngredients += ingredientModel.getQuantity() +" "+ ingredientModel.getMeasure() +" "+ ingredientModel.getIngredient() + " \n";
        }

        textListOfIngredients.setText(listOfIngredients);
        steps = recipe.getSteps();
        stepAdapter = new StepAdapter(getContext(), steps, this);
        recyclerViewSteps.setAdapter(stepAdapter);
    }

    public static RecipeDetailFragment newInstance(RecipeModel recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_RECIPE, recipe);
        recipeDetailFragment.setArguments(args);
        return recipeDetailFragment;
    }


    @Override
    public void onItemClick(int position) {
        if (!getResources().getBoolean(R.bool.isTablet)) {
            Intent intent = new Intent(getActivity(), RecipeStepActivity.class);
            intent.putExtra(EXTRA_STEP, steps.get(position));
            intent.putExtra(EXTRA_RECIPE, recipe);
            startActivity(intent);

        } else {
            FragmentManager fragmentManager = getFragmentManager();
            RecipeStepFragment2 stepFragment = RecipeStepFragment2.newInstance(steps.get(position));
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_step_container, stepFragment)
                    .commit();
        }

    }

}
