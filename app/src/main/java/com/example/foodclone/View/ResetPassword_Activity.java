package com.example.foodclone.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword_Activity extends AppCompatActivity implements View.OnClickListener {
    private Button btnResetPassword;
    private TextView btnSignUp;
    private EditText edEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reset_password);

        mAuth= FirebaseAuth.getInstance();

        btnResetPassword = findViewById(R.id.btn_ResetPassword);
        btnSignUp = findViewById(R.id.txt_SignUp);
        edEmail = findViewById(R.id.ed_EmailReset);

        btnResetPassword.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    private boolean CheckValidateEmail(String email){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
            return false;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ResetPassword:
                String email = edEmail.getText().toString().trim();
                // if == true thì resetPassword
                if(CheckValidateEmail(email)){
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPassword_Activity.this, "Kiểm tra hộp thư Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(ResetPassword_Activity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.txt_SignUp:
                Intent iRegisterNew = new Intent(ResetPassword_Activity.this,Register_Activity.class);
                startActivity(iRegisterNew);
                break;
        }
    }
}
