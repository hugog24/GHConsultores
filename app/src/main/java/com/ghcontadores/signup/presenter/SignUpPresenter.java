package com.ghcontadores.signup.presenter;

import com.ghcontadores.signup.view.fragments.CreateAccountFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Redhat on 16/09/2017.
 */

public interface SignUpPresenter {
    void signUp(String email, String password, CreateAccountFragment createAccountFragment, FirebaseAuth firebaseAuth);
    void signUpSuccess(String success);
    void signUpUnSuccess(String unsuccess);
    void sendEmailVerification(CreateAccountFragment createAccountFragment, FirebaseUser firebaseUser);
    void emailVerificationSuccess();
    void emailVerificationUnSuccess();

}
