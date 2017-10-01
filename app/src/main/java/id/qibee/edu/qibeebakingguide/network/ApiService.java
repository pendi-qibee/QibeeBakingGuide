package id.qibee.edu.qibeebakingguide.network;

import java.util.ArrayList;

import id.qibee.edu.qibeebakingguide.model.RecipeModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by masq2 on 16/09/2017.
 */

public interface ApiService {

    @GET("baking.json")
    Call<ArrayList<RecipeModel>> getRecipeResult();
}
