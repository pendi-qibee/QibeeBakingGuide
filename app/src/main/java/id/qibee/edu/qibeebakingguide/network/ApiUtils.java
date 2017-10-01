package id.qibee.edu.qibeebakingguide.network;

/**
 * Created by masq2 on 16/09/2017.
 */

public class ApiUtils {


    public static final String BASE_URL
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);

    }
}
