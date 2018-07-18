package com.example.practica.foodshop;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class cart extends AppCompatActivity {
    private int back;
    private Item i;
    private loadcart l;
    private SwipeRefreshLayout Refresh;
    private ArrayList<Bundle> d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Toolbar toolbar =
                (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            back = bundle.getInt("back");
            i = (Item) bundle.get("item");
        }
        l = new loadcart(this);
        Refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        Refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load(l);
            }
        });
        Refresh.setRefreshing(true);
        load(l);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cartmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart) {
            send();
        }else {
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
                case 5:
                    myIntent = new Intent(getApplicationContext(), moreinfo.class);
                    myIntent.putExtra("Item", i);
                    startActivityForResult(myIntent, 0);
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void load(loadcart l) {
        d = l.get();
        TableLayout table = (TableLayout) findViewById(R.id.table);
        table.removeAllViews();
        for (Bundle i : d) {
            if (i != null) {
                TableRow tr = new TableRow(this);
                TextView name = new TextView(this);
                TextView price = new TextView(this);
                Button delete = new Button(this);
                EditText number = new EditText(this);
                int id=i.getInt("id");
                Log.d("name2",i.getString("name"));
                number.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                number.setText("1");
                delete.setText("Delete");
                delete.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        l.delete(id);
                        Refresh.setRefreshing(true);
                        load(l);
                    }
                });
                name.setText(i.getString("name"));
                price.setText(Double.toString(i.getDouble("price")));
                tr.addView(name);
                tr.addView(price);
                tr.addView(number);
                tr.addView(delete);
                table.addView(tr);
            }
        }
        Refresh.setRefreshing(false);
    }
    private void send(){

    }
}
