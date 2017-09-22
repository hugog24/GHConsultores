package com.ghcontadores.login.repository;

import com.ghcontadores.login.view.fragments.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Redhat on 12/09/2017.
 */

public interface LoginRepository {

    void signIn(String username, String password, LoginFragment loginFragment, FirebaseAuth firebaseAuth);


}
