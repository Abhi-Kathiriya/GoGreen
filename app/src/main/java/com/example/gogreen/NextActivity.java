package com.example.gogreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NextActivity extends AppCompatActivity {

    private TextView textView1,textView2,textView3,textView4;
    private Button shopnowBtn;
    Animation fade_in_anim;
//    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

//        firebaseAuth = FirebaseAuth.getInstance();

        shopnowBtn = findViewById(R.id.shopnowBtn);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        fade_in_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        textView1.startAnimation(fade_in_anim);
        textView4.startAnimation(fade_in_anim);
        textView3.startAnimation(fade_in_anim);
        textView2.startAnimation(fade_in_anim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 5000);

        shopnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NextActivity.this, LoginActivity.class);
//                startActivity(intent);

//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                if(user==null){
//                    //user not logged in start login activity
//                    startActivity(new Intent(NextActivity.this,LoginActivity.class));
//                    finish();
//                }
//                else {
//                    //user is logged in, check user type
//                    checkUserType();
//
//                }
            }
        });
    }

//    private void checkUserType() {
//        //if user is seller, start seller main screen
//        //if user is buyer, start user main screen
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
//                            String accountType = ""+ds.child("accountType").getValue();
//                            if(accountType.equals("Seller")){
//                                //user is seller
//                                startActivity(new Intent(NextActivity.this,MainActivity.class));
//                                finish();
//                            }
//                            else{
//                                //user is buyer
//                                startActivity(new Intent(NextActivity.this, MainActivity.class));
//                                finish();
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//    }

}