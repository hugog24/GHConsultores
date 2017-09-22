package com.ghcontadores.login.interactor;

import com.ghcontadores.login.presenter.LoginPresenter;
import com.ghcontadores.login.repository.LoginRepository;
import com.ghcontadores.login.repository.LoginRepositoryImplements;
import com.ghcontadores.login.view.fragments.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Redhat on 12/09/2017.
 */

public class LoginInteractorImplements implements LoginInteractor{

    private LoginPresenter loginPresenter;
    private LoginRepository loginRepository;

    public LoginInteractorImplements(LoginPresenter loginPresenter) {
        this.loginPresenter=loginPresenter;
        loginRepository=new LoginRepositoryImplements(loginPresenter);

    }

    @Override
    public void signIn(String username, String password, LoginFragment loginFragment, FirebaseAuth firebaseAuth) {
        loginRepository.signIn(username, password, loginFragment,firebaseAuth);
    }




}
