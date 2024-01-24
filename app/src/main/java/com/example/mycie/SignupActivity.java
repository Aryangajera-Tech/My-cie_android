package com.example.mycie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText signup_name, signup_email, signup_password,signup_repassword;
    TextView signinText;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        signup_name = findViewById(R.id.signup_name);
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_repassword = findViewById(R.id.signup_repassword);
        signinText = findViewById(R.id.signinText);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = signup_name.getText().toString();
                String email = signup_email.getText().toString();
                String password = signup_password.getText().toString();
                String repassword = signup_repassword.getText().toString();

                if(password.equals(repassword)){
                    String user = signup_email.getText().toString().trim();
                    String pass = signup_password.getText().toString().trim();
                    if (name.isEmpty()){
                        signup_name.setError("Username cannot be empty");
                    }
                    if (user.isEmpty()){
                        signup_email.setError("Email cannot be empty");
                    }
                    if (pass.isEmpty()){
                        signup_password.setError("Password cannot be empty");
                    } else{
                        if(pass.length() < 6 ){
                            Toast.makeText(SignupActivity.this, "Password length at least 6 character", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(SignupActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }else{
                    Toast.makeText(SignupActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                }
            }
        });signinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });




    }
}