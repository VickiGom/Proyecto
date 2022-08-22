package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class UsuariosActivity extends AppCompatActivity {
    RecyclerView recy;
    String s1[], s2[], s3[];
    int images[] = {R.drawable.a01,R.drawable.a02,R.drawable.a03,R.drawable.a04,R.drawable.a10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        recy = (RecyclerView)findViewById(R.id.recy);
        s1 = getResources().getStringArray(R.array.Usuarios);
        s2 = getResources().getStringArray(R.array.Telefono);
        s3 = getResources().getStringArray(R.array.Malestar);
        MyAdapter myAdapter = new MyAdapter(this,s1,s2,s3,images);
        recy.setAdapter(myAdapter);
        recy.setLayoutManager(new LinearLayoutManager(this));
    }
}