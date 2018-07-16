package com.example.practica.foodshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TableLayout table;
    private getData d;
    private ArrayList<Item> items;
    SwipeRefreshLayout Refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        d = new getData();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        table = (TableLayout) findViewById(R.id.table);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        Refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                d.getJSON("http://foodshopandroid.tk/main.php", String -> {
                    try {
                        loadIntoListView(String);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                Refresh.setRefreshing(false);
            }
        });
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainlayout);
        ImageView imageView = new ImageView(MainActivity.this);
        /*//add a image
        imageView.setImageResource(R.drawable.ic_menu_camera);
        LayoutParams imageViewLayoutParams
                = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(imageViewLayoutParams);
        mainLayout.addView(imageView);
        //add a text
        TextView text = new TextView(MainActivity.this);
        text.setText("asdasd");
        mainLayout.addView(text);*/
        Refresh.setRefreshing(true);
        d.getJSON("http://foodshopandroid.tk/main.php", String -> {
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
                TableRow tr = new TableRow(this);
                tr.setId(n);

                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                TextView name = new TextView(this);
                TextView text = new TextView(this);
                ImageView img = new ImageView(this);
                Button addcart = new Button(this);
                addcart.setText("Add to cart");
                addcart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                name.setText(i.getName());
                text.setText(i.getText());
                String picture = "http://foodshopandroid.tk/" + i.getImg();
                URL url = null;
                /*try {
                    url = new URL(picture);
                    try {
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        img.setImageBitmap(bmp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }*/
                tr.addView(name);
                tr.addView(text);
                tr.addView(img);
                tr.addView(addcart);
                table.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                //mainpage[n] = i.toString();
                n++;
            }
            /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mainpage);
            listView.setAdapter(arrayAdapter);*/
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart) {
            Intent intent = new Intent(this, cart.class);
            intent.putExtra("back", 0);
            startActivity(intent);
            return true;//kosarat meghivni
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                intent = new Intent(this, settings.class);
                startActivity(intent);
                break;
            case R.id.info:
                intent = new Intent(this, info.class);
                startActivity(intent);
                break;
            case R.id.dinner:
                intent = new Intent(this, dinner.class);
                startActivity(intent);
                break;
            case R.id.drinks:
                intent = new Intent(this, drinks.class);
                startActivity(intent);
                break;
            case R.id.breakfast:
                intent = new Intent(this, breakfast.class);
                startActivity(intent);
                break;
            case R.id.lunch:
                intent = new Intent(this, lunch.class);
                startActivity(intent);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
