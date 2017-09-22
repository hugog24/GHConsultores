package com.ghcontadores.signup.interactor;

import com.ghcontadores.signup.view.fragments.CreateAccountFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Redhat on 16/09/2017.
 */

public interface SignUpInteractor {
    void signUp(String email, String password, CreateAccountFragment createAccountFragment, FirebaseAuth firebaseAuth);
    void sendEmailVerification(CreateAccountFragment createAccountFragment, FirebaseUser firebaseUser);
}
