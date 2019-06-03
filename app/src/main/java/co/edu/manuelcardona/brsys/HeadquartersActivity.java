package co.edu.manuelcardona.brsys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeadquartersActivity extends AppCompatActivity {

    private int id = 0;
    /////
    private RecyclerView myList;
    private RecyclerView.LayoutManager layoutManager;
    private HeadquarterAdapter myAdapter;
    private List<Headquarter> myDataset = new ArrayList<>();

    private RequestQueue mRequestQueue;
    private String baseUrl = "http://192.168.0.18:8000/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headquarter);


        mRequestQueue = Volley.newRequestQueue(this);
        ////

        Intent intent = getIntent();
        id = intent.getIntExtra(UserActivity.EXTRA_ID_BARBERSHOP, -1);
        Toast.makeText(this,"id: "+id, Toast.LENGTH_LONG).show();

        myList = (RecyclerView) findViewById(R.id.myListHeadquarter);

        //
        //
        layoutManager = new LinearLayoutManager(this);
        myList.setLayoutManager(layoutManager);

        //
        myAdapter = new HeadquarterAdapter(myDataset);
        myList.setAdapter(myAdapter);

        //
        myAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = myList.indexOfChild(view);

                String businesName = ((TextView) view.findViewById(R.id.businessNameH)).getText().toString();
                String message = position +": "+ businesName;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        try {
            prepareData();
        }catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    private void prepareData() throws JSONException {
        String url = baseUrl + "barbershop/headquarter/"+id;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando ...");
        progressDialog.show();

        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        JSONArray headquarters = null;

                        try {
                            headquarters = response.getJSONArray("data");
                            Log.e("Size", "onResponse: "+headquarters.getJSONObject(0).getString("businessName"));
                            if (myDataset.size() > 0) {
                                myDataset.clear();
                            }

                            for (int i = 0; i < headquarters.length(); i++) {
                                Log.e("Size", "onEntre: ");
                                JSONObject tmp = headquarters.getJSONObject(i);
                                Headquarter headquarter = new Headquarter(tmp.getString("businessName"),
                                        tmp.getString("address"), tmp.getString("phone"),
                                        (float) tmp.getDouble("longitude"), (float) tmp.getDouble("latitude"));
                                myDataset.add(headquarter);
                            }

                            myAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Size", "onSize: "+myDataset.size());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = -1;
                        NetworkResponse response = null;

                        if(error.networkResponse != null)
                        {
                            response = error.networkResponse;
                            statusCode = response.statusCode;
                        }
                    }
                });
        mRequestQueue.add(request);
    }
}

