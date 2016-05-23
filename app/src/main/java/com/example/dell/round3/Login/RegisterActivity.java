package com.example.dell.round3.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.round3.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtRUser;
    private EditText edtREmail;
    private EditText edtRPassword;
    private EditText edtRName;
    private TextView tvUser;
    private TextView tvUserType;
    private String url = "https://proyecto-movil.firebaseio.com/";

    private CurrentUser currentUser;
    Firebase rootUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);
        currentUser = new CurrentUser(this);

        rootUrl = new Firebase(url);

        tvUser = (TextView) findViewById(R.id.tvUser);
        tvUserType = (TextView) findViewById(R.id.tvUserType);
        tvUser.setText(currentUser.getUser());
        tvUserType.setText(currentUser.getType());
    }

    public void btnRegister(View view) throws UnsupportedEncodingException {
        edtRUser = (EditText) findViewById(R.id.edtRUser);
        rootUrl.child("Users/" + edtRUser.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    edtREmail = (EditText) findViewById(R.id.edtREmail);
                    edtRPassword = (EditText) findViewById(R.id.edtRPassword);
                    edtRName = (EditText) findViewById(R.id.edtRName);
                    Map<String, String> post1 = new HashMap<String, String>();
                    post1.put("Email", edtREmail.getText().toString());
                    byte[] data = new byte[0];
                    try {
                        data = edtRPassword.getText().toString().getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                    post1.put("Password", base64);
                    post1.put("Name", edtRName.getText().toString());
                    post1.put("Type", "Usuario");
                    rootUrl.child("Users").child(edtRUser.getText().toString()).setValue(post1);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Este nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


    }

    public void btnLogout(View view) {
        currentUser.setUser("Users");
        currentUser.setType("Nothing");
        tvUser.setText(currentUser.getUser());
        tvUserType.setText(currentUser.getType());
    }

}
