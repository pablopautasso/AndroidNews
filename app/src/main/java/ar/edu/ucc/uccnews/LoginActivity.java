package ar.edu.ucc.uccnews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {


    ////////////////////////////////////
    @NotEmpty(message = "Campo Obligatorio!!")
    private EditText Usuario;
    @NotEmpty(message = "Campo Obligatorio!!")
    private EditText Contraseña;

    TextView registroLink;

    Button btnInicio;
    Validator validator;
    Thread n;
    Conexion cn;
    Intent intent_registro;
    Intent intent;
    ////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //intent = new Intent(this, UsuarioActivity.class);
        validator = new Validator(this);
        validator.setValidationListener(this);
        cn = new Conexion(this);

        Contraseña = (EditText) findViewById(R.id.editTextContraseña);
        Usuario = (EditText) findViewById(R.id.editTextUsuario);


        btnInicio = (Button) findViewById(R.id.btnInicio);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });


        registroLink = (TextView) findViewById(R.id.tvRegis);
        registroLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_registro = new Intent(LoginActivity.this,ActivityRegistro.class);
                startActivity(intent_registro);
            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/uccnews");
    }
    @Override
    public void onValidationSucceeded() {

        String usuario  = Usuario.getText().toString();
        String contraseña = Contraseña.getText().toString();

        Log.d("validation", "user: " + usuario + "contraseña: " + contraseña);
        Log.d("validation", String.valueOf(cn.consultar(usuario,contraseña)));

        if(cn.consultar(usuario,contraseña))
        {
            Toast.makeText(this,"Datos correctos. Iniciando sesion...",Toast.LENGTH_SHORT).show();
            Log.d("validation", "dentro del consultar");
            intent = new Intent(LoginActivity.this,UsuarioActivity.class);
            startActivity(intent);
        }
        else {
            Log.d("validation", "dentro del else consultar");
            Toast.makeText(this, "Datos erroneos. Vuelva a ingresarlos", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }

        }
    }



}
