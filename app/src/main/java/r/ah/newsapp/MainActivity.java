package r.ah.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<ModelClass> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.status));
        }
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsAPI newsAPI=retrofit.create(NewsAPI.class);

        Call<MyJSONResponse> call=newsAPI.getData();

        call.enqueue(new Callback<MyJSONResponse>() {
            @Override
            public void onResponse(Call<MyJSONResponse> call, Response<MyJSONResponse> response) {
                MyJSONResponse myJSONResponse=response.body();
                list=new ArrayList<>(Arrays.asList(myJSONResponse.getDataarray()));
                putDataIntoRecyclerView(list);
            }

            @Override
            public void onFailure(Call<MyJSONResponse> call, Throwable t) {

            }
        });

    }

    void putDataIntoRecyclerView(List<ModelClass> datalist)
    {
        NewsAdapter adapter=new NewsAdapter(this,datalist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }


    public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        Context context;
        List<ModelClass> datalist;

        public NewsAdapter(Context context, List<ModelClass> datalist) {
            this.context = context;
            this.datalist = datalist;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.news_adapter, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.news_title.setText(datalist.get(position).getTitle());
            holder.news_desc.setText(datalist.get(position).getDescription());
            holder.news_author.setText(datalist.get(position).getAuthor());
            Glide.with(context).load(datalist.get(position).getUrlToImage()).into(holder.image);

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StoreClass.author=datalist.get(position).getAuthor();
                    StoreClass.desc=datalist.get(position).getDescription();
                    StoreClass.img=datalist.get(position).getUrlToImage();
                    StoreClass.content=datalist.get(position).getContent();
                    StoreClass.title=datalist.get(position).getTitle();
                    startActivity(new Intent(context,Detail_Activity.class));
                }
            });

        }

        @Override
        public int getItemCount() {
            return datalist.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView news_title,news_desc,news_author;
            ImageView image;
            LinearLayout layout;
            ViewHolder(View itemView) {
                super(itemView);
                news_title = itemView.findViewById(R.id.news_title);
                news_desc = itemView.findViewById(R.id.news_desc);
                news_author = itemView.findViewById(R.id.news_author);
                image=itemView.findViewById(R.id.image);
                layout=itemView.findViewById(R.id.layout);
            }
        }
    }

}