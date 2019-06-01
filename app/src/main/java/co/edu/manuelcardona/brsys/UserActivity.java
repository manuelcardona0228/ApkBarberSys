package co.edu.manuelcardona.brsys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class UserActivity extends AppCompatActivity {

    private String name = "";
    String lasName = "";
    String phone = "";
    int id = 0;
    int type_user = 0;

    public static final String EXTRA_USER_ID = "co.edu.manuelcardona.brsys.USER_ID";
    public static final String EXTRA_USER_TYPEUSER = "co.edu.manuelcardona.brsys.USER_TYPEUSER";

    private RecyclerView myListBarbershops;
    private RecyclerView.LayoutManager layoutManager;
    private BarbershopAdapter myAdapter;
    private List<Barbershop> myDataset = new ArrayList<>();

    private RequestQueue mRequestQueue;
    private String baseUrl = "http://192.168.0.18:8000/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mRequestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        name = intent.getStringExtra(LoginActivity.EXTRA_NAME);
        lasName = intent.getStringExtra(LoginActivity.EXTRA_LASTNAME);
        phone = intent.getStringExtra(LoginActivity.EXTRA_PHONE);
        id = intent.getIntExtra(LoginActivity.EXTRA_ID, -1);
        type_user = intent.getIntExtra(LoginActivity.EXTRA_TYPEUSER, -1);
        Toast.makeText(this, "Bienvenido "+ name + " "+ lasName, Toast.LENGTH_LONG).show();
        //TextView welcome = (TextView) findViewById(R.id.tUserWelcome);
        //welcome.setText("Bienvenido "+ name + " "+ lasName);

        myListBarbershops = (RecyclerView) findViewById(R.id.myListBarbershops);
        ///////
        //////

        myListBarbershops.setHasFixedSize(true);
        ///

        layoutManager = new LinearLayoutManager(this);
        myListBarbershops.setLayoutManager(layoutManager);

        ////
        myAdapter = new BarbershopAdapter(myDataset);
        myListBarbershops.setAdapter(myAdapter);

        ///
        myAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = myListBarbershops.indexOfChild(view);

                String businessName = ((TextView) view.findViewById(R.id.businessName)).getText().toString();
                String message = position + ": " + businessName;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        try {
            prepareData();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void prepareData() throws JSONException{

        String url = baseUrl + "barbershops";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando ...");
        progressDialog.show();

        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        JSONArray barbershops = null;

                        try {
                            barbershops = response.getJSONArray("data");
                            if (myDataset.size() > 0) {
                                myDataset.clear();
                            }

                            for (int i = 0; i < barbershops.length(); i++) {
                                JSONObject tmp = barbershops.getJSONObject(i);
                                Barbershop barbershop = new Barbershop(tmp.getString("businessName"));
                                myDataset.add(barbershop);
                            }

                            myAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
