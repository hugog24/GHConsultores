package com.ghcontadores.home.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.ghcontadores.R;
import com.ghcontadores.login.view.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String TAG="omeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showToolbar("Inicio",false);
        firebaseInitialize();

    }

    public void showToolbar(String title,boolean upButton)
    {
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        //CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.signout:
                //se cierra sesion de firebase
                firebaseAuth.signOut();
                //se cierra sesion de facebook
                if (LoginManager.getInstance()!=null)
                {
                    LoginManager.getInstance().logOut();
                }
                Toast.makeText(this,"Cerrando sesión", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(this, LoginActivity.class);
                startActivity(intent);

                break;

            case R.id.aboutus:
                Toast.makeText(this,"GHConsultores by Hugo Garcia", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void firebaseInitialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Log.w(TAG, "Usuario logueado con éxito" + firebaseUser.getUid());
                    Log.w(TAG, "Usuario logueado con éxito" + firebaseUser.getEmail());

                } else {
                    Log.w(TAG, "Usuario no logueado");
                }
            }
        };
    }
}
