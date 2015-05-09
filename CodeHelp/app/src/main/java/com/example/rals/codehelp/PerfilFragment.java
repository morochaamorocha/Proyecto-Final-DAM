package com.example.rals.codehelp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class PerfilFragment extends Fragment implements View.OnClickListener {


    private Button btnRegistrarse;
    private EditText txtNombre, txtApellido, txtEmail, txtPass, txtCompany, txtLeng, txtExp, txtPassAct;
    private TextView lblPass;
    private Map<String, Object> datos;
    private String provider;
    private Firebase mUserRef;


    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();


        return fragment;
    }

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        provider = Const.gAuthData.getProvider();

        //Obtenemos los cardview de Firebase
        Firebase mUserRef = Const.ref.child("users").child(Const.gAuthData.getUid());
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                datos = (Map<String, Object>) dataSnapshot.getValue();

                //Asignamos los cardview a los controles
                if (datos != null) {
                    txtNombre.setText(datos.get("nombre").toString());
                    txtApellido.setText(datos.get("apellido").toString());
                    txtEmail.setText(datos.get("email").toString());
                    txtCompany.setText(datos.get("compania").toString());
                    txtLeng.setText(datos.get("lenguajes").toString());
                    txtExp.setText(datos.get("experiencia").toString());
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity().getApplicationContext(), "Ha ocurrido un error al recuperar los cardview.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modificar_datos, container, false);

        txtNombre = (EditText)view.findViewById(R.id.txtNombre);
        txtApellido = (EditText)view.findViewById(R.id.txtApellido);
        txtEmail = (EditText)view.findViewById(R.id.txtEmail);

        //Si es Password gestionamos contraseña, por tanto se muestra el control
        if (provider.equals("password")){
            LinearLayout layout = (LinearLayout)view.findViewById(R.id.contrasena);
            layout.setVisibility(View.VISIBLE);

            txtPassAct = (EditText)view.findViewById(R.id.txtPassAct);
            txtPass = (EditText)view.findViewById(R.id.txtPassword1);
        }

        txtCompany = (EditText)view.findViewById(R.id.txtCompany);
        txtLeng = (EditText)view.findViewById(R.id.txtLeng);
        txtExp = (EditText)view.findViewById(R.id.txtExp);


        btnRegistrarse = (Button)view.findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setText("Actualizar Datos");
        btnRegistrarse.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnRegistrarse:

                //Actualizar los cardview en firebase
                if (provider.equals("password")){
                    if (!txtPassAct.getText().toString().trim().equals("")){

                        //Si se ha modificado la contraseña...
                        if (!txtPass.getText().toString().trim().equals("")){

                            //Si se ha modificado el email...
                            if (!datos.get("email").equals(txtEmail.getText().toString())){
                                //Modificamos el email
                                Const.ref.changeEmail(datos.get("email").toString(), txtPassAct.getText().toString(), txtEmail.getText().toString(), null);
                            }

                            //Modificamos la contraseña
                            Const.ref.changePassword(txtEmail.getText().toString(), txtPassAct.getText().toString(), txtPass.getText().toString(), null);


                        }

                        actualizarDatos();


                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Introduzca su password actual", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    //El proveedor es Google por tanto no gestionamos la contraseña del usuario
                    actualizarDatos();

                }
                break;
        }
    }

    private void actualizarDatos() {

        Map<String, Object> m1 = new HashMap<>();

        //Actualizamos los cardview...
        mUserRef = Const.ref.child("users").child(Const.gAuthData.getUid());
        m1.put("nombre", txtNombre.getText().toString());
        m1.put("apellido", txtApellido.getText().toString());
        m1.put("email",txtEmail.getText().toString());
        m1.put("compania", txtCompany.getText().toString());
        m1.put("lenguajes", txtLeng.getText().toString());
        m1.put("experiencia", txtExp.getText().toString());

        mUserRef.updateChildren(m1, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Toast.makeText(getActivity().getApplication(), "Los cardview han sido actualizados", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
