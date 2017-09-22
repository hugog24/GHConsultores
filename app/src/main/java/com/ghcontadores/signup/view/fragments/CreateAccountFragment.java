package com.ghcontadores.signup.view.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ghcontadores.R;
import com.ghcontadores.login.presenter.LoginPresenter;
import com.ghcontadores.login.view.LoginView;
import com.ghcontadores.login.view.fragments.LoginFragment;
import com.ghcontadores.signup.presenter.SignUpPresenter;
import com.ghcontadores.signup.presenter.SignUpPresenterImplements;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class CreateAccountFragment extends Fragment implements LoginView {

    private Button btnsignup;
    private ProgressBar signUpProgressbar;
    private TextInputEditText edtemail,edtpassword,edtconfirmpass,edtname;

    public FirebaseAuth firebaseAuth;
    public FirebaseAuth.AuthStateListener authStateListener;
    public FirebaseUser firebaseUser;
    private SignUpPresenter signUpPresenter;
    private static final String TAG = "CreateAccountFragment";

   public CreateAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        btnsignup=(Button)view.findViewById(R.id.btnCreateAccount);
        signUpProgressbar=(ProgressBar)view.findViewById(R.id.progressBarSignup);
        edtconfirmpass=(TextInputEditText)view.findViewById(R.id.confirmpassword);
        edtname=(TextInputEditText)view.findViewById(R.id.nombre);

        signUpPresenter=new SignUpPresenterImplements(this);
        firebaseInitialize();
        hideProgressBar();



        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtemail=(TextInputEditText)view.findViewById(R.id.email);
                edtpassword=(TextInputEditText)view.findViewById(R.id.password);

                if (TextUtils.isEmpty(edtemail.getText()) || TextUtils.isEmpty(edtpassword.getText()))
                    //||edtpassword.getText()!=edtconfirmpass.getText()
                {
                    Toast.makeText(getContext(),getString(R.string.login_fields_validation), Toast.LENGTH_LONG).show();
                    //edtconfirmpass.setError("Las contraseñas no coinciden");
                }else
                {
                        firebaseSignUp(edtemail.getText().toString(),edtpassword.getText().toString());
                        emailVerificationWelcome();

                }


            }
        });


        return view;
    }

    //obtiene informacion del archivo google-services.json
    public void firebaseInitialize() {
        //obtiene informacion del archivo google-services.json
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Log.w(TAG, "Usuario logueado con éxito" + firebaseUser.getUid());
                    Log.w(TAG, "Usuario logueado con éxito" + firebaseUser.getEmail());

                } else {
                    Log.w(TAG, "Usuario no logueado");
                }
            }
        };
    }

    public void firebaseSignUp(String username,String password)
    {
        signUpPresenter.signUp(username,password,this,firebaseAuth);
    }

    public void sendEmailVerification(FirebaseUser firebaseUser)
    {
        signUpPresenter.sendEmailVerification(this,firebaseUser);

    }

    @Override
    public void enableInputs() {
        btnsignup.setEnabled(true);
        edtemail.setEnabled(true);
        edtpassword.setEnabled(true);
        edtconfirmpass.setEnabled(true);
        edtname.setEnabled(true);

    }

    @Override
    public void disableInputs() {
        btnsignup.setEnabled(false);
        edtemail.setEnabled(false);
        edtpassword.setEnabled(false);
        edtconfirmpass.setEnabled(false);
        edtname.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        signUpProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        signUpProgressbar.setVisibility(View.GONE);

    }

    @Override
    public void loginWelcome(String success) {
        Toast.makeText(getContext(),getString(R.string.signup_success)+success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(getContext(),getString(R.string.signup_error)+error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void emailVerificationWelcome() {
        Snackbar snackbar=Snackbar.make(getView(),getString(R.string.email_verification),Snackbar.LENGTH_LONG)
                .setAction("Verificar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast reset=Toast.makeText(getContext(),getString(R.string.email_verification_message),Toast.LENGTH_SHORT);
                        reset.show();
                        sendEmailVerification(firebaseUser);
                    }
                });
        snackbar.setActionTextColor(Color.CYAN);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
        snackbar.show();
    }

    @Override
    public void emailVerificationError() {
        Toast.makeText(getContext(),getString(R.string.email_verification_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
