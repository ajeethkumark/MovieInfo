package myapp.example.ajeethkumar.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {
    public static  String BASE_URL="https://api.themoviedb.org";
    public static int PAGE=1;
    public static String API_KEY="f7235b7ab0504d7fba50fbd504cc8bf8";
    public static String LANGUAGE="en-US";
    public static String CATEGORY="popular";
    StringBuilder movieContent=new StringBuilder();
    ArrayList<MovieResult.ResultsBean> data;
    RecyclerView recyclerMovieInfo;
    TextView movieInfo;
    MovieAdapter adapter;
    boolean flag=true;
    ProgressBar progressBar;
    Call<MovieResult> call;
    MovieResult mr;
     ApiInterface myInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieInfo=(TextView)findViewById(R.id.text_info);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        recyclerMovieInfo=(RecyclerView)findViewById(R.id.recycler_movie_info);
        data=new ArrayList<>();
        mr=new MovieResult();
        adapter=new MovieAdapter(data);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        myInterface=retrofit.create(ApiInterface.class);
        recyclerMovieInfo.setAdapter(adapter);
        // movieInfo.setText(movieContent.toString());
        recyclerMovieInfo.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
        //Glide.with(MainActivity.this).load("http://image.tmdb.org/t/p/w500//52lVqTDhIeNTjT7EiJuovXgw6iE.jpg").placeholder(R.drawable.images);
        //recyclerMovieInfo.hasFixedSize();
       /* adapter.setOnBottomReachedListener(new OnBottomReachedListener.Reached(){

            @Override
            public void click() {

            }
        });*/
        addindList();

       /* call=myInterface.listOfMovie(CATEGORY,API_KEY,LANGUAGE,PAGE);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                MovieResult result=response.body();
                List<MovieResult.ResultsBean> listofMovie=result.getResults();
               // MovieResult.ResultsBean firstMovie=listofMovie.get(1);
                Log.i("size...............",Integer.toString(listofMovie.size()));
                movieInfo.setText("first");
                for(int i=0;i<listofMovie.size();i++)
                {
                    MovieResult.ResultsBean firstMovie=listofMovie.get(i);
                 //   movieContent.append(firstMovie.getTitle()+"\n");
                    data.add(firstMovie);
                    Log.d("siva",""+new Gson().toJson(data));
                }
               // movieDisplay.setText(movieContent.toString());
                adapter=new MovieAdapter(data);
                recyclerMovieInfo.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                movieInfo.setText("error...check! do you have a propery connection");
            }
        });*/


    }
    public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


        ArrayList<MovieResult.ResultsBean> data=new ArrayList<>();
       // OnBottomReachedListener.Reached onBottomReachedListener;
        public MovieAdapter(ArrayList<MovieResult.ResultsBean> data) {
            if(!(this.data.size()>0)) {
                this.data = data;
            }
            else {
                this.data.addAll(data);
            }
        }

        @Override
        public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.displaydetails,parent, false);
            return new MovieAdapter.MovieViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MovieAdapter.MovieViewHolder holder, int position) {
            final int place=position;



            holder.movieName.setText(data.get(position).getTitle());
           // Glide.with(MainActivity.this).load("http://image.tmdb.org/t/p/w500/"+data.get(place).getBackdrop_path()).into(holder.movieImage);
          //  Glide.with(MainActivity.this).load(R.drawable.images).into(holder.movieImage);
           // Toast.makeText(MainActivity.this,data.get(place).getPoster_path(),Toast.LENGTH_SHORT).show();
            holder.movieImage.setImageResource(R.drawable.images);


            if(position==(data.size()-3))
            {
                if(PAGE<=50) {
                  //  onBottomReachedListener.click();
                    addindList();
                    progressBar.setVisibility(View.VISIBLE);                    // flag=true;
                }
                else
                {

                    Toast.makeText(MainActivity.this,"List is over...",Toast.LENGTH_LONG).show();
                }
            }
        }


       /* public void setOnBottomReachedListener(OnBottomReachedListener.Reached onBottomReachedListener)
        {
            this.onBottomReachedListener=onBottomReachedListener;
        }*/

        @Override
        public int getItemCount() {
            return data.size();
        }
        public  class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerMovieInfo.getChildLayoutPosition(view);
                MovieResult.ResultsBean item = data.get(itemPosition);
              //  Toast.makeText(MainActivity.this,data.get(itemPosition).getOverview(),Toast.LENGTH_LONG).show();
                Intent i=new Intent(MainActivity.this,DisplayMovieDetail.class);
                i.putExtra("title",data.get(itemPosition).getTitle());
                i.putExtra("date",data.get(itemPosition).getRelease_date());
                i.putExtra("url",data.get(itemPosition).getBackdrop_path());
                i.putExtra("vote",data.get(itemPosition).getVote_average());
                i.putExtra("overView",data.get(itemPosition).getOverview());
              //  i.putExtra("vote",data.get(itemPosition).getVote_average());
                //Toast.makeText(MainActivity.this,data.get(itemPosition).getVote_average()+"..."+data.get(itemPosition).getVote_count(),Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
            public TextView movieName;
            ImageView movieImage;
            public MovieViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                movieName=(TextView)itemView.findViewById(R.id.movie_detail_text);
                movieImage=(ImageView)itemView.findViewById(R.id.movie_image);

            }


        }
    }
    private void addindList() {
       // Toast.makeText(MainActivity.this,"calling",Toast.LENGTH_LONG).show();
        call=myInterface.listOfMovie(CATEGORY,API_KEY,LANGUAGE,PAGE);

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                PAGE++;

               // Toast.makeText(MainActivity.this,"sucess.....",Toast.LENGTH_LONG).show();
                MovieResult result=response.body();
                List<MovieResult.ResultsBean> listofMovie=result.getResults();
                // MovieResult.ResultsBean firstMovie=listofMovie.get(1);
                Log.i("size...............",Integer.toString(listofMovie.size()));
                for(int i=0;i<listofMovie.size();i++)
                {
                    MovieResult.ResultsBean firstMovie=listofMovie.get(i);
                    //   movieContent.append(firstMovie.getTitle()+"\n");
                    data.add(firstMovie);
                  //  Log.d("siva",""+new Gson().toJson(data));
                }
                // movieDisplay.setText(movieContent.toString());
                // adapter=new MovieAdapter(data);
                // recyclerMovieInfo.setAdapter(adapter);
                // recyclerMovieInfo.invalidate();
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                Log.i("length.....",Integer.toString(data.size()));


            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                //  movieInfo.setText("error...check! do you have a propery connection");
                Toast.makeText(MainActivity.this,"connection fail......",Toast.LENGTH_LONG).show();
                movieInfo.setText("error...check! do you have a propery connection");
                progressBar.setVisibility(View.INVISIBLE);
                //PAGE--;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       // PAGE=1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PAGE=1;
    }
}
