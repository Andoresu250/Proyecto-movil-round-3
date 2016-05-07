package com.example.dell.round3.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    //ESTA ES LA CLASE QUE MANTIENE LA SESION DEL USUARIO
    private CurrentUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        rootUrl = new Firebase(url);
        currentUser = new CurrentUser(this);
    }


    public void btnLogin(View view) {
        edtLUser = (EditText) findViewById(R.id.edtLUser);
        edtLPassword = (EditText) findViewById(R.id.edtLPassword);
        // CON ESTA URL ACCEDO A LOS USUARIOS QUE LOS LLAME USERS EN LA DB MAS EL NOMBRE DEL USUARIO, PORQUE
        // LAS KEY DE LOS USUARIOS SON EL USUARIO QUE SE REGISTRAN
        Firebase get = rootUrl.child("Users/" + edtLUser.getText().toString());

        // ESTE ES EL EVENTO PARA HACER EL GET DE USUARIOS, AQUI SOLO BUSCA EL USUARIO QUE SE QUIERE LOGEAR
        get.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // ESTE IF ES EN CASO, DE QUE EL DATASNAPSHOT SEA NULL ES PORQUE EL USUARIO NO EXISTE
                if (dataSnapshot.getValue() != null) {

                    //TODO ESTOS HACE PARTE DE LA DESCODIFICACION DE LA PASSWORD, SI ENTRA AL IF ES PORQUE EL USUARIO
                    // EXISTE
                    byte[] data = Base64.decode(dataSnapshot.child("Password").getValue().toString(), Base64.DEFAULT);
                    try {
                        String text = new String(data, "UTF-8");
                        if (text.equals(edtLPassword.getText().toString())) {
                            // ESTOS TOAST LOS PUEDES QUITAR SOLO ME DICEN SI ENTRO O NO
                            Toast toast1 = Toast.makeText(getApplicationContext(), "entro", Toast.LENGTH_SHORT);
                            toast1.show();
                            // AQUI LO QUE HAGO ES QUE CUANDO EL USUARIO SE LOGEA, GUARDA SU USERNAME Y SU TYPO EN CURRENT USER
                            currentUser.setUser(edtLUser.getText().toString());
                            currentUser.setType(dataSnapshot.child("Type").getValue().toString());
                            // ESTE ES EL METODO DEL INTENT QUE ME MANDA A LA VISTA DEL REGISTRO
                            // ERA PARA PROBAR QUE CAMBIARA DE VISTA CUANDO SE LOGEARA
                            //AQUI DEBERIA MANDARLO A LA VISTA DE LAS ACTIVIDADES
                            start();

                        } else  {
                            // AQUI ENTRA SI EL USERNAME ESTA CORRECTO PERO LA CONTRASEÑA NO
                            Toast toast1 = Toast.makeText(getApplicationContext(), "contraseña invalida", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                } else {
                    // Y AQUI ESTA SI EL USUARIO NO EXISTE
                    Toast toast1 = Toast.makeText(getApplicationContext(), "no entro", Toast.LENGTH_SHORT);
                    toast1.show();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast toast1 = Toast.makeText(getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
    }
    // ESTO SOLO MANDA A LA VISTA DE REGISTRO
    public void btnSignUp(View view) {
       viewRegister();
    }

    public void viewRegister(){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private void start(){
        Intent i = new Intent(this, NewActivity.class);
        startActivity(i);
    }
}
