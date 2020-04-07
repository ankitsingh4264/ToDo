package com.example.todo

import android.icu.text.CaseMap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    var title: String,
    var description:String,
    var date:String,
    var time:String,
    var isfinished:Int=-1,
    @PrimaryKey(autoGenerate = true)
    var id:Long=0L





)