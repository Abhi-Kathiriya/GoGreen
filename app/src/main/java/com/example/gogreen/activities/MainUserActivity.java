package com.example.gogreen.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.gogreen.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainUserActivity extends AppCompatActivity {

    private ImageButton logoutBtn;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        firebaseAuth = FirebaseAuth.getInstance();

        logoutBtn = findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(MainUserActivity.this);
                builder.setTitle("Alert !")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //sign out
                                firebaseAuth.signOut();
                                checkUser();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //cancel, dismiss dialog
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });
    }
    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if( user==null ){
            startActivity(new Intent(MainUserActivity.this,LoginActivity.class));
            finish();
        }
        else{

        }
    }
}