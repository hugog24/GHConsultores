package com.ghcontadores.login.view;

/**
 * Created by Redhat on 10/09/2017.
 */

public interface LoginView {

    void enableInputs();
    void disableInputs();
    void showProgressBar();
    void hideProgressBar();
    void loginWelcome(String success);
    void loginError(String error);
    void emailVerificationWelcome();
    void emailVerificationError();
}
