package com.example.practica.foodshop;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class dinner extends AppCompatActivity {
    ListView listView;
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
        listView = (ListView) findViewById(R.id.listView);
        Refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        Refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                d.getJSON("http://foodshopandroid.tk/dinner.php", String -> {
                    try {
                        loadIntoListView(String);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                Refresh.setRefreshing(false);
            }
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
            String[] mainpage = new String[jsonArray.length()];
            items = new ArrayList<Item>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Item item = new Item(obj.getString("name"), obj.getString("text"), obj.getString("picture"));
                items.add(item);
            }
            int n = 0;
            for (Item i : items) {
                mainpage[n] = i.toString();
                n++;
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mainpage);
            listView.setAdapter(arrayAdapter);
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
