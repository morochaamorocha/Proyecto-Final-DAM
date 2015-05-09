package com.example.rals.codehelp;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrearSolicitudFragment extends Fragment {


    public static CrearSolicitudFragment newInstance() {
        CrearSolicitudFragment fragment = new CrearSolicitudFragment();

        return fragment;
    }

    public CrearSolicitudFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //TODO: Implementar las vistas del fragment

        return rootView;
    }


}
