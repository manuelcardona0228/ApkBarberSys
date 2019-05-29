package co.edu.manuelcardona.brsys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private String baseUrl = "http://192.168.0.18:8000/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRequestQueue = Volley.newRequestQueue(this);
    }

    public void onLogin(View view)
    {
        String localUrl = baseUrl + "login";
        String name = "";
        String email = ((EditText) findViewById(R.id.tEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.tPassword)).getText().toString();

        HashMap<String, String> params = new HashMap<>();

        params.put("email", email);
        params.put("password", password);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, localUrl,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        String phone = "desconocido";

                        try {
                            JSONObject datos = response.getJSONObject("data");
                            phone = datos.getString("phone");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String mensaje = "El usuario " + phone + " inicio correctamente.";

                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                        Log.i("post", "Logeado con éxito");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        int statusCode = -1;
                        NetworkResponse response = null;

                        if(error.networkResponse != null)
                        {
                            response = error.networkResponse;
                            statusCode = response.statusCode;
                        }

                        Log.e("post", error.getMessage());
                    }
                });

        mRequestQueue.add(request);

    }
}
