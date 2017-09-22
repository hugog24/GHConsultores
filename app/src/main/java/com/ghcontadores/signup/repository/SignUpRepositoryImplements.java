package com.ghcontadores.signup.repository;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.ghcontadores.R;
import com.ghcontadores.signup.presenter.SignUpPresenter;
import com.ghcontadores.signup.view.fragments.CreateAccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Redhat on 16/09/2017.
 */

public class SignUpRepositoryImplements implements SignUpRepository {

    private SignUpPresenter signUpPresenter;

    public SignUpRepositoryImplements(SignUpPresenter signUpPresenter) {
        this.signUpPresenter=signUpPresenter;

    }

    @Override
    public void signUp(String email, String password, CreateAccountFragment createAccountFragment, FirebaseAuth firebaseAuth) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    signUpPresenter.signUpSuccess("Revisa tu correo para verificar tu cuenta");
                }else
                {
                    signUpPresenter.signUpUnSuccess(" Verifica los campos");
                }
            }
        });
    }

    @Override
    public void sendEmailVerification(CreateAccountFragment createAccountFragment, FirebaseUser firebaseUser) {
        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    signUpPresenter.emailVerificationSuccess();
                }else
                {
                    signUpPresenter.emailVerificationUnSuccess();
                }
            }
        });
    }
}
