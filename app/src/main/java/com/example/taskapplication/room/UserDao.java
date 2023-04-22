package com.example.taskapplication.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    // ** HERE WE MAKING A INSERT FUNCTION THAT HELPS TO INSERT THE USER DATA.
    @Insert
    void insert(Users users);


    //**  HERE WE MAKING A UPDATE FUNCTION THAT HELPS TO UPDATE THE USER DATA.
    @Update
    void update(Users users);


    //**  HERE WE MAKING A DELETE FUNCTION THAT HELPS TO  DELETE THE USER DATA.
    //** HERE WE GET THE USER ID TO DELETE THE DATA .
    @Query("DELETE FROM Users WHERE id=:id")
    void delete(int id);

    //** NOW WE MAKE A FUNCTION THAT GET THE ALL THE USER LIST.

    @Query("SELECT * FROM users ORDER BY id DESC")
    List<Users> getAllUsersData();
}
