package com.ghcontadores.login.repository;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.ghcontadores.home.view.HomeActivity;
import com.ghcontadores.login.presenter.LoginPresenter;
import com.ghcontadores.login.view.fragments.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Redhat on 12/09/2017.
 */

public class LoginRepositoryImplements implements LoginRepository {

    private LoginPresenter loginPresenter;

    public LoginRepositoryImplements(LoginPresenter loginPresenter) {
        this.loginPresenter=loginPresenter;

    }

    @Override
    public void signIn(String username, String password, final LoginFragment loginFragment, FirebaseAuth firebaseAuth) {
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    //SharedPreferences sharedPreferences=
                    loginPresenter.loginSucess(" Bienvenido!!");
                }
                else
                    {
                        loginPresenter.loginUnsucess(" Usuario o contraseña incorrectos");
                    }
            }
        });


    }





}





