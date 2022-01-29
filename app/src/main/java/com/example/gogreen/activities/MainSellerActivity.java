package com.example.gogreen.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gogreen.R;
import com.example.gogreen.Constants;
import com.example.gogreen.adapter.AdapterProductSeller;
import com.example.gogreen.models.ModelProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainSellerActivity extends AppCompatActivity {

    private ImageButton logoutBtn,filterProductBtn,reviewsBtn,filterOrderBtn;
    private ImageView profileIv;
    private TextView nameTv,shopNameTv,emailTv,tabProductsTv,tabOrdersTv,filteredProductsTv,filteredOrdersTv;
    private RelativeLayout productsRl,ordersRl;
    private EditText searchProductEt;
    private RecyclerView productsRv,ordersRv;
    private Button addProductBtn;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private ArrayList<ModelProduct> productList;
    private AdapterProductSeller adapterProductSeller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seller);

        logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        addProductBtn = findViewById(R.id.addProductBtn);
        reviewsBtn = (ImageButton) findViewById(R.id.reviewBtn);
        profileIv = (ImageView) findViewById(R.id.profileIv);
        nameTv = (TextView) findViewById(R.id.nameTv);
        shopNameTv = (TextView) findViewById(R.id.shopNameTv);
        emailTv = (TextView) findViewById(R.id.emailTv);
        tabProductsTv = (TextView) findViewById(R.id.tabProductsTv);
        tabOrdersTv = (TextView) findViewById(R.id.tabOrdersTv);
        productsRl = (RelativeLayout) findViewById(R.id.productsRl);
        searchProductEt = (EditText) findViewById(R.id.searchProductEt);
        filterProductBtn = (ImageButton) findViewById(R.id.filterProductBtn);
        filteredProductsTv = (TextView) findViewById(R.id.filteredProductsTv);
        productsRv = (RecyclerView) findViewById(R.id.productsRv);
        filterOrderBtn =  findViewById(R.id.filterOrderBtn);
        filteredOrdersTv =  findViewById(R.id.filteredOrdersTv);
        ordersRl =  findViewById(R.id.ordersRl);
        ordersRv =  findViewById(R.id.ordersRv);


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();

        checkUser();
        loadMyInfo();
        loadAllProducts();
        showProductsUI();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign out
                firebaseAuth.signOut();
                checkUser();
            }
        });

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSellerActivity.this,ProfileEditSellerActivity.class));
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSellerActivity.this,AddProductActivity.class));
            }
        });

        tabProductsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductsUI();
            }
        });

        tabOrdersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOrdersUI();
            }
        });

        filterProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainSellerActivity.this);
                builder.setTitle("Filter Products:")
                        .setItems(Constants.productCategories1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //get selected item
                                String selected = Constants.productCategories1[which];
                                filteredProductsTv.setText(selected);
                                if(selected.equals("All")){
                                    //load all
                                    loadAllProducts();
                                }
                                else {
                                    //loads filtered
                                    loadFilteredProducts(selected);
                                }
                            }
                        })
                        .show();
            }
        });
    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if( user==null ){
            startActivity(new Intent(MainSellerActivity.this,LoginActivity.class));
            finish();
        }
        else{
            loadMyInfo();
        }
    }

    private void loadMyInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            String name = ""+ds.child("name").getValue();
                            //String accountType = ""+ds.child("accountType").getValue();
                            String shopName = ""+ds.child("shopName").getValue();
                            String profileImage = ""+ds.child("profileImage").getValue();
                            String email = ""+ds.child("email").getValue();
                            nameTv.setText(name);
                            shopNameTv.setText(shopName);
                            emailTv.setText(email);

                            try{
                                Picasso.get().load(profileImage).placeholder(R.drawable.person).into(profileIv);
                            }
                            catch (Exception e){
                                profileIv.setImageResource(R.drawable.person);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadFilteredProducts(final String selected) {
        productList = new ArrayList<>();
        //get all products
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productList.clear();
                        for (DataSnapshot ds: dataSnapshot.getChildren()){

                            String productCategory = ""+ds.child("productCategory").getValue();

                            if(selected.equals(productCategory)){
                                ModelProduct modalProduct = ds.getValue(ModelProduct.class);
                                productList.add(modalProduct);
                            }
                        }
                        //setup adapter
                        adapterProductSeller = new AdapterProductSeller(MainSellerActivity.this, productList);
                        //set adapter
                        productsRv.setAdapter(adapterProductSeller);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadAllProducts() {

        productList = new ArrayList<>();
        //get all products
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productList.clear();
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                            productList.add(modelProduct);
                        }
                        //setup adapter
                        adapterProductSeller = new AdapterProductSeller(MainSellerActivity.this, productList);
                        //set adapter
                        productsRv.setAdapter(adapterProductSeller);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void showProductsUI() {
        productsRl.setVisibility(View.VISIBLE);
        ordersRl.setVisibility(View.GONE);

        tabProductsTv.setTextColor(getResources().getColor(R.color.colorWhite));
        tabProductsTv.setBackgroundResource(R.drawable.shape_rect04);

        tabOrdersTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void showOrdersUI() {
        productsRl.setVisibility(View.GONE);
        ordersRl.setVisibility(View.VISIBLE);

        tabProductsTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabProductsTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabOrdersTv.setTextColor(getResources().getColor(R.color.colorWhite));
        tabOrdersTv.setBackgroundResource(R.drawable.shape_rect04);
    }
}