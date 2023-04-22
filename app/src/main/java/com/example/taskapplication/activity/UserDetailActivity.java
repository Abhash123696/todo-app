package com.example.taskapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.taskapplication.R;
import com.example.taskapplication.databinding.ActivityUserDetailBinding;
import com.example.taskapplication.room.UserDao;
import com.example.taskapplication.room.UserDatabase;
import com.example.taskapplication.room.Users;
import com.shashank.sony.fancytoastlib.FancyToast;

public class UserDetailActivity extends AppCompatActivity {
    ActivityUserDetailBinding activityUserDetailBinding;

    //** THIS BELOW INITIALIZATION FOR THE DATABASE
    private UserDatabase userDatabase;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserDetailBinding = ActivityUserDetailBinding.inflate(getLayoutInflater());
        setContentView(activityUserDetailBinding.getRoot());


        //**   HERE WE GET THE INSTANCE OF THE USER DATABASE CLASS
        userDatabase = UserDatabase.getInstance(this);
        userDao = userDatabase.getDao();


        //** HERE WE GET THE ALL THE DATA FROM THE MAIN ACTIVITY
        int id = getIntent().getExtras().getInt("id");
        String name = getIntent().getExtras().getString("name");
        String email = getIntent().getExtras().getString("email");
        String address = getIntent().getExtras().getString("address");
        String description = getIntent().getExtras().getString("description");
        String gender = getIntent().getExtras().getString("gender");



        activityUserDetailBinding.tvUsernameShowInDetailActivity.setText(name);
        activityUserDetailBinding.tvUserEmailShowInDetailActivity.setText(email);
        activityUserDetailBinding.tvUserAddressShowInDetailActivity.setText(address);
        activityUserDetailBinding.tvUserDescriptionShowInDetailActivity.setText(description);



        if (gender.equalsIgnoreCase("Male")){
            activityUserDetailBinding.radioBtnMale.setChecked(true);
        } else if (gender.equalsIgnoreCase("Female")) {
            activityUserDetailBinding.radioBtnFemale.setChecked(true);
        }



        //** HERE WE WRITE A FUNCTION TO UPDATE THE USER DETAILS
        activityUserDetailBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //**  HERE WE GET THE ALL THE DATA FROM THE INPUT FIELD
                int genid=activityUserDetailBinding.radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(genid);
                String gender=radioButton.getText().toString();
                String name = activityUserDetailBinding.tvUsernameShowInDetailActivity.getText().toString().trim();
                String email = activityUserDetailBinding.tvUserEmailShowInDetailActivity.getText().toString().trim();
                String address = activityUserDetailBinding.tvUserAddressShowInDetailActivity.getText().toString().trim();
                String description = activityUserDetailBinding.tvUserDescriptionShowInDetailActivity.getText().toString().trim();


                Users users = new Users(id,name,email,address,gender,description);


                userDao.update(users);

                //** HERE WE ADD THE DATA ON RECYCLERVIEW ,WHEN USER IS  ADD THE DATA
                activityUserDetailBinding.tvUsernameShowInDetailActivity.setText(" ");
                activityUserDetailBinding.tvUserEmailShowInDetailActivity.setText(" ");
                activityUserDetailBinding.tvUserAddressShowInDetailActivity.setText(" ");
                activityUserDetailBinding.tvUserDescriptionShowInDetailActivity.setText(" ");

                FancyToast.makeText(getApplicationContext()," Data is Updated Successfully .",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}