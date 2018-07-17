package com.example.practica.foodshop;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.widget.TableLayout;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class getData {
    private ArrayList<Item> items;
    private TableLayout table;
    private DisplayMetrics displayMetrics;
    private Context context;
    private Resources resurce;
    private load l;

    public getData(ArrayList<Item> items, TableLayout table, DisplayMetrics displayMetrics, Context context, Resources resurce) {
        this.items = items;
        this.table = table;
        this.displayMetrics = displayMetrics;
        this.context = context;
        this.resurce = resurce;
        l = new load();
    }

    public void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //metod.set(s);
                try {
                    l.loadIntoViews(s, items, table, displayMetrics, context, resurce);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

}
