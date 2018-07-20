package com.example.practica.foodshop;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

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
        EditText a = (EditText) findViewById(R.id.address);
        a.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    if (send()) {
                        startActivityForResult(myIntent, 0);
                    }
                    return true;
                }
                return false;
            }
        });
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
                item.put("name", name.getText().toString());
                item.put("phone", phone.getText().toString());
                item.put("address", address.getText().toString());
                item.put("id", i.getInt("id"));
                item.put("iname", i.getString("name"));
                item.put("price", i.getDouble("price"));
                item.put("number", i.getString("number"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            items.put(item);
            JSONObject p = new JSONObject();
            try {
                p.put("items", items.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = p.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = null;
                try {
                    value = p.get(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (first)
                    first = false;
                else
                    result.append("&");

                try {
                    result.append(URLEncoder.encode(key, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                result.append("=");
                try {
                    result.append(URLEncoder.encode(value.toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
            new SendPostRequest().execute(result.toString());
        }
        Log.d("items", items.toString());
        loadcart l = new loadcart(this);
        l.drop();
        return true;
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://foodshopandroid.tk/order.php"); // here is your URL path
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(arg0[0]);

                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("result",result);
            Toast.makeText(getApplicationContext(), "Order sent",
                    Toast.LENGTH_LONG).show();
        }
    }

}