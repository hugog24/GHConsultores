package com.ghcontadores.signup.presenter;

import com.ghcontadores.login.view.LoginView;
import com.ghcontadores.signup.interactor.SignUpInteractor;
import com.ghcontadores.signup.interactor.SignUpInteractorImplements;
import com.ghcontadores.signup.view.fragments.CreateAccountFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Redhat on 16/09/2017.
 */

public class SignUpPresenterImplements implements SignUpPresenter {

    private LoginView loginView;
    private SignUpInteractor signUpInteractor;

    public SignUpPresenterImplements(LoginView loginView) {
        this.loginView = loginView;
        signUpInteractor = new SignUpInteractorImplements(this);
    }

    @Override
    public void signUp(String email, String password, CreateAccountFragment createAccountFragment, FirebaseAuth firebaseAuth) {
        loginView.disableInputs();
        loginView.showProgressBar();
        signUpInteractor.signUp(email, password, createAccountFragment,firebaseAuth);
    }

    @Override
    public void signUpSuccess(String success) {
        loginView.hideProgressBar();
        loginView.loginWelcome(success);
    }

    @Override
    public void signUpUnSuccess(String unsuccess) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(unsuccess);
    }

    @Override
    public void sendEmailVerification(CreateAccountFragment createAccountFragment, FirebaseUser firebaseUser) {
        signUpInteractor.sendEmailVerification(createAccountFragment,firebaseUser);
    }

    @Override
    public void emailVerificationSuccess() {
        loginView.emailVerificationWelcome();
    }

    @Override
    public void emailVerificationUnSuccess() {
        loginView.emailVerificationError();
    }
}
