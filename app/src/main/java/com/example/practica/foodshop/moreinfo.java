package com.example.practica.foodshop;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class moreinfo extends AppCompatActivity {
    private int back=0;
    private Item i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfo);
        TextView t = (TextView) findViewById(R.id.textView);
        Toolbar toolbar =
                (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            back = bundle.getInt("back");
            i = (Item) bundle.get("Item");
            ImageView img = (ImageView) findViewById(R.id.imageView);
            String picture = "http://foodshopandroid.tk/" + i.getImg();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            Picasso.get().load(picture).resize(width,height/2).centerCrop().into(img);
            this.setTitle(i.getName());
            t.setText(i.getText());
            t.setTextSize(20);
            TextView price = (TextView) findViewById(R.id.textView2);
            price.setText("Price:"+Double.toString(i.getPrice()));
            price.setTextSize(25);
            price.setTypeface(price.getTypeface(), Typeface.BOLD_ITALIC);
            Button addcart = (Button) findViewById(R.id.button) ;
            Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.cartb,null);
            icon.setBounds(0, 0, icon.getMinimumWidth(),
                    icon.getMinimumHeight());
            addcart.setCompoundDrawables(icon,null,null,null);
            addcart.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                }
            });
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart) {
            Intent intent = new Intent(this, cart.class);
            intent.putExtra("back", 5);
            intent.putExtra("item", i);
            startActivity(intent);
            return true;
        } else {
            String s = "";
            Class<?> myclass = null;
            switch (back){
                case 0: s="MainActivity";
                break;
                case 1: s="breakfast";
                    break;
                case 2: s="lunch";
                    break;
                case 3: s="dinner";
                    break;
                case 4: s="drinks";
                    break;
            }
            try {
                myclass = Class.forName("com.example.practica.foodshop."+s);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            Intent myIntent = new Intent(getApplicationContext(), myclass);
            startActivityForResult(myIntent, 0);
            /*Intent myIntent;
            myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);*/
            return true;
        }
    }
}
