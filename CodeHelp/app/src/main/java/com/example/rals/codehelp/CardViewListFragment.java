package com.example.rals.codehelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rals.codehelp.model.Usuario;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CardViewListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Object> datos;
    private ExpertosAdapter adapter;
    private int pos;


    public static CardViewListFragment newInstance(int position) {
        CardViewListFragment fragment = new CardViewListFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public CardViewListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        datos = new ArrayList<>();

        Firebase mRef = Const.ref.child("solicitudes");


        //TODO: Extraer datos de Firebase según la posicion
        switch (pos = getArguments().getInt("position")){
            case 0:
                //Mostrar solicitudes activas hechas por el usuario
                Query query = mRef.orderByChild("idCliente").equalTo(Const.gAuthData.getUid());



                break;
            case 2:
                //Mostrar historial expertos
                Usuario u1 = new Usuario("test1", "test1", "", "", "", "");
                Usuario u2 = new Usuario("test2", "test2", "", "", "", "");
                Usuario u3 = new Usuario("test3", "test3", "", "", "", "");

                datos = new ArrayList<>();
                datos.add(u1);
                datos.add(u2);
                datos.add(u3);
                break;
            case 3:
                //Mostrar historial solicitudes
                break;
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cardview_list, container, false);

        if (!datos.isEmpty()){

            recyclerView = (RecyclerView)view.findViewById(R.id.RecView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            adapter = new ExpertosAdapter(datos);
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Iniciar actividad de detalle del experto/solicitud
                    startActivity(new Intent(getActivity().getApplicationContext(), DetalleActivity.class));
                }
            });

            recyclerView.setAdapter(adapter);

            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setVisibility(View.VISIBLE);


        }else {

            TextView lblEmptyList = (TextView)view.findViewById(R.id.lblEmptyText);
            lblEmptyList.setVisibility(View.VISIBLE);

        }

        Button btnIniciarSolicitud = (Button)view.findViewById(R.id.btnIniciarSolicitud);
        btnIniciarSolicitud.setVisibility(View.VISIBLE);
        btnIniciarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Iniciar nueva solicitud
                startActivity(new Intent(getActivity().getApplicationContext(), IniciarSolicitudActivity.class));
            }
        });
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    class ExpertosAdapter extends RecyclerView.Adapter<ExpertosAdapter.Holder> implements View.OnClickListener{

        private List<Object> m;
        private View.OnClickListener listener;

        public ExpertosAdapter(List<Object> m) {
            this.m = m;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

            view.setOnClickListener(this);
            Holder holder = new Holder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Usuario u = (Usuario)m.get(position);
            int img;

            if (pos == 2){
                img = R.drawable.user_icon;
            }else{
                img = R.drawable.javaimage;
            }
            holder.asignarDatos(u.getNombre() + " " + u.getApellido(), img);
        }

        @Override
        public int getItemCount() {
            return m.size();
        }

        public void setOnClickListener(View.OnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onClick(v);
            }
        }

        class Holder extends RecyclerView.ViewHolder{

            private TextView txtTitulo;
            private ImageView imgFondo;

            public Holder(View itemView) {
                super(itemView);

                txtTitulo = (TextView)itemView.findViewById(R.id.txt2);
                imgFondo = (ImageView)itemView.findViewById(R.id.img_cardview);
            }

            public void asignarDatos(String titulo, int img){
                txtTitulo.setText(titulo);
                imgFondo.setImageResource(img);
            }


        }
    }
}
