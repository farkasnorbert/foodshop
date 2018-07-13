package com.example.practica.foodshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class cart extends AppCompatActivity {
    private int back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar =
                (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            back = bundle.getInt("back");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
