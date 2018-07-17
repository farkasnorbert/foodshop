package com.example.practica.foodshop;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class load extends Activity {
    public load() {
    }

    public void loadIntoViews(String json, ArrayList<Item> items, TableLayout table, DisplayMetrics displayMetrics,final Context context, Resources resurce,int back) throws JSONException {
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
                TableRow tr = new TableRow(context);
                TableRow tr2 = new TableRow(context);
                tr.setId(n);
                tr2.setId(n);
                TableLayout.LayoutParams trParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                trParams.setMargins(0, 0, 0, 0);
                tr.setPadding(0, 0, 0, 0);
                tr.setLayoutParams(trParams);
                tr2.setPadding(0, 0, 0, 0);
                tr2.setLayoutParams(trParams);
                TextView name = new TextView(context);
                ImageView img = new ImageView(context);
                Button addcart = new Button(context);
                TextView price = new TextView(context);
                Button more = new Button(context);
                addcart.setText("Add to cart");
                more.setText("More info");
                Drawable icon = ResourcesCompat.getDrawable(resurce, R.drawable.cartb,null);
                icon.setBounds(0, 0, icon.getMinimumWidth(),
                        icon.getMinimumHeight());
                addcart.setCompoundDrawables(icon,null,null,null);
                addcart.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {

                    }
                });
                more.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, moreinfo.class);
                        intent.putExtra("Item",i);
                        intent.putExtra("back",back);
                        context.startActivity(intent);
                    }
                });
                name.setText(i.getName());
                price.setText(Double.toString(i.getPrice()));
                String picture = "http://foodshopandroid.tk/" + i.getImg();
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
    }
}
