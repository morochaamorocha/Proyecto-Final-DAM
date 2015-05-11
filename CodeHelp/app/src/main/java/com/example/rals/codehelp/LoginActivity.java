package com.example.rals.codehelp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rals.codehelp.model.Usuario;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class LoginActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    /* ****************************
     *        GENERAL             *
     ******************************/

    //Muestra un dialogo mientras se realiza la autenticación
    private ProgressDialog mAuthProgressDialog;

    //Datos del usuario autenticado
    //private AuthData mAuthData;

    //Preferencias para guardar el id de usuario
    private SharedPreferences pref;

    private Firebase userRef;

    /* ****************************
     *        GOOGLE              *
     ******************************/

    /* Request code used to invoke sign in user interactions for Google+ */
    public static final int RC_GOOGLE_LOGIN = 1;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents us from starting further intents. */
    private boolean mGoogleIntentInProgress;

    /* Track whether the sign-in button has been clicked so that we know to resolve all issues preventing sign-in
     * without waiting. */
    private boolean mGoogleLoginClicked;

    /* Store the connection result from onConnectionFailed callbacks so that we can resolve them when the user clicks
     * sign-in. */
    private ConnectionResult mGoogleConnectionResult;

    //Botón de login en Google
    private SignInButton mGoogleLoginButton;

    /* *************************************
     *              PASSWORD               *
     ***************************************/

    private Button mPasswordLoginButton;
    private EditText txtEmail;
    private  EditText txtPassword;
    private TextView lblRegistrarse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar bar = getActionBar();
        bar.hide();

        Firebase.setAndroidContext(getApplicationContext());

        mGoogleLoginButton = (SignInButton)findViewById(R.id.google_login);
        mGoogleLoginButton.setOnClickListener(this);

        mPasswordLoginButton = (Button)findViewById(R.id.btnLogin);
        mPasswordLoginButton.setOnClickListener(this);

        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        lblRegistrarse = (TextView)findViewById(R.id.lblRegistrarse);
        lblRegistrarse.setOnClickListener(this);

        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Cargando");
        mAuthProgressDialog.setMessage("Autenticando...");
        mAuthProgressDialog.setCancelable(false);
        mAuthProgressDialog.show();

        Const.ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                mAuthProgressDialog.hide();
                setAuthenticatedUser(authData);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        pref = getSharedPreferences("mis_pref", MODE_PRIVATE);
    }

    /* ************************************
    *              GOOGLE                *
    **************************************
    */
    /* A helper method to resolve the current ConnectionResult error. */
    private void resolveSignInError() {
        if (mGoogleConnectionResult.hasResolution()) {
            try {
                mGoogleIntentInProgress = true;
                mGoogleConnectionResult.startResolutionForResult(this, RC_GOOGLE_LOGIN);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mGoogleIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    private void getGoogleOAuthTokenAndLogin() {
        mAuthProgressDialog.show();
        /* Get OAuth token in Background */
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            String errorMessage = null;

            @Override
            protected String doInBackground(Void... params) {
                String token = null;

                try {
                    String scope = String.format("oauth2:%s", Scopes.PLUS_LOGIN);
                    token = GoogleAuthUtil.getToken(LoginActivity.this, Plus.AccountApi.getAccountName(mGoogleApiClient), scope);
                } catch (IOException transientEx) {
                    /* Network or server error */
                    Log.e(TAG, "Error authenticating with Google: " + transientEx);
                    errorMessage = "Network error: " + transientEx.getMessage();
                } catch (UserRecoverableAuthException e) {
                    Log.w(TAG, "Recoverable Google OAuth error: " + e.toString());
                    /* We probably need to ask for permissions, so start the intent if there is none pending */
                    if (!mGoogleIntentInProgress) {
                        mGoogleIntentInProgress = true;
                        Intent recover = e.getIntent();
                        startActivityForResult(recover, RC_GOOGLE_LOGIN);
                    }
                } catch (GoogleAuthException authEx) {
                    /* The call is not ever expected to succeed assuming you have already verified that
                     * Google Play services is installed. */
                    Log.e(TAG, "Error authenticating with Google: " + authEx.getMessage(), authEx);
                    errorMessage = "Error authenticating with Google: " + authEx.getMessage();
                }
                return token;
            }

            @Override
            protected void onPostExecute(String token) {
                mGoogleLoginClicked = false;
                if (token != null) {
                    /* Successfully got OAuth token, now login with Google */
                    Const.ref.authWithOAuthToken("google", token, new AuthResultHandler("google"));
                } else if (errorMessage != null) {
                    mAuthProgressDialog.hide();
                    showErrorDialog(errorMessage);
                }
            }
        };
        task.execute();
    }

    @Override
    public void onConnected(Bundle bundle) {
        /* Connected with Google API, use this to authenticate with Firebase */
        getGoogleOAuthTokenAndLogin();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //nada
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mGoogleIntentInProgress) {
            /* Store the ConnectionResult so that we can use it later when the user clicks on the Google+ login button */
            mGoogleConnectionResult = connectionResult;

            if (mGoogleLoginClicked) {
                /* The user has already clicked login so we attempt to resolve all errors until the user is signed in,
                 * or they cancel. */
                resolveSignInError();
            } else {
                Log.e(TAG, connectionResult.toString());
            }
        }
    }

    /* ************************************
     *              PASSWORD              *
     **************************************
     */
    public void loginWithPassword() {
        mAuthProgressDialog.show();
        Const.ref.authWithPassword(txtEmail.getText().toString(), txtPassword.getText().toString(), new AuthResultHandler("password"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case RC_GOOGLE_LOGIN:
            /* This was a request by the Google API */
            if (resultCode != RESULT_OK) {
                mGoogleLoginClicked = false;
            }
            mGoogleIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
                break;
            case 2:
                if (resultCode == 0){
                    logout();
                }else{
                    finish();
                }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.google_login:

                mGoogleLoginClicked = true;
                if (!mGoogleApiClient.isConnecting()) {
                    if (mGoogleConnectionResult != null) {
                        resolveSignInError();
                    } else if (mGoogleApiClient.isConnected()) {
                        getGoogleOAuthTokenAndLogin();
                    } else {
                    /* connect API now */
                        Log.d(TAG, "Trying to connect to Google API");
                        mGoogleApiClient.connect();
                    }
                }
                break;
            case R.id.btnLogin:
                loginWithPassword();

                break;
            case R.id.lblRegistrarse:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;

        }
    }


    private void setAuthenticatedUser(AuthData authData) {

        if (authData != null) {

            //Almacenamos el uid en las preferencias
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("uid", authData.getUid());
            editor.apply();

            Intent intent = new Intent();
            //Toast.makeText(getApplicationContext(), authData.getProvider(), Toast.LENGTH_SHORT).show();

            if (authData.getProvider().equals("google")){
                //verificamos si el usuario existe
                userRef = Const.ref.child("users").child(authData.getUid());
                userRef.child("nombre").addValueEventListener(new ValueListener(authData));
            }
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 2);


        }
        Const.gAuthData = authData;

    }

    private void crearUserGoogle(AuthData authData) {
        Map<String, String> m = (Map<String, String>) authData.getProviderData().get("cachedUserProfile");
        Map<String, Object> m1 = new HashMap<>();

        //Log.i("DATOS DEL USUARIO:", m.keySet().toString());


        m1.put("nombre", m.get("given_name"));
        m1.put("apellido", m.get("family_name"));
        m1.put("email", m.get("email"));
        m1.put("compania", "");
        m1.put("lenguajes", "");
        m1.put("experiencia", "0");

        userRef.updateChildren(m1);
    }

    /**
     * Show errors to users
     */
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void logout(){
        if(Const.gAuthData != null){
            Const.ref.unauth();

            if (Const.gAuthData.getProvider().equals("google")) {
                /* Logout from Google+ */
                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                }
            }
        }
    }

    /**
     * Utility class for authentication results
     */
    class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {
            mAuthProgressDialog.hide();
            Log.i(TAG, provider + " auth successful");
            setAuthenticatedUser(authData);
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            mAuthProgressDialog.hide();
            showErrorDialog(firebaseError.toString());
        }
    }

    class ValueListener implements ValueEventListener{

        private AuthData authData;

        public ValueListener(AuthData authData) {
            this.authData = authData;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (!dataSnapshot.exists()){
                crearUserGoogle(authData);
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }
}
