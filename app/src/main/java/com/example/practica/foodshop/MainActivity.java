package com.example.practica.foodshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        table = (TableLayout) findViewById(R.id.table);
        table.setStretchAllColumns(true);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        d = new getData(items,table,displayMetrics,this,getResources());
        Refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        Refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                d.getJSON("http://foodshopandroid.tk/main.php");
                Refresh.setRefreshing(false);
            }
        });
        Refresh.setRefreshing(true);
        d.getJSON("http://foodshopandroid.tk/main.php");
        Refresh.setRefreshing(false);
    }

    /*private void loadIntoListView(String json) throws JSONException {
        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            items = new ArrayList<Item>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Item item = new Item(obj.getString("name"), obj.getString("text"), obj.getString("picture"), Double.parseDouble(obj.getString("price")));
                items.add(item);
            }
            int n = 0;
            table.removeAllViews();
            for (Item i : items) {
                TableRow tr = new TableRow(this);
                TableRow tr2 = new TableRow(this);
                tr.setId(n);
                tr2.setId(n);
                TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParams.setMargins(0, 0, 0, 0);
                tr.setPadding(0, 0, 0, 0);
                tr.setLayoutParams(trParams);
                tr2.setPadding(0, 0, 0, 0);
                tr2.setLayoutParams(trParams);
                TextView name = new TextView(this);
                ImageView img = new ImageView(this);
                Button addcart = new Button(this);
                TextView price = new TextView(this);
                Button more = new Button(this);
                addcart.setText("Add to cart");
                more.setText("More info");
                Drawable icon = ResourcesCompat.getDrawable(getResources(),R.drawable.cartb,null);
                icon.setBounds(0, 0, icon.getMinimumWidth(),
                        icon.getMinimumHeight());
                addcart.setCompoundDrawables(icon,null,null,null);
                addcart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                name.setText(i.getName());
                price.setText(Double.toString(i.getPrice()));
                String picture = "http://foodshopandroid.tk/" + i.getImg();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;
                Picasso.get().load(picture).resize(width/3,height/5).centerCrop().into(img);
                name.setGravity(Gravity.CENTER);
                img.setForegroundGravity(Gravity.LEFT);
                addcart.setGravity(Gravity.RIGHT);
                price.setGravity(Gravity.CENTER);
                TableRow.LayoutParams p = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
                p.span=3;
                more.setLayoutParams(p);
                tr.addView(name);
                tr.addView(more);
                tr2.addView(img);
                tr2.addView(price);
                tr2.addView(addcart);
                name.setTextSize(25);
                name.setTypeface(name.getTypeface(), Typeface.BOLD_ITALIC);
                price.setTextSize(25);
                price.setTypeface(name.getTypeface(), Typeface.BOLD_ITALIC);
                table.addView(tr, trParams);
                table.addView(tr2, trParams);
                n++;
            }
        }
    }*/

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
