package r.ah.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }

        setContentView(R.layout.activity_detail);

        TextView titletxt=findViewById(R.id.titletxt);
        TextView desc=findViewById(R.id.desc);
        TextView content=findViewById(R.id.content);
        TextView brand=findViewById(R.id.brand);
        ImageView image=findViewById(R.id.image);

        titletxt.setText(StoreClass.title);
        brand.setText(StoreClass.author);
        desc.setText(StoreClass.desc);
        content.setText(StoreClass.content);
        Glide.with(Detail_Activity.this).load(StoreClass.img).into(image);

    }

    public void back(View view) {
        finish();
    }

    public void shareNow(View view) {
        Intent i=new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, StoreClass.title+"\n"+StoreClass.desc);
        startActivity(Intent.createChooser(i,"Share via"));
    }
}