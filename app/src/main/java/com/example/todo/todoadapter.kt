package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.boxcard.view.*

class todoadapter(val list:List<User>) : RecyclerView.Adapter<todoadapter.viewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.boxcard,parent,false)
        )

    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(list[position])


    }
    class viewholder( itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(task: User) {
            with(itemView){
                tasktitle.text=task.title
                descriptiontxt.text=task.description
                datetxt.text=task.date
                timetxt.text=task.time
            }

        }


    }

}
