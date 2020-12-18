package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.aplicacion.Model.LoginInteractorImpl;
import com.example.aplicacion.Presenter.LoginPresenter;
import com.example.aplicacion.Presenter.LoginPresenterImpl;
import com.example.aplicacion.View.LoginView;
import  android.content.SharedPreferences;

public class LoginActivity extends Activity implements LoginView, View.OnClickListener {

    private ProgressBar progressBar;
    private EditText usuario;
    private EditText password;
    private LoginPresenter presenter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       
        usuario = findViewById((R.id.edtusername));
        password = findViewById(R.id.edtpassword);
      //  sharedPreferences=getSharedPreferences("loginref",MODE_PRIVATE);
       // editor=sharedPreferences.edit();
        findViewById(R.id.btnIngresar).setOnClickListener(this);
        presenter = new LoginPresenterImpl(this,new LoginInteractorImpl());
    }

    public void onDestroy() {
       presenter.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {

        preferences=getSharedPreferences("Presionado",MODE_PRIVATE);
        String userDetails=preferences.getString(usuario.getText().toString(),password.getText().toString());
        editor=preferences.edit();
        editor.putString("Login",userDetails);
        editor.commit();

        Intent paso=new Intent(this,MainActivity2.class);
        startActivity(paso);

        //Se esta haciendo uso del sharedPreferences sin uso del presenter
        //presenter.validateCredentials(usuario.getText().toString(), password.getText().toString());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void setUsernameError() {
        usuario.setError("error de usuario");

    }

    @Override
    public void setPasswordError() {
        password.setError("error de password");

    }

    @Override
    public void navigateToHome() {
        Toast.makeText(this,"ingreso datos",Toast.LENGTH_SHORT).show();
        Intent paso=new Intent(this,MainActivity2.class);
        startActivity(paso);

    }
    public void Siguiente2(View view){
        Intent paso=new Intent(this,MainActivity2.class);
        startActivity(paso);
    }
}