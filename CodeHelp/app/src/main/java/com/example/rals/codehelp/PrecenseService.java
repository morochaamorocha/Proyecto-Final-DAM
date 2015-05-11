package com.example.rals.codehelp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;


public class PrecenseService extends IntentService {

    private android.os.Handler handler;

    private static final String ACTION_INICIAR = "com.example.rals.codehelp.action.FOO";
    private static final String ACTION_INICIAR_CHAT = "com.example.rals.codehelp.action.BAZ";
    private static final String ACTION_FIN = "com.example.rals.codehelp.action.FIN";

    public static final String ID_SOLICITUD = "com.example.rals.codehelp.extra.PARAM1";

    private String idSolicitud;

    public static void iniciarServicio(Context context, String idSolicitud) {
        Intent intent = new Intent(context, PrecenseService.class);
        intent.setAction(ACTION_INICIAR);
        intent.putExtra(ID_SOLICITUD, idSolicitud);

        context.startService(intent);
    }

    public PrecenseService() {
        super("PrecenseService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String action = intent.getAction();
            idSolicitud = intent.getStringExtra(ID_SOLICITUD);

            if (ACTION_INICIAR.equals(action)) {

                handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        handleEsperandoExperto();
                    }
                }, 900000);

                handleActionTimeOut();

            }else if (ACTION_INICIAR_CHAT.equals(action)){

                //TODO: Atender servicio para el chat


            }
        }
    }


    private void handleActionTimeOut() {

        //Cambiamos el estado de la solicitud..
        Firebase mRef = Const.ref.child("solicitudes").child(idSolicitud);
        Map<String, Object> m = new HashMap<>();
        m.put("abierta", false);
        mRef.updateChildren(m);

        //Enviamos un broadcast para publicar la acción...
        Intent intent = new Intent();
        intent.setAction(ACTION_FIN);
        sendBroadcast(intent);
        stopSelf();
    }


    private void handleEsperandoExperto() {

        //Implementar el escuchador de Firebase sobre la solicitud creada...
        Firebase mRef =Const.ref.child("solicitudes").child(idSolicitud);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Iniciamos el chat una vez que ha aceptado la solicitud el experto...
                Map<String, Object> m = (Map<String, Object>) dataSnapshot.getValue();
                if (!m.get("idexperto").toString().equals(null)){
                    Intent intent = new Intent();
                    intent.setAction(ACTION_INICIAR_CHAT);
                    sendBroadcast(intent);
                }

                //Mejorar para que permita aceptar o denegar el experto.
                //Por ahora conecta con el primero que acepte.

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
