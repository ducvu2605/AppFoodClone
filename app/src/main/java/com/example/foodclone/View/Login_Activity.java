package com.example.foodclone.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodclone.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class Login_Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {

    private Button btnLogin,btnLoginGoogle,btnLoginFb;
    private TextView btnRegisterNew,btnQuenPassword ;
    private EditText edEmail,edPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth ;

    private GoogleApiClient apiClient;
    private  CallbackManager callbackFB;
    private LoginManager loginManager;
    private List<String> mPermissions = Arrays.asList("email","public_profile");
    public  static int RequestCode_SignIn_Google =  26 ;
    public static int  Check_SignIn = 0;  // 1 - google 2- facebook

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        callbackFB =CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        progressDialog = new ProgressDialog(this);
        edEmail = findViewById(R.id.ed_Email);
        edPassword = findViewById(R.id.ed_Password);
        btnLogin = findViewById(R.id.btn_Login);
        btnLoginGoogle = findViewById(R.id.btn_Login_Google);
        btnLoginFb = findViewById(R.id.btn_Login_Facebook);
        btnRegisterNew = findViewById(R.id.txt_SignUp);
        btnQuenPassword = findViewById(R.id.txt_QuenPassWord);
        btnLoginGoogle.setOnClickListener(this);
        btnLoginFb.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegisterNew.setOnClickListener(this);
        btnQuenPassword.setOnClickListener(this);
        ClientGoogle();

    }
    private void ClientGoogle(){
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
         apiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();

    }
    private void SignInFacebook(){
        loginManager.logInWithReadPermissions(this,mPermissions);
        loginManager.registerCallback(callbackFB, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Check_SignIn = 2;
                String tokenID = loginResult.getAccessToken().getToken();
                AuthFirebase(tokenID);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    private void SignInGoogle(GoogleApiClient apiClient){
        Check_SignIn = 1;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(signInIntent,RequestCode_SignIn_Google);
    }

    private void AuthFirebase(final String tokenID){

            if(Check_SignIn == 1){
               AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID,null);
               mAuth.signInWithCredential(authCredential);
            }else if(Check_SignIn == 2) {
                AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenID);
                mAuth.signInWithCredential(authCredential);
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCode_SignIn_Google){
            if(resultCode == RESULT_OK){
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount signInAccount = signInResult.getSignInAccount(); // Get ID
                String tokenID = signInAccount.getIdToken();  //Get TOKEN ID
                AuthFirebase(tokenID); // Check TOKENID is GOOGLE or FB
            }
        }else{
            callbackFB .onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_Login_Google:
                SignInGoogle(apiClient);
                break;
            case R.id.btn_Login_Facebook:
                SignInFacebook();
                break;
            case R.id.txt_SignUp:
                Intent iRegisterNew = new Intent(Login_Activity.this,Register_Activity.class);
                startActivity(iRegisterNew);
                break;
            case R.id.btn_Login:
                progressDialog.setMessage("Checking...");
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                SignIn();
                break;
            case R.id.txt_QuenPassWord:
                Intent iResetPassword = new Intent(Login_Activity.this,ResetPassword_Activity.class);
                startActivity(iResetPassword);
                break;

        }
    }
    private void SignIn(){
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Login_Activity.this, "Check Your Email & Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Intent iHome = new Intent(Login_Activity.this,Home_Activity.class);
            startActivity(iHome);
            progressDialog.dismiss();
            Toast.makeText(this, "đăng nhập thành công", Toast.LENGTH_SHORT).show();
            finish();
        }else{

        }
    }
}
