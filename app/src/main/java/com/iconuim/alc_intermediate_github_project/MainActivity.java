package com.iconuim.alc_intermediate_github_project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static RecyclerView devsRecyclerView;
    private static RecyclerView.Adapter devsRecyclerViewAdapter;
    private static RecyclerView.LayoutManager devsRecyclerViewLayoutManager;
    private static String URL = "https://api.github.com/search/users?q=location:lagos+language:java";
    private static Gson gson = new Gson();
    private static Devs devs;
    private static ArrayList<GitDev> devsTemp = new ArrayList<GitDev>();
    private static ProgressDialog dialog;
    // private static List<String> devsTemp = ;
    ListView listView;
    JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listView = (ListView) findViewById(R.id.devsListView);
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();


        //       devsRecyclerView = (RecyclerView) findViewById(R.id.devs_recycler_view);
        //       devsRecyclerViewLayoutManager = new LinearLayoutManager(MainActivity.this);
        //       devsRecyclerView.setLayoutManager(devsRecyclerViewLayoutManager);

        //create new requestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        ApiRequest apiRequest = new ApiRequest(Request.Method.GET, URL, Devs.class, null,

                new Response.Listener<Devs>() {
                    @Override
                    public void onResponse(Devs _devs) {
                        devsTemp.clear();
                        devs = _devs;

                        for (GitDev d : devs.getItems()) {
                            devsTemp.add(d);
                        }

                        // create instance of the adapter
                        final DevsListViewAdapter devsListViewAdapter = new DevsListViewAdapter(MainActivity.this, R.layout.devs_card, devsTemp);

                        //bind adapter to listview
                        listView.setAdapter(devsListViewAdapter);

                        //notify adapter about new data
                        devsListViewAdapter.notifyDataSetChanged();

                        dialog.dismiss();

                        //   devsRecyclerViewAdapter = new DevsRecyclerViewAdapter(getApplication().getApplicationContext(), devs.getItems());
                        //   devsRecyclerView.setAdapter(devsRecyclerViewAdapter);


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }


        );


        requestQueue.add(apiRequest);


        // DevsListViewAdapter devsListViewAdapter =  ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
