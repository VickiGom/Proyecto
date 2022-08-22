package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class AgregarPacientes extends AppCompatActivity {

    private Button btnGuardar, btnLimpiar, btnDashboard;
    private EditText txtNombre, txtCel, txtEmail, txtOcupacion, txtSexo, txtEstado, txtDireccion, txtNotas, txtEdad;
    private Pacientes pacienteGuardado;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pacientes);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnDashboard = findViewById(R.id.btnDash);

        txtNombre = findViewById(R.id.txtNombre);
        txtCel = findViewById(R.id.txtTelefono1);
        txtEmail = findViewById(R.id.txtCorreo);
        txtOcupacion = findViewById(R.id.txtOcupacion);
        txtEstado = findViewById(R.id.txtEstadoCivil);
        txtSexo = findViewById(R.id.txtSexo);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtNotas = findViewById(R.id.txtNotas);
        txtEdad = findViewById(R.id.txtEdad);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                    txtNombre.getText().toString().equals("") ||
                    txtCel.getText().toString().equals("") ||
                    txtEmail.getText().toString().equals("") ||
                    txtOcupacion.getText().toString().equals("") ||
                    txtEstado.getText().toString().equals("") ||
                    txtSexo.getText().toString().equals("") ||
                    txtDireccion.getText().toString().equals("") ||
                    txtNotas.getText().toString().equals("") ||
                    txtEdad.getText().toString().equals("")
                ){
                    Toast.makeText(AgregarPacientes.this, "Por favor rellene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Pacientes paciente = new Pacientes();
                paciente.setDireccion(txtDireccion.getText().toString());
                paciente.setNombre(txtNombre.getText().toString());
                paciente.setEmail(txtEmail.getText().toString());
                paciente.setEstado(txtEstado.getText().toString());
                paciente.setTelefono1(txtCel.getText().toString());
                paciente.setOcupacion(txtOcupacion.getText().toString());
                paciente.setSexo(txtSexo.getText().toString());
                paciente.setNotas(txtNotas.getText().toString());
                paciente.setEdad(txtEdad.getText().toString());

                if(pacienteGuardado == null){
                    AndroidNetworking.post("https://api-clinica-dental-info9-3.herokuapp.com/api/pacientes")
                            .addBodyParameter("AlergiaMedicamentoPaciente", paciente.getNotas())
                            .addBodyParameter("CelularPaciente", paciente.getTelefono1())
                            .addBodyParameter("CiudadPaciente", "")
                            .addBodyParameter("CodigoPostalPaciente", "")
                            .addBodyParameter("CorreoPaciente", paciente.getEmail())
                            .addBodyParameter("DireccionPaciente", paciente.getDireccion())
                            .addBodyParameter("Edad", paciente.getEdad())
                            .addBodyParameter("EstadoCivilPaciente", paciente.getEstado())
                            .addBodyParameter("FechaNacimientoPaciente", "")
                            .addBodyParameter("FotoPaciente", "")
                            .addBodyParameter("LugarNacimientoPaciente", "")
                            .addBodyParameter("NombrePaciente", paciente.getNombre())
                            .addBodyParameter("OcupacionPaciente", paciente.getOcupacion())
                            .addBodyParameter("Procedencia", "")
                            .addBodyParameter("Sexo", paciente.getSexo())
                            .addBodyParameter("TelefonoFijoPaciente", "")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // do anything with response
                                    Toast.makeText(AgregarPacientes.this, "Exito al crear paciente", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    Toast.makeText(AgregarPacientes.this, "Ocurrió un error al crear paciente", Toast.LENGTH_SHORT).show();
                                    Log.e("ERROR API", error.toString());
                                }
                            });
                } else {
                    AndroidNetworking.put("https://api-clinica-dental-info9-3.herokuapp.com/api/pacientes/{id}")
                            .addPathParameter("id", pacienteGuardado.get_ID())
                            .addBodyParameter("Sexo", paciente.getSexo())
                            .addBodyParameter("NombrePaciente", paciente.getNombre())
                            .addBodyParameter("OcupacionPaciente", paciente.getOcupacion())
                            .addBodyParameter("CorreoPaciente", paciente.getEmail())
                            .addBodyParameter("DireccionPaciente", paciente.getDireccion())
                            .addBodyParameter("Edad", paciente.getEdad())
                            .addBodyParameter("EstadoCivilPaciente", paciente.getEstado())
                            .addBodyParameter("AlergiaMedicamentoPaciente", paciente.getNotas())
                            .addBodyParameter("CelularPaciente", paciente.getTelefono1())
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Toast.makeText(AgregarPacientes.this, "Exito al actualizar paciente", Toast.LENGTH_SHORT).show();
                                    limpiar();
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(AgregarPacientes.this, "Ocurrió un error al crear paciente", Toast.LENGTH_SHORT).show();
                                    Log.e("ERROR API", anError.toString());
                                }
                            });
                }
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AgregarPacientes.this, Dashboard.class);
                limpiar();
                startActivityForResult(i, 0);
            }
        });
    }

    public void limpiar(){
        txtNombre.setText("");
        txtCel.setText("");
        txtEmail.setText("");
        txtEstado.setText("");
        txtOcupacion.setText("");
        txtSexo.setText("");
        txtDireccion.setText("");
        txtNotas.setText("");
        txtEdad.setText("");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(intent != null){
            Bundle oBundle = intent.getExtras();
            if(Activity.RESULT_OK == resultCode){
                Pacientes paciente = (Pacientes) oBundle.getSerializable("paciente");
                pacienteGuardado = paciente;
                id = paciente.get_ID();
                txtNombre.setText(paciente.getNombre());
                txtCel.setText(paciente.getTelefono1());
                txtEmail.setText(paciente.getEmail());
                txtEstado.setText(paciente.getEstado());
                txtOcupacion.setText(paciente.getOcupacion());
                txtSexo.setText(paciente.getSexo());
                txtDireccion.setText(paciente.getDireccion());
                txtNotas.setText(paciente.getNotas());
                txtEdad.setText(paciente.getEdad());
            }else{
                limpiar();
            }
        }
    }
}