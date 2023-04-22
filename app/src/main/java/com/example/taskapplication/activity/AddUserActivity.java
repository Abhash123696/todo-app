package com.example.taskapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskapplication.databinding.ActivityAddUserBinding;
import com.example.taskapplication.room.UserDao;
import com.example.taskapplication.room.UserDatabase;
import com.example.taskapplication.room.Users;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Pattern;

public class AddUserActivity extends AppCompatActivity {

    private ActivityAddUserBinding activityAddUserBinding;
    private RadioButton radioButton;

    //** THIS BELOW INITIALIZATION FOR THE DATABASE
    private UserDatabase userDatabase;
    private UserDao userDao;

    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddUserBinding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(activityAddUserBinding.getRoot());


        //**   HERE WE GET THE INSTANCE OF THE USER DATABASE CLASS
        userDatabase = UserDatabase.getInstance(this);
        userDao = userDatabase.getDao();

        activityAddUserBinding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllFields();


                if (isAllFieldsChecked) {
                    if (activityAddUserBinding.radioGroup.getCheckedRadioButtonId() == -1) {
                        FancyToast.makeText(getApplicationContext(), " Please select Gender .", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                    }
                    else {
                        insertData();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                }

            }
        });

    }

    //***** HERE WE MAKE A FUNCTION THAT ADD THE USER
    private void insertData() {


        //**  HERE WE GET THE ALL THE DATA FROM THE INPUT FIELD
        int genid = activityAddUserBinding.radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(genid);
        String gender = radioButton.getText().toString();


        String name = activityAddUserBinding.name.getText().toString().trim();
        String email = activityAddUserBinding.email.getText().toString().trim();
        String address = activityAddUserBinding.address.getText().toString().trim();
        String description = activityAddUserBinding.description.getText().toString().trim();


        String capitalName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        String capitalAddress = address.substring(0, 1).toUpperCase() + address.substring(1).toLowerCase();
        String capitalDescription = description.substring(0, 1).toUpperCase() + description.substring(1).toLowerCase();
        //** HERE WE ACTUALLY INSERTING THE USER DATA
        Users users = new Users(0, capitalName, email, capitalAddress, gender, capitalDescription);
        userDao.insert(users);

        //** HERE WE ADD THE DATA ON RECYCLERVIEW ,WHEN USER IS  ADD THE DATA
        activityAddUserBinding.name.setText(" ");
        activityAddUserBinding.email.setText(" ");
        activityAddUserBinding.address.setText(" ");
        activityAddUserBinding.description.setText(" ");

        FancyToast.makeText(getApplicationContext(), " Data is Added Successfully .", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
    }


    //**  HERE WE MAKE A FUNCTION THAT VALIDATE FORM FIELD IS EMPTY OR NOT.
    private boolean CheckAllFields() {


        if (activityAddUserBinding.name.length() == 0) {
            activityAddUserBinding.name.setError("Name is required");
            return false;
        }

        if (!isValidEmailId((activityAddUserBinding.email.getText().toString().trim()))) {
            FancyToast.makeText(getApplicationContext(), " Please Enter Valid Email Address .", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        }

        if (activityAddUserBinding.address.length() == 0) {
            activityAddUserBinding.address.setError("Address is required");
            return false;
        } else if (activityAddUserBinding.description.length() == 0) {
            activityAddUserBinding.description.setError("Description is required");
            return false;
        }


        // after all validation return true.
        return true;
    }


    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}