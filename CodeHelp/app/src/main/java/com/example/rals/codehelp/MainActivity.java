package com.example.rals.codehelp;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        uid = getSharedPreferences("mis_pref", MODE_PRIVATE).getString("uid", null);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getFragmentManager();
        switch (position){
            case 0:

                fragmentManager.beginTransaction()
                        .replace(R.id.container, CrearSolicitudFragment.newInstance())
                        .commit();

                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PerfilFragment.newInstance())
                        .commit();
                break;
            case 2:

                fragmentManager.beginTransaction()
                        .replace(R.id.container, ExpertosFragment.newInstance(position))
                        .commit();

                break;
            case 3:

                fragmentManager.beginTransaction()
                        .replace(R.id.container, ExpertosFragment.newInstance(position))
                        .commit();

                break;
            case 4:

                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Esta seguro que desea salir de la aplicacion?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                break;
        }



    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = "Solicitar un Experto";
                break;
            case 1:
                mTitle = "Perfil y Facturacion";
                break;
            case 2:
                mTitle = "Mis Expertos";
                break;
            case 3:
                mTitle = "Mis Solicitudes";
                break;
        }

        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void logout() {
        if (uid != null) {

            setResult(0);
            finish();

        }
    }

}
