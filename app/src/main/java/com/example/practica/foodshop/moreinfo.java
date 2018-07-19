package com.example.practica.foodshop;

import android.content.Context;
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
            Context c = this;
            addcart.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    loadcart l= new loadcart(c);
                    l.load(i);
                }
            });
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart) {
            Intent intent = new Intent(this, cart.class);
            intent.putExtra("back", 5);
            intent.putExtra("back2",back);
            intent.putExtra("item", i);
            startActivity(intent);
            return true;
        } else {
            Intent myIntent;
            switch (back) {
                case 0:
                    myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivityForResult(myIntent, 0);
                    return true;
                case 1:
                    myIntent = new Intent(getApplicationContext(), breakfast.class);
                    startActivityForResult(myIntent, 0);
                    return true;
                case 2:
                    myIntent = new Intent(getApplicationContext(), lunch.class);
                    startActivityForResult(myIntent, 0);
                    return true;
                case 3:
                    myIntent = new Intent(getApplicationContext(), dinner.class);
                    startActivityForResult(myIntent, 0);
                    return true;
                case 4:
                    myIntent = new Intent(getApplicationContext(), drinks.class);
                    startActivityForResult(myIntent, 0);
                    return true;
            }
            return true;
        }
    }
}
