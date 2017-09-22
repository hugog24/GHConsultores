package com.ghcontadores.login.presenter;

import com.ghcontadores.login.view.fragments.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Redhat on 12/09/2017.
 */

public interface LoginPresenter {
    void signIn(String username, String password, LoginFragment loginFragment, FirebaseAuth firebaseAuth);
    void loginSucess(String success);//Interactor
    void loginUnsucess(String error);

}
