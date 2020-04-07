package com.example.todo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import androidx.room.Room
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity() {
    var list = arrayListOf<User>()
    lateinit var dapter: todoadapter

    val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "Task.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)
        list.addAll(db.userDao().getunfinishedtask())
        dapter = todoadapter(list)
        recyclerview.apply {

            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = dapter

        }
        val intentt = Intent(this, AddWork::class.java)
        floatingbtn.setOnClickListener {
            startActivityForResult(intentt, 999)

        }


        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    var position = viewHolder.adapterPosition
                    val temp = list.get(position)
                    when (direction) {

                        ItemTouchHelper.LEFT -> {
                            list.removeAt(position)
                            dapter.notifyItemRemoved(position)
                            var flag = 0;




                            Snackbar.make(recyclerview, "Task Deleted", Snackbar.LENGTH_LONG)
                                .setAction("Undo", View.OnClickListener {
                                    list.add(position, temp)
                                    dapter.notifyItemInserted(position)
                                    flag = 1
                                })
                                .show()


                            val res = GlobalScope.launch(Dispatchers.Main) {

                                delay(3000)
                                Log.i("tag", "$flag")
                                if (flag == 0) {
                                    db.userDao().delete(temp)
                                }


                            }
                        }

                        ItemTouchHelper.RIGHT -> {


                            db.userDao().finishTask(dapter.getItemId(position))

                            list.removeAt(position)
                            dapter.notifyItemRemoved(position)


                        }


                    }


                }

            }

        var ItemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        ItemTouchHelper.attachToRecyclerView(recyclerview)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 999 && resultCode == Activity.RESULT_OK) {

            list.clear()
            list.addAll(db.userDao().getunfinishedtask())
            dapter.notifyDataSetChanged()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history -> {
                startActivity(Intent(this, History::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
