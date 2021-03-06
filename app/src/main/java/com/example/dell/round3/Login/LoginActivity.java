package com.example.dell.round3.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.round3.Activity.ActivitiesActivity;
import com.example.dell.round3.Activity.NewActivity;
import com.example.dell.round3.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLUser;
    private EditText edtLPassword;
    private String url = "https://proyecto-movil.firebaseio.com/";
    Firebase rootUrl;
    private CurrentUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        rootUrl = new Firebase(url);
        currentUser = new CurrentUser(this);
        if(!currentUser.getUser().equals("Users")){
            start();
        }
    }


    public void btnLogin(View view) {
        edtLUser = (EditText) findViewById(R.id.edtLUser);
        edtLPassword = (EditText) findViewById(R.id.edtLPassword);
        Firebase get = rootUrl.child("Users/" + edtLUser.getText().toString());
        get.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    byte[] data = Base64.decode(dataSnapshot.child("Password").getValue().toString(), Base64.DEFAULT);
                    try {
                        String text = new String(data, "UTF-8");
                        if (text.equals(edtLPassword.getText().toString())) {
                            currentUser.setUser(edtLUser.getText().toString());
                            currentUser.setType(dataSnapshot.child("Type").getValue().toString());
                            currentUser.setName(dataSnapshot.child("Name").getValue().toString());
                            start();
                        } else  {
                            Toast.makeText(getApplicationContext(), "Contraseña invalida", Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "El nombre de usuario no existe", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast toast1 = Toast.makeText(getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
    }

    public void btnSignUp(View view) {
       viewRegister();
    }

    public void viewRegister(){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void start(){
        Intent i = new Intent(this, ActivitiesActivity.class);
        startActivity(i);
        finish();
    }
}
