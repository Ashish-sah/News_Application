package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // static List<list_item> list=new ArrayList<>();
    static List<list_item> list = new ArrayList<>();
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Code to hide the status bar
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //To change the color of status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.front_Screen_background_color));

        fetchData();
    }

    private void fetchData() {
        String NewsUrl = "https://gnews.io/api/v4/search?q=example&token=6fd62d15cb116f9a7b62cf1b370a7ec8";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, NewsUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
         /*                   JSONArray   jsonArray = new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                list_item news = new list_item(
                                        //  newsDetail.getString("urlToImage"),
                                        jsonObject.getString("state"),
                                        //newsDetail.getString("description"),
                                        //newsDetail.getString("content"),
                                        jsonObject.getString("active"));
                                list.add(news);
                            }*/
                            JSONObject jsonObject = new JSONObject(response);
                            //to fetch the array data
                            JSONArray jsonArr = jsonObject.getJSONArray("articles");

                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject newsDetail = jsonArr.getJSONObject(i);
                                list_item news = new list_item(
                                        newsDetail.getString("image"),
                                        newsDetail.getString("title"),
                                        newsDetail.getString("description"),
                                        newsDetail.getString("content"),
                                        newsDetail.getString("url"));
                                list.add(news);
                            }
                            //added here so that is loads after everything is loaded json
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}