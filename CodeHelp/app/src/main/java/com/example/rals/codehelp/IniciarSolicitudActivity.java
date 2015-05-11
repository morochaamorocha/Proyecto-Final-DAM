package com.example.rals.codehelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rals.codehelp.model.Solicitud;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class IniciarSolicitudActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private Firebase mSolicitudesRef, nuevaSolicitudRef;
    private String categoria;
    private EditText txtTitulo, txtDescripcion;
    private Button btnIniciarSolic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_solicitud);

        spinner = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        txtTitulo = (EditText)findViewById(R.id.txtTitulo);

        txtDescripcion = (EditText)findViewById(R.id.txtDescripcion);

        btnIniciarSolic = (Button)findViewById(R.id.btnIniciarSolicitud);
        btnIniciarSolic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Iniciar una nueva solicitud...
                mSolicitudesRef = Const.ref.child("solicitudes");
                nuevaSolicitudRef = mSolicitudesRef.push();

                Solicitud solicitud = new Solicitud();
                solicitud.setTitulo(txtTitulo.getText().toString());
                solicitud.setDescripcion(txtDescripcion.getText().toString());
                solicitud.setCategoria(categoria);
                solicitud.setIdCliente(Const.gAuthData.getUid());
                solicitud.setAbierta(true);

                nuevaSolicitudRef.setValue(solicitud, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                        //Iniciamos servicio...
                        PrecenseService.iniciarServicio(getApplicationContext(), nuevaSolicitudRef.getKey());

                        Toast.makeText(getApplicationContext(), "Solicitud creada. Buscando un Experto", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iniciar_solicitud, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categoria = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
