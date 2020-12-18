package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_PERMISSION=111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        solicitarPermisos();
    }
    public void Siguiente(View view){
        Intent paso=new Intent(this,LoginActivity.class);
        startActivity(paso);
    }

    public void Regis(View view){
        Intent paso=new Intent(this,Registro.class);
        startActivity(paso);
    }

    private void solicitarPermisos(){
        int permisoStorage = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permisoLocation = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permisoStorage!=getPackageManager().PERMISSION_GRANTED)
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                requestPermissions(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERMISSION);

    }

}