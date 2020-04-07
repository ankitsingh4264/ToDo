package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_history.*

class History : AppCompatActivity() {
    val list= arrayListOf<User>()
    val db by lazy {
        Room.databaseBuilder(this,
            AppDatabase::class.java,
            "Task.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        list.addAll(db.userDao().getfinishedtask())
        Log.i("info","${list.size}")
        val adapterr=todoadapter(list)

        historyrecyclerview.apply {
            layoutManager=LinearLayoutManager(this@History,RecyclerView.VERTICAL,false)
            adapter=adapterr
        }

    }
}
