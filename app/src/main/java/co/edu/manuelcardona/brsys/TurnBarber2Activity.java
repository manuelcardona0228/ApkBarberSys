package co.edu.manuelcardona.brsys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TurnBarber2Activity extends AppCompatActivity {

    private RecyclerView myList;
    private RecyclerView.LayoutManager layoutManager;
    private TurnAdapter myAdapter;
    private List<Turn> myDataset = new ArrayList<>();
    private RequestQueue mRequestQueue;
    private String baseUrl = "http://192.168.0.18:8000/api/";
    private int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_barber2);

        mRequestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        id = intent.getIntExtra(BarberActivity.EXTRA_BARBER_ID, -1);
        int type_user = intent.getIntExtra(BarberActivity.EXTRA_BARBER_TYPEUSER, -1);

        //EditText result = (EditText) findViewById(R.id.tRespuesta);
        //result.setText("Este es el id: "+ id +" y el tipo de usuario: "+type_user);

        myList = (RecyclerView) findViewById(R.id.myList);
        //////////////
        myList.setHasFixedSize(true);
        //////

        layoutManager = new LinearLayoutManager(this);
        myList.setLayoutManager(layoutManager);

        //////
        myAdapter = new TurnAdapter(myDataset);
        myList.setAdapter(myAdapter);

        /////
        myAdapter.setClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int position = myList.indexOfChild(view);

                String id = ((TextView) view.findViewById(R.id.id)).getText().toString();
                String message = position +": "+ id;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        try {
            prepareDynamicData();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void prepareDynamicData() throws JSONException{

        String url = baseUrl + "turn/barber/"+id;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando ...");
        progressDialog.show();

        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(params),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        JSONArray turns = null;
                        try {
                            turns = response.getJSONArray("data");
                            //String message = "turn length: " + turns.length();
                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            if (myDataset.size() > 0) {
                                 myDataset.clear();
                            }

                            for (int i = 0; i < turns.length(); i++) {
                                JSONObject tmp = turns.getJSONObject(i);
                                Turn turn = new Turn(tmp.getInt("id"),
                                        tmp.getString("day"),
                                        tmp.getString("hour"),
                                        tmp.getInt("barber_id"),
                                        tmp.getInt("service_id"),
                                        tmp.getInt("customer_id"),
                                        tmp.getInt("state"));
                                //Toast.makeText(getApplicationContext(), "Turn: "+turn.getDay(), Toast.LENGTH_LONG).show();
                                myDataset.add(turn);
                            }
                            myAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Prueba", "MyDataset.size() : "+myDataset.size());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = -1;
                        NetworkResponse response = null;

                        if(error.networkResponse != null){
                            response = error.networkResponse;
                            statusCode = response.statusCode;
                            Toast.makeText(getApplicationContext(), "Error "+statusCode, Toast.LENGTH_LONG).show();
                        }
                    }
                });
        mRequestQueue.add(request);
    }

    public void onVolver(View view)
    {
        this.finish();
    }
}
