package co.edu.manuelcardona.brsys;

import android.content.Intent;
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
    private static final String EXTRA_NAME = "co.edu.manuelcardona.brsys.NAME";
    private static final String EXTRA_LASTNAME = "co.edu.manuelcardona.brsys.LASTNAME";
    private static final String EXTRA_PHONE = "co.edu.manuelcardona.brsys.PHONE";
    private static final String EXTRA_ID = "co.edu.manuelcardona.brsys.ID";
    private static final String EXTRA_TYPEUSER = "co.edu.manuelcardona.brsys.TYPEUSER";

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
        String lastName = "";
        String phone = "";
        int id = 0;
        int type_user = 0;

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
                        String name = "desconocido";
                        String lastName = "desconocido";
                        String phone = "desconocido";
                        int id = 0;
                        int type_user = 0;
                        try {
                            JSONObject datos = response.getJSONObject("data");
                            name = datos.getString("name");
                            lastName = datos.getString("lastName");
                            phone = datos.getString("phone");
                            id = datos.getInt("id");
                            type_user = datos.getInt("type_user_id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String mensaje = "El usuario " + name + " " + lastName + " "+ type_user +" inicio correctamente.";

                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                        Log.i("post", "Logeado con éxito");

                        if(type_user == 3)
                        {
                            Intent intent = new Intent(getApplicationContext(), BarberActivity.class);
                            intent.putExtra(EXTRA_NAME, name);
                            intent.putExtra(EXTRA_LASTNAME, lastName);
                            intent.putExtra(EXTRA_PHONE, phone);
                            intent.putExtra(EXTRA_ID, id);
                            intent.putExtra(EXTRA_TYPEUSER, type_user);
                            startActivity(intent);
                        }

                        if(type_user == 4)
                        {
                            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                            intent.putExtra(EXTRA_NAME, name);
                            intent.putExtra(EXTRA_LASTNAME, lastName);
                            intent.putExtra(EXTRA_PHONE, phone);
                            intent.putExtra(EXTRA_ID, id);
                            intent.putExtra(EXTRA_TYPEUSER, type_user);
                            startActivity(intent);
                        }


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

                        if(response.statusCode == 404)
                        {
                            ((EditText) findViewById(R.id.tEmail)).setText("");
                            ((EditText) findViewById(R.id.tPassword)).setText("");

                            Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_LONG).show();
                        }

                        if(response.statusCode == 406)
                        {
                            ((EditText) findViewById(R.id.tEmail)).setText("");
                            ((EditText) findViewById(R.id.tPassword)).setText("");

                            Toast.makeText(getApplicationContext(), "Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                        }



                        //Log.e("post", error.getMessage());
                    }
                });

        mRequestQueue.add(request);

    }
}
