package com.example.practica.foodshop;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class sendcart extends AppCompatActivity {
    private int back;
    private int back2;
    private Item i;
    private ArrayList<Bundle> d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendcart);
        Toolbar toolbar =
                (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            back = bundle.getInt("back");
            d = bundle.getParcelableArrayList("cart");
            i = (Item) bundle.get("item");
            back2 = bundle.getInt("back2");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cartmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.send) {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            if (send()) {
                startActivityForResult(myIntent, 0);
            }
            return true;
        } else {
            Intent myIntent = new Intent(getApplicationContext(), cart.class);
            myIntent.putExtra("back", back);
            myIntent.putExtra("item", i);
            myIntent.putExtra("back2", back2);
            startActivity(myIntent);
            return true;
        }
    }

    private boolean send() {
        EditText name = (EditText) findViewById(R.id.name);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText address = (EditText) findViewById(R.id.address);
        if (name.getText().toString().matches("")) {
            Toast.makeText(this, "You did not enter your name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.getText().toString().matches("")) {
            Toast.makeText(this, "You did not enter your phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address.getText().toString().matches("")) {
            Toast.makeText(this, "You did not enter your address", Toast.LENGTH_SHORT).show();
            return false;
        }
        JSONArray items = new JSONArray();
        for (Bundle i : d) {
            JSONObject item = new JSONObject();
            try {
                item.put("name",name.getText().toString());
                item.put("phone",phone.getText().toString());
                item.put("address",address.getText().toString());
                item.put("id",i.getInt("id"));
                item.put("iname",i.getString("name"));
                item.put("price",i.getDouble("price"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            items.put(item);
        }
        //new HttpAsyncTask().execute("http://foodshopandroid.tk/order.php",);

        loadcart l = new loadcart(this);
        l.drop();
        return true;
    }
    /*private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            /*person = new Person();
            person.setName(etName.getText().toString());
            person.setCountry(etCountry.getText().toString());
            person.setTwitter(etTwitter.getText().toString());

            return; //POST(urls[0],person);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }*/
}