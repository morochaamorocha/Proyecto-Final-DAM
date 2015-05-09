package com.example.rals.codehelp;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rals.codehelp.model.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExpertosFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Object> datos;


    public static ExpertosFragment newInstance(int position) {
        ExpertosFragment fragment = new ExpertosFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    public ExpertosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //TODO: Extraer datos de Firebase según la posicion

        Usuario u1 = new Usuario("test1", "test1", "", "", "", "", "");
        Usuario u2 = new Usuario("test2", "test2", "", "", "", "", "");
        Usuario u3 = new Usuario("test3", "test3", "", "", "", "", "");

        datos = new ArrayList<>();
        datos.add(u1);
        datos.add(u2);
        datos.add(u3);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mis_expertos, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.RecView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        ExpertosAdapter adapter = new ExpertosAdapter(datos);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Iniciar actividad de detalle del experto
            }
        });

        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datos, parent, false);

            view.setOnClickListener(this);
            Holder holder = new Holder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Usuario u = (Usuario)m.get(position);
            holder.asignarDatos(u.getNombre() + " " + u.getApellido(), R.id.useLogo);
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
