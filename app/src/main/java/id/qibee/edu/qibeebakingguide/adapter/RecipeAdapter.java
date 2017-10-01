package id.qibee.edu.qibeebakingguide.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.qibee.edu.qibeebakingguide.InterfaceOnItemClickListener;
import id.qibee.edu.qibeebakingguide.R;
import id.qibee.edu.qibeebakingguide.model.RecipeModel;

/**
 * Created by masq2 on 16/09/2017.
 */

public class RecipeAdapter  extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder>{

    private Context mContext;
    private ArrayList<RecipeModel> mRecipes;
    private InterfaceOnItemClickListener mListener;

    public RecipeAdapter(Context mContext, ArrayList<RecipeModel> mRecipes, InterfaceOnItemClickListener mListener) {
        this.mContext = mContext;
        this.mRecipes = mRecipes;
        this.mListener = mListener;
    }

    @Override
    public RecipeAdapter.RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
        LayoutInflater layoutInflater =LayoutInflater.from(mContext);
        View rootView=layoutInflater.inflate(R.layout.item_recipe, parent, false);
        return new RecipeHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
//        return 0;
        return mRecipes.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.image_recipe_thumbnail)
        ImageView imageRecipeThumbnail;

        @BindView(R.id.text_recipe_name)
        TextView textRecipeName;

        public RecipeHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListener.onItemClick(clickedPosition);
        }

        public void bind(int position) {
            RecipeModel recipe = mRecipes.get(position);
            textRecipeName.setText(recipe.getName());

            Glide.with(mContext)
                    .load(recipe.getImage())
                    .error(R.drawable.apple_pie)
                    .into(imageRecipeThumbnail);
        }
    }
}
