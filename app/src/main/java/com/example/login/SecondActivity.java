package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    ImageView imaPa;
    Button btnSalir;
    Button btnMod;
    Button btnBorrar;
    EditText editNombre;
    EditText editTel;
    EditText editCaso;
    EditText editTra;
    EditText editObs;
    String data1, data2,data3;
    int img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imaPa = (ImageView) findViewById(R.id.imaPa);
        btnSalir= (Button) findViewById(R.id.btnSalir);
        btnMod = (Button) findViewById(R.id.btnModi);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editTel  = (EditText) findViewById(R.id.editNum);
        editCaso = (EditText) findViewById(R.id.editMales);
        editTra = (EditText) findViewById(R.id.editTra);
        editObs = (EditText) findViewById(R.id.editObs);
        getData();
        setData();
    }
    private void getData(){
        if(getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2") &&
                getIntent().hasExtra("data3")){
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            data3 = getIntent().getStringExtra("data3");
            img = getIntent().getIntExtra("myImage",1);
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    private void setData(){
        editNombre.setText(data1);
        editTel.setText(data2);
        editCaso.setText(data3);
        imaPa.setImageResource(img);
    }
}