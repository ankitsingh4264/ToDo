package com.example.todo

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [User::class],version = 3)
 abstract  class AppDatabase :RoomDatabase(){

    abstract fun userDao():UserDao
}