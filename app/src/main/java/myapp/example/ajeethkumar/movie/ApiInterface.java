package myapp.example.ajeethkumar.movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



/**
 * Created by Ajeethkumar on 12/16/2017.
 */

public interface ApiInterface {
    @GET("/3/movie/{category}")
    Call<MovieResult> listOfMovie(
            @Path("category") String categoty,
            @Query("api_key") String apikey,
            @Query("language") String language,
            @Query("page") int page

    );
}
