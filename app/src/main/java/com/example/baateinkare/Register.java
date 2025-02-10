package com.example.baateinkare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Register extends AppCompatActivity {
    TextView textView5;
    EditText editText8;
    EditText editText;
    EditText editTextTextPassword;
    EditText editTextTextPassword1;
    Button button;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri imageURI;
    String imageuri;
    android.app.ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("plz wait");
        progressDialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        textView5 = findViewById(R.id.textView5);
        editText8 = findViewById(R.id.editText8);
        editText  = findViewById(R.id.editText);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        editTextTextPassword1 = findViewById(R.id.editTextTextPassword1);
        button  = findViewById(R.id.button);


        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, login_page.class);
                startActivity(intent);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = editText.getText().toString();
                String Email = editText8.getText().toString();
                String Password = editTextTextPassword.getText().toString();
                String CPassword = editTextTextPassword1.getText().toString();
                String Status = "using this application";

                if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)||
                        TextUtils.isEmpty(CPassword)){
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, "Please Enter Valid information", Toast.LENGTH_SHORT).show();


                } else if (!Email.matches(emailPattern)) {
                    progressDialog.dismiss();
                    editText8.setError("Plz valid information");

                } else if (Password.length()<6) {
                    progressDialog.dismiss();
                    editTextTextPassword.setError("minimum password six letter");

                } else if (!Password.equals(CPassword)) {
                    progressDialog.dismiss();
                    editTextTextPassword.setError("plz valid same as password");

                }else {

                    auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                String Id = task.getResult().getUser().getUid();
                                DatabaseReference reference = database.getReference().child("user").child(Id);
                                StorageReference storageReference = storage.getReference().child("Uploaad").child(Id);

                                if (imageURI!=null){
                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageuri = uri.toString();
                                                        Users users = new Users(Id,Name,Email,Password,imageuri,Status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    progressDialog.show();
                                                                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                                    Intent intent=new Intent(Register.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }else {
                                                                    Toast.makeText(Register.this, "Error on user interface", Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }else {

                                    imageuri = "https://firebasestorage.googleapis.com/v0/b/baatein-kare-4ed5c.appspot.com/o/www.jpeg?alt=media&token=0add6a8e-1584-4974-9a82-dcabda123d82";
                                    Users users = new Users(Id,Name,Email,Password,imageuri,Status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressDialog.show();
                                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                                                Intent intent=new Intent(Register.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                Toast.makeText(Register.this, "Error on user interface", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                }

                            }else {
                                Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

    }
}



