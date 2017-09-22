package com.ghcontadores.login.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ghcontadores.R;
import com.ghcontadores.home.view.HomeActivity;
import com.ghcontadores.login.presenter.LoginPresenter;
import com.ghcontadores.login.presenter.LoginPresenterImplements;
import com.ghcontadores.login.view.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class LoginFragment extends Fragment implements LoginView {

    private TextInputEditText edtusername,edtpassword;
    private Button btnsignin;
    private ProgressBar progressBarLogin;
    private LoginButton fbloginbutton;
    private CallbackManager callbackManager;

    public FirebaseAuth firebaseAuth;
    public FirebaseAuth.AuthStateListener authStateListener;
    LoginPresenter loginPresenter;
    private static final String TAG = "LoginFragment";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override

    //se infla el fragment para que se visualize
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        fbloginbutton=(LoginButton)view.findViewById(R.id.loginFacebook);
        btnsignin=(Button)view.findViewById(R.id.btnSigin);
        progressBarLogin=(ProgressBar)view.findViewById(R.id.progressBarLogin);

        loginPresenter=new LoginPresenterImplements(this);
        firebaseInitialize();
        hideProgressBar();


//Accion de boton iniciar sesion
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtusername=(TextInputEditText)view.findViewById(R.id.username);
                edtpassword=(TextInputEditText)view.findViewById(R.id.password);

                if (TextUtils.isEmpty(edtusername.getText()) || TextUtils.isEmpty(edtpassword.getText()))
                {
                    Toast.makeText(getContext(),getString(R.string.login_fields_validation), Toast.LENGTH_LONG).show();
                }else
                    {
                        firebaseSignIn(edtusername.getText().toString(),edtpassword.getText().toString());
                    }
            }
        });

//Configuracion Facebook Login
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
        callbackManager=CallbackManager.Factory.create();
        fbloginbutton.setReadPermissions(Arrays.asList("email"));
        fbloginbutton.setFragment(this);
        fbloginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            Log.w(TAG,"Facebook Login Success Token: "+loginResult.getAccessToken().getApplicationId());
                signinFacebookFirebase(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.w(TAG,"Facebook Login Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG,"Facebook Login Success Error "+error.toString());
                error.printStackTrace();
            }
        });


        return view;

    }

    private void signinFacebookFirebase(AccessToken accessToken) {
        AuthCredential authCredential= FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(),getString(R.string.login_facebook_success), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                }else
                    {
                        Toast.makeText(getContext(),getString(R.string.login_facebook_unsuccess), Toast.LENGTH_LONG).show();
                    }
            }
        });
    }


    //obtiene informacion del archivo google-services.json

    public void firebaseInitialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Log.w(TAG, "Usuario logueado con éxito" + firebaseUser.getUid());
                    Log.w(TAG, "Usuario logueado con éxito" + firebaseUser.getEmail());

                } else {
                    Log.w(TAG, "Usuario no logueado");
                }
            }
        };
    }

    public void firebaseSignIn(String username,String password)
    {
        loginPresenter.signIn(username,password,this,firebaseAuth);
    }


    //Metodos de interfaz LoginView

    @Override
    public void enableInputs() {
        edtusername.setEnabled(true);
        edtpassword.setEnabled(true);
        btnsignin.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        edtusername.setEnabled(false);
        edtpassword.setEnabled(false);
        btnsignin.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginWelcome(String success) {
        Toast.makeText(getContext(),getString(R.string.login_success)+success, Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(getContext(),getString(R.string.login_error)+error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void emailVerificationWelcome() {

    }

    @Override
    public void emailVerificationError() {

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

  //Manejo de sesion de facebook
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
