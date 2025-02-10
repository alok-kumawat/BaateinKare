package com.example.baateinkare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {
TextView textView6;
EditText editText;
EditText editTextTextPassword;
Button button;
FirebaseAuth auth;
String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
android.app.ProgressDialog progressDialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("plz wait");
        progressDialog.setCancelable(false);


        auth = FirebaseAuth.getInstance();
        textView6 =  findViewById(R.id.textView6);
        editText = findViewById(R.id.editText);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.button);



        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_page.this, Register.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = editText.getText().toString();
                String Password = editTextTextPassword.getText().toString();
                if ((TextUtils.isEmpty(Email))){
                    progressDialog.dismiss();
                    Toast.makeText(login_page.this, "plz enter Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Password)) {
                    progressDialog.dismiss();
                    Toast.makeText(login_page.this, "plz enter password", Toast.LENGTH_SHORT).show();

                } else if (!Email.matches(emailPattern)){
                    progressDialog.dismiss();
                    editText.setError("proper sequence");

                } else if (Password.length()<6) {
                    progressDialog.dismiss();
                    editTextTextPassword.setError("mimimum letter six");
                    Toast.makeText(login_page.this, "mimimum letter six or more than six letter", Toast.LENGTH_SHORT).show();
                    
                }
                auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.show();
                            try {
                                Intent intent = new Intent(login_page.this, MainActivity.class);
                                startActivity(intent);
                                finish();


                            }catch (Exception e){
                                Toast.makeText(login_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(login_page.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });




            }
        });
    }
}