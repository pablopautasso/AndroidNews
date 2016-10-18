package ar.edu.ucc.uccnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;


public class ActivityRegistro extends AppCompatActivity implements Validator.ValidationListener {

    private Spinner sp1;
    private Button btnOK, btnCancel;
    @NotEmpty(message = "Campo obligatorio")
    private EditText etNombre;
    @NotEmpty(message = "Campo obligatorio")
    private EditText etapellido;
    @Email(message = "Email invalido")
    private EditText etMail;
    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC, message = "Minimo 8 caracteres,alfanumérico")
    private EditText etContraseña;
    @ConfirmPassword(message = "Las contraseñas no coinsiden")
    private EditText etRContraseña;
    @NotEmpty(message = "Campo obligatorio")
    private EditText etClave;
    private ArrayAdapter<CharSequence> adapter;

    Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_registro);

        sp1 = (Spinner) findViewById(R.id.sp01);

        adapter = ArrayAdapter.createFromResource(this, R.array.Ociones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                          @Override
                                          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                              Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " Seleccionado", Toast.LENGTH_LONG).show();
                                          }


                                          @Override
                                          public void onNothingSelected(AdapterView<?> parent) {

                                          }
                                      }
        );

        validator = new Validator(this);
        validator.setValidationListener(this);

        ////////////////////////Linkojets/////////////////////////////
        etNombre = (EditText) findViewById(R.id.etNombre);
        etapellido = (EditText) findViewById(R.id.etApellido);
        etClave = (EditText) findViewById(R.id.etClaveA);
        etMail = (EditText) findViewById(R.id.etEmail);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        etRContraseña = (EditText) findViewById(R.id.etPassC);


        ///////////////////Botone Cancelar///////////////////////
        btnCancel = (Button) findViewById(R.id.btCancelar);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ///////////////////Boton OK///////////////////////

        btnOK = (Button) findViewById(R.id.btOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    ///////////////Validacion//////////
    @Override
    public void onValidationSucceeded() {
        Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();

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
