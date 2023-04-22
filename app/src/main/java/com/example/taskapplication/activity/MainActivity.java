package com.example.taskapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.adapters.UserAdapter;
import com.example.taskapplication.databinding.ActivityMainBinding;
import com.example.taskapplication.room.UserDao;
import com.example.taskapplication.room.UserDatabase;
import com.example.taskapplication.room.Users;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;


    //** THIS BELOW INITIALIZATION FOR THE DATABASE
    private UserDatabase userDatabase;
    private UserDao userDao;

    //** THIS BELOW INITIALIZATION FOR THE ADAPTER CLASS
    private UserAdapter userAdapter;

    //** THE BELOW CODE IS INITIALIZATION FOR THE RECYCLERVIEW
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //**   HERE WE GET THE INSTANCE OF THE USER DATABASE CLASS
        userDatabase = UserDatabase.getInstance(this);
        userDao = userDatabase.getDao();

        //** HERE WE MAKING A INSTANCE OF THE USER ADAPTER
        userAdapter = new UserAdapter(this);


        //**  HERE WE HIDING THE TOOLBAR
//        getSupportActionBar().hide();

        //** HERE WE SET THE ADAPTER ON THE RECYCLERVIEW
        activityMainBinding.recyclerviewShowUserList.setAdapter(userAdapter);
        activityMainBinding.recyclerviewShowUserList.setLayoutManager(new LinearLayoutManager(this));


        //** NOW WE MAKE A FUNCTION THAT INSERT THE DATA

        activityMainBinding.goToAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivity(intent);
            }
        });

    }


    //** HERE WE MAKING A FUNCTION THAT FETCH A DATA FROM THE DATABASE
    private void fetchData() {
        userAdapter.clearData();
        List<Users> usersList = userDao.getAllUsersData();

        if(usersList.isEmpty())

        {
            activityMainBinding.recyclerviewShowUserList.setVisibility(View.GONE);
            activityMainBinding.emptyViewImage.setVisibility(View.VISIBLE);
            activityMainBinding.emptyViewTv.setVisibility(View.VISIBLE);

        }
        else {
            for (int i = 0; i < usersList.size(); i++) {
                Users users = usersList.get(i);
                userAdapter.addUsers(users);
            }
            activityMainBinding.recyclerviewShowUserList.setVisibility(View.VISIBLE);
            activityMainBinding.emptyViewImage.setVisibility(View.GONE);
            activityMainBinding.emptyViewTv.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        //**  HERE WE CALL THE FETCH FUNCTION , THAT FETCH THE DATA FROM THE DATABASE
        fetchData();
    }
}