package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private Button buttonIngresar;
    private EditText txtUser, txtPass;
    private String user, pass;
    private boolean logged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonIngresar = (Button) findViewById(R.id.buttonIngresar);
        txtUser = findViewById(R.id.editTextUser);
        txtPass = findViewById(R.id.editTextPassword);


        buttonIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logged = false;
                AndroidNetworking.get("https://api-clinica-dental-info9-3.herokuapp.com/api/doctor")
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i = 0; i < response.length(); i++){
                                JSONObject res = null;
                                try {
                                    res = response.getJSONObject(i);
                                    if(res.get("UsuarioDoctor").toString().equals(txtUser.getText().toString()) &&
                                        res.get("Contraseña").toString().equals(txtPass.getText().toString())){

                                        logged = true;
                                        Intent intent = new Intent(MainActivity.this,AgregarPacientes.class);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            if(!logged){
                                Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.e("ERROR API", error.toString());
                            Toast.makeText(MainActivity.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }

}
