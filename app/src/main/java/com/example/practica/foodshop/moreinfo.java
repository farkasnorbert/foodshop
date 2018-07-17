package com.example.practica.foodshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class moreinfo extends AppCompatActivity {
    private int back=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinfo);
        TextView t = (TextView) findViewById(R.id.textView);
        Toolbar toolbar =
                (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        Item i;
        if (bundle != null) {
            back = bundle.getInt("back");
            i = (Item) bundle.get("Item");
            t.setText(i.getName());
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
