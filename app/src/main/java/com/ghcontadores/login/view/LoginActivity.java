package com.ghcontadores.login.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ghcontadores.R;
import com.ghcontadores.signup.view.fragments.CreateAccountFragment;
import com.ghcontadores.login.view.fragments.LoginFragment;
import com.ghcontadores.login.view.fragments.NewsFragment;


public class LoginActivity extends AppCompatActivity  {

    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imgLogo=(ImageView)findViewById(R.id.idLogo);
       /* imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent implicito
                Intent logo=new Intent(Intent.ACTION_WEB_SEARCH);
                logo.setData(Uri.parse("http://www.ghcontadores.com"));
                startActivity(logo);
            }
        });
        */

//se instancia el bottomnavigation view, y se asignan los fragmentos para cada item

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.tabNews:
                        addFragment(new NewsFragment());
                        break;

                    case R.id.tabLogin:
                        addFragment(new LoginFragment());
                        break;

                    case R.id.tabCreateAccount:
                        addFragment(new CreateAccountFragment());
                        break;

                }

                return false;
            }
        });
    }
        /*
    Se crea un metodo que se encargara de realizar la gestion de colocar el fragment en el contenedor(activity_login)
    Por parametro, se va recibir un fragment; que se debe tener en cuenta de agregar el paquete de
    soporte de Fragment(android.support.v4.app.Fragment), mas no, el otro paquete
    Tambien se valida que el fragment que se envia por parametro, no sea nulo.
    */

        private void addFragment(Fragment fragment)
        {
            if(null!=fragment)
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.navContainer,fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();

            }
        }






}

