package r.ah.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {

    @GET("v2/everything?q=keyword&apiKey=8d8a44572df9437ba803bfb85b1a4bfe")
    Call<MyJSONResponse> getData();
}
