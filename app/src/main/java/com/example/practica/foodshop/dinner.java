package com.example.practica.foodshop;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class dinner extends AppCompatActivity {
    TableLayout table;
    private getData d;
    private ArrayList<Item> items;
    SwipeRefreshLayout Refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);
        Toolbar toolbar =
                (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        d = new getData();
        table = (TableLayout) findViewById(R.id.table);
        Refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        Refresh.setOnRefreshListener(() -> {
            table.removeAllViews();
            d.getJSON("http://foodshopandroid.tk/dinner.php", String -> {
                try {
                    loadIntoListView(String);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            Refresh.setRefreshing(false);
        });
        Refresh.setRefreshing(true);
        d.getJSON("http://foodshopandroid.tk/dinner.php", String -> {
            try {
                loadIntoListView(String);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        Refresh.setRefreshing(false);
    }

    private void loadIntoListView(String json) throws JSONException {
        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            items = new ArrayList<Item>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Item item = new Item(obj.getString("name"), obj.getString("text"), obj.getString("picture"),Double.parseDouble(obj.getString("price")));
                items.add(item);
            }
            int n = 0;
            for (Item i : items) {
                TableRow tr = new TableRow(this);
                tr.setId(n);

                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                TextView name = new TextView(this);
                TextView text = new TextView(this);
                ImageView img = new ImageView(this);
                Button addcart = new Button(this);
                TextView price = new TextView(this);
                //addcart.setText("Add to cart");
                addcart.setBackgroundResource(R.drawable.cartb);
                addcart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                name.setText(i.getName());
                text.setText(i.getText());
                price.setText(Double.toString(i.getPrice()));
                String picture = "http://foodshopandroid.tk/" + i.getImg();
                Picasso.get().load(picture).resize(500, 500).centerCrop().into(img);
                tr.addView(name);
                tr.addView(text);
                tr.addView(img);
                tr.addView(price);
                tr.addView(addcart);
                table.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                n++;
            }
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
            intent.putExtra("back", 3);
            startActivity(intent);
            return true;
        } else {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
            return true;
        }

    }
}
