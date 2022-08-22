package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    final Context context = this;

    private Button btnNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getData();

        btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    public void getData(){
        final ArrayList<Pacientes> pacientes = new ArrayList<>();

        AndroidNetworking.get("https://api-clinica-dental-info9-3.herokuapp.com/api/pacientes")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            Pacientes nuevoPaciente = new Pacientes();
                            JSONObject res = null;
                            try {
                                res = response.getJSONObject(i);
                                nuevoPaciente.setTelefono1(res.get("CelularPaciente").toString());
                                nuevoPaciente.setNotas(res.get("AlergiaMedicamentoPaciente").toString());
                                nuevoPaciente.setOcupacion(res.get("OcupacionPaciente").toString());
                                nuevoPaciente.setEstado(res.get("EstadoCivilPaciente").toString());
                                nuevoPaciente.setEmail(res.get("CorreoPaciente").toString());
                                nuevoPaciente.setNombre(res.get("NombrePaciente").toString());
                                nuevoPaciente.setDireccion(res.get("DireccionPaciente").toString());
                                nuevoPaciente.setEdad(res.get("Edad").toString());
                                nuevoPaciente.set_ID(res.get("idPaciente").toString());
                                nuevoPaciente.setSexo(res.get("Sexo").toString());

                                pacientes.add(nuevoPaciente);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("ERROR RESPUESTA", e.toString());
                                Toast.makeText(context, "Ocurrió un error cargando los datos", Toast.LENGTH_SHORT).show();
                            }
                        }

                        final MyArrayAdapter adapter = new MyArrayAdapter(context, R.layout.layout_paciente, pacientes);
                        ListView lv = findViewById(R.id.list);
                        lv.setAdapter(adapter);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("ERROR API", error.toString());
                        Toast.makeText(Dashboard.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class MyArrayAdapter extends ArrayAdapter<Pacientes> {
        Context context;
        int textViewRecursoId;
        ArrayList<Pacientes> objects;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Pacientes> objects){
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup){
            LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);
            TextView lblNombre = (TextView)view.findViewById(R.id.lblNombreContacto);
            TextView lblTelefono = (TextView)view.findViewById(R.id.lblTelefonoContacto);
            TextView lblNotas = view.findViewById(R.id.lblNotas);
            Button btnModificar = (Button)view.findViewById(R.id.btnModificar);
            Button btnBorrar = (Button)view.findViewById(R.id.btnBorrar);

            lblNombre.setText(objects.get(position).getNombre());
            lblTelefono.setText(objects.get(position).getTelefono1());
            lblNotas.setText(objects.get(position).getNotas());
//finish
            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(Dashboard.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Borrando registro")
                            .setMessage("¿Está seguro que desea borrar este registro?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AndroidNetworking.delete("https://api-clinica-dental-info9-3.herokuapp.com/api/pacientes/{id}")
                                            .addPathParameter("id", objects.get(position).get_ID())
                                            .build()
                                            .getAsJSONObject(new JSONObjectRequestListener() {
                                                @Override
                                                public void onResponse(JSONObject jsonObject) {
                                                    Toast.makeText(Dashboard.this, "Exito al borrar", Toast.LENGTH_SHORT).show();
                                                    objects.remove(position);
                                                    notifyDataSetChanged();
                                                }

                                                @Override
                                                public void onError(ANError anError) {
                                                    Toast.makeText(Dashboard.this, "Ocurrió un error al borrar", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });

            btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle oBundle = new Bundle();
                    oBundle.putSerializable("paciente", objects.get(position));
                    Intent i = new Intent();
                    i.putExtras(oBundle);
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            });

            return view;
        }
    }

}