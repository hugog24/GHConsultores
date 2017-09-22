package com.ghcontadores.signup.interactor;

import com.ghcontadores.signup.presenter.SignUpPresenter;
import com.ghcontadores.signup.repository.SignUpRepository;
import com.ghcontadores.signup.repository.SignUpRepositoryImplements;
import com.ghcontadores.signup.view.fragments.CreateAccountFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Redhat on 16/09/2017.
 */

public class SignUpInteractorImplements implements SignUpInteractor {

    private SignUpPresenter signUpPresenter;
    private SignUpRepository signUpRepository;

    public SignUpInteractorImplements(SignUpPresenter signUpPresenter) {
        this.signUpPresenter=signUpPresenter;
        signUpRepository=new SignUpRepositoryImplements(signUpPresenter);
    }

    @Override
    public void signUp(String email, String password, CreateAccountFragment createAccountFragment, FirebaseAuth firebaseAuth) {
        signUpRepository.signUp(email,password,createAccountFragment,firebaseAuth);
    }

    @Override
    public void sendEmailVerification(CreateAccountFragment createAccountFragment, FirebaseUser firebaseUser) {
        signUpRepository.sendEmailVerification(createAccountFragment,firebaseUser);
    }

}
