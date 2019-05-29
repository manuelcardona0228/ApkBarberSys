package co.edu.manuelcardona.brsys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class TurnBarber2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_barber2);

        Intent intent = getIntent();
        int id = intent.getIntExtra(BarberActivity.EXTRA_BARBER_ID, -1);
        int type_user = intent.getIntExtra(BarberActivity.EXTRA_BARBER_TYPEUSER, -1);

        EditText result = (EditText) findViewById(R.id.tRespuesta);
        result.setText("Este es el id: "+ id +" y el tipo de usuario: "+type_user);
    }
}
