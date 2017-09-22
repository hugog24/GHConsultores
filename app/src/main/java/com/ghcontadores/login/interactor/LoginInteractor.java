package com.ghcontadores.login.interactor;

import com.ghcontadores.login.view.fragments.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Redhat on 12/09/2017.
 */

public interface LoginInteractor {
    void signIn(String username, String password, LoginFragment loginFragment, FirebaseAuth firebaseAuth);


}
