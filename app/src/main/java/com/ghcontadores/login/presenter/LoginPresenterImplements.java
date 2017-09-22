package com.ghcontadores.login.presenter;

import com.ghcontadores.login.interactor.LoginInteractor;
import com.ghcontadores.login.interactor.LoginInteractorImplements;
import com.ghcontadores.login.view.LoginView;
import com.ghcontadores.login.view.fragments.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Redhat on 12/09/2017.
 */

public class LoginPresenterImplements implements LoginPresenter{

    private LoginView loginView;
    private LoginInteractor loginInteractor;


    public LoginPresenterImplements(LoginView loginView)
    {
        this.loginView=loginView;
        loginInteractor=new LoginInteractorImplements(this);
    }

    @Override
    public void signIn(String username, String password, LoginFragment loginFragment, FirebaseAuth firebaseAuth) {
        loginView.disableInputs();
        loginView.showProgressBar();
        loginInteractor.signIn(username, password, loginFragment,firebaseAuth);
    }


    @Override
    public void loginSucess(String success) {
        loginView.hideProgressBar();
        loginView.loginWelcome(success);

}


    @Override
    public void loginUnsucess(String error) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(error);
    }


}
