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
//
import com.example.foodclone.Controller.RegisterController;
import com.example.foodclone.Model.UserModel;
import com.example.foodclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRegister;
    private EditText edRegEmail ,edRegPassWord,edRegAgainPassWord;
    private TextView btnSignIn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private RegisterController registerController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btnRegister =findViewById(R.id.btn_SignUp);
        edRegEmail = findViewById(R.id.ed_Email);
        edRegPassWord = findViewById(R.id.ed_Password);
        edRegAgainPassWord = findViewById(R.id.ed_AgainPassword);
        btnSignIn = findViewById(R.id.txt_SignIn);
        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_SignUp:
                final String email = edRegEmail.getText().toString().trim();
                String password = edRegPassWord.getText().toString().trim();
                String againPassword = edRegAgainPassWord.getText().toString().trim();
                progressDialog.setMessage("Processing ...");
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                if (email.isEmpty()){
                    edRegEmail.setError("Enter Your Email");
                    return;
                }else if (password.isEmpty()){
                    edRegPassWord.setError("Enter Your Password");
                    return;
                }else if(password.length() <6){
                    edRegPassWord.setError("At least 6 character!");
                    return;
                }else if(!againPassword.equals(password)){
                    edRegAgainPassWord.setError("Nhập giống mật khẩu ở trên");
                    return;
                }else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Log.d("KIEM TRA",mAuth.getCurrentUser().getEmail());
                                UserModel userModel = new UserModel();
                                userModel.setName(email);
                                userModel.setPhoto("user.png");
                                String uid = task.getResult().getUser().getUid(); // get UID of email
                                registerController = new RegisterController();
                                registerController.addInfoController(userModel,uid);

                                Intent iNextHome = new Intent(Register_Activity.this,Home_Activity.class);
                                startActivity(iNextHome);
//                        Toast.makeText(getApplication(),"Thành công",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.txt_SignIn:
                Intent iLogIn = new Intent(Register_Activity.this,Login_Activity.class);
                startActivity(iLogIn);
                break;

        }
    }
}
