package com.example.aplicacion;

import android.Manifest;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.aplicacion.Presenter.Servicios.MiServicioGPS;
import com.example.aplicacion.ui.dashboard.MusicFragment;
import com.example.aplicacion.ui.home.HomeFragment;
import com.example.aplicacion.ui.times.IOnFocusListenable;
import com.example.aplicacion.ui.times.TimesFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

public class MainActivity2 extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSION = 111;
    BottomNavigationView navigation;
    Fragment musica, mapa, tiempo, home;
    private GoogleMap mMap;
    Fragment currentFragment;;
    FragmentTransaction transaction;
    public static MiServicioGPS miServicioGPS;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        musica = new MusicFragment();
        mapa = new MapsFragment();
        tiempo = new TimesFragment();
        home = new HomeFragment();
        currentFragment = tiempo;
        context = getApplicationContext();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(

                R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_musica,R.id.navigation_times)
                .build();*/
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);//cambios en el AppBar
        //NavigationUI.setupWithNavController(navView, navController);


        solicitarPermisos();
    }

    public void setmMap(GoogleMap mMap2) {
        this.mMap = mMap2;
    }

    private void solicitarPermisos() {
        int permisoStorage = ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permisoStorage != getPackageManager().PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSION);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (home.isAdded()) {
                        transaction
                                .hide(currentFragment)
                                .show(home);
                    } else {
                        transaction
                                .hide(currentFragment)
                                .add(R.id.nav_host_fragment, home);
                    }
                    currentFragment = home;
                   // transaction.replace(R.id.nav_host_fragment, home);
                   // transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.navigation_musica:
                    if (musica.isAdded()) {
                        transaction
                                .hide(currentFragment)
                                .show(musica);
                    } else {
                        transaction
                                .hide(currentFragment)
                                .add(R.id.nav_host_fragment, musica);
                    }
                    currentFragment = musica;
                    //transaction.replace(R.id.nav_host_fragment, musica).commit();
                    //transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.navigation_times:
                    if (tiempo.isAdded()) {
                        transaction
                                .hide(currentFragment)
                                .show(tiempo);
                    } else {
                        transaction
                                .hide(currentFragment)
                                .add(R.id.nav_host_fragment, tiempo);
                    }
                    currentFragment = tiempo;
                    //transaction.replace(R.id.nav_host_fragment, tiempo).commit();
                    //transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    if (mapa.isAdded()) {
                        transaction
                                .hide(currentFragment)
                                .show(mapa);
                    } else {
                        transaction
                                .hide(currentFragment)
                                .add(R.id.nav_host_fragment, mapa);
                    }
                    currentFragment = mapa;
                    //transaction.replace(R.id.nav_host_fragment, mapa).commit();
                    //transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };



    public  static void iniciarServicio(){
        miServicioGPS = new MiServicioGPS(context);

    }
    public  static void setMapa(GoogleMap mMap){
        miServicioGPS.setMap(mMap);
    }
    public static void setVieww (View v){
        miServicioGPS.setView(v);
    }
}