package com.example.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDao {


     @Insert
    fun insert(user:User)

    @Delete
    fun delete(user:User)

    @Query("SELECT * FROM User WHERE isfinished=-1")
    fun getunfinishedtask() : List<User>

    @Query("SELECT * FROM User WHERE isfinished=1")
    fun getfinishedtask() : List<User>



    @Query("Update user Set isFinished = 1 where id=:uid")
    fun finishTask(uid:Long)



    @Query("SELECT * FROM User ")
    fun getalltask() :List<User>



}