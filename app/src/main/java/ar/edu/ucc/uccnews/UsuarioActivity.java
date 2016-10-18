package ar.edu.ucc.uccnews;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.util.Date;
import java.util.Locale;

public class UsuarioActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "login";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        if(!sharedPreferences.getBoolean(PREFS_NAME,false))
        {

            Intent intent = new Intent(this, LoginA.class);
            startActivity(intent);

        }



    }




}
