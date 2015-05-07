package com.example.rals.codehelp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rals.codehelp.model.Usuario;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;


public class RegisterActivity extends Activity implements View.OnClickListener{

    private Button btnRegistrarse;
    private EditText txtNombre, txtApellido, txtEmail, txtPass, txtCompany, txtLeng, txtExp;
    private Firebase usersRef;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Registrarse");


        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtApellido = (EditText)findViewById(R.id.txtApellido);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPass = (EditText)findViewById(R.id.txtPassword);
        txtCompany = (EditText)findViewById(R.id.txtCompany);
        txtLeng = (EditText)findViewById(R.id.txtLeng);
        txtExp = (EditText)findViewById(R.id.txtExp);

        btnRegistrarse = (Button)findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnRegistrarse:


                    Const.ref.createUser(txtEmail.getText().toString(), txtPass.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringObjectMap) {

                            usersRef = Const.ref.child("users").child(stringObjectMap.get("uid").toString());
                            usuario = new Usuario(txtNombre.getText().toString(), txtApellido.getText().toString(), txtEmail.getText().toString(), txtCompany.getText().toString(), txtLeng.getText().toString(), txtExp.getText().toString());
                            usersRef.setValue(usuario);

                            Toast.makeText(getApplicationContext(),"Usuario creado correectamente. Inicie Sesion", Toast.LENGTH_SHORT).show();
                            finishActivity(2);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error: " + firebaseError.getMessage() + ". Intente de nuevo", Toast.LENGTH_SHORT).show();
                        }
                    });


                break;
        }
    }
}
