package co.edu.manuelcardona.brsys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    private String name = "";
    String lasName = "";
    String phone = "";
    int id = 0;
    int type_user = 0;

    public static final String EXTRA_USER_ID = "co.edu.manuelcardona.brsys.USER_ID";
    public static final String EXTRA_USER_TYPEUSER = "co.edu.manuelcardona.brsys.USER_TYPEUSER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        name = intent.getStringExtra(LoginActivity.EXTRA_NAME);
        lasName = intent.getStringExtra(LoginActivity.EXTRA_LASTNAME);
        phone = intent.getStringExtra(LoginActivity.EXTRA_PHONE);
        id = intent.getIntExtra(LoginActivity.EXTRA_ID, -1);
        type_user = intent.getIntExtra(LoginActivity.EXTRA_TYPEUSER, -1);

        TextView welcome = (TextView) findViewById(R.id.tUserWelcome);
        welcome.setText("Bienvenido "+ name + " "+ lasName);
    }

    public void onTerminar(View view) { this.finish(); }
}
