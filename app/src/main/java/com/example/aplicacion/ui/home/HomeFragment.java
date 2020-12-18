package com.example.aplicacion.ui.home;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.aplicacion.MainActivity2;
import com.example.aplicacion.Presenter.Servicios.MiServicioGPS;
import com.example.aplicacion.R;


public class HomeFragment extends Fragment {
     private int var = 0;
    MiServicioGPS miServicio;
    ProgressBar progressBar;
    private HomeViewModel homeViewModel;
    TextView prueba;
    ImageView play,pause,stop;
    boolean flag1 = false, flag2 = true;


    Chronometer chronometro;
    Boolean correr=false;
    long detenerse;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        chronometro=root.findViewById(R.id.chronometro);
        progressBar = root.findViewById(R.id.progress_bar);
        play = root.findViewById(R.id.btnplay);
        stop = root.findViewById(R.id.btnstop);
        pause = root.findViewById(R.id.btnpause);

        Toast.makeText(getActivity(),"DE NUEVO",Toast.LENGTH_SHORT).show();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChronometro();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetChronometro();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopChronometro();
            }
        });
        chronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                updateProgressBar();
            }
        });
        //miServicio = new MiServicioGPS(getActivity());
        prueba = (TextView)root.findViewById(R.id.kmrecorridos);
        //miServicio.setView(prueba);





        return root;
    }
    private void updateProgressBar(){
        if(flag1){
            progressBar.setProgress(var);
        }
        flag1 = true;
        if(var== 59 ){
            var = 0;
        }
        var++;

    }

    private void resetChronometro() {

        chronometro.setBase(SystemClock.elapsedRealtime());
        detenerse=0;
        var = 0;
    }

    private void stopChronometro() {
        if (correr){
            chronometro.stop();
            detenerse = SystemClock.elapsedRealtime() - chronometro.getBase();
            correr=false;
        }
    }

    private void startChronometro() {
        if(flag2) {
            flag2 = false;
            ((MainActivity2) getActivity()).iniciarServicio();
            ((MainActivity2) getActivity()).setVieww(prueba);
        }
        if(!correr){
            chronometro.setBase(SystemClock.elapsedRealtime() - detenerse);
            chronometro.start();
            correr=true;
        }
    }
}