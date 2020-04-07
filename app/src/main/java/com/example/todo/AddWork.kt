package com.example.todo

import android.app.Activity
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TimePicker

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_work.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.boxcard.*
import java.util.*
import android.app.AlarmManager as AlarmManager1

class AddWork : AppCompatActivity() {
    lateinit var c:Calendar
    var alarmday:Int = 0
    var alarmmonth:Int = 0
    var alarmyear:Int = 0
    var alarmhour:Int = 0
    var alarmmin:Int = 0

    lateinit var cal:Calendar


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
        setContentView(R.layout.activity_add_work)
        c= Calendar.getInstance()
        var day=c.get(Calendar.DAY_OF_MONTH)
        var month=c.get(Calendar.MONTH)
        var year=c.get(Calendar.YEAR)
        val hour=c.get(Calendar.HOUR_OF_DAY)
        val min=c.get(Calendar.MINUTE)


        dateEdt.setOnClickListener {

            var dpdd=DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                alarmday=dayOfMonth
                alarmmonth=month
                alarmyear=year
                dateEdt.setText(" $dayOfMonth/$month/$year")
            }
             ,year,month,day)
            dpdd.show()

            timeEdt.visibility=View.VISIBLE

        }

        timeEdt.setOnClickListener{
            var tpdd= TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{ timePicker: TimePicker, hour: Int, minute: Int ->

                timeEdt.setText("$hour:$minute")
                alarmhour=hour
                alarmmin=minute
            },hour,min,false)

            tpdd.show()
        }



        val result=Intent()


        btnaddtask.setOnClickListener {
            val descrip=edtdesp.text.toString()
            var date:String=""
            if (dateEdt.text.toString()==""){
                c= Calendar.getInstance()

                var day=c.get(Calendar.DAY_OF_MONTH)
                var month=c.get(Calendar.MONTH)
                var year=c.get(Calendar.YEAR)
                val hour=c.get(Calendar.HOUR_OF_DAY)
                val min=c.get(Calendar.MINUTE)
                date=" $day/$month/$year"
            }else {
                 date = dateEdt.text.toString()
            }

            var time=""
            if (timeEdt.text.toString()==""){
                time="$hour:$min"
            }else {
                time = timeEdt.text.toString()
            }
//            setalarm(date,time)
            Log.i("inffo","$descrip $date $time ${edttitle.text.toString()}")

            setResult(Activity.RESULT_OK,result)
            val user=User(edttitle.text.toString(),descrip,date,time)

            db.userDao().insert(user)



            finish()

        }

    }

    private fun setalarm(date: String, time: String) {
        if (date=="" && time==""){
            return
        }else{

            cal.set(alarmyear,alarmmonth,alarmday,alarmhour,alarmmin)
            val alarmManager :android.app.AlarmManager= getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
            val intent=Intent(this,Alertreceiver::class.java)
            val pi=PendingIntent.getActivity(this,System.currentTimeMillis().toInt(),intent,PendingIntent.FLAG_ONE_SHOT)

            alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP,cal.timeInMillis,pi)








        }

    }
}
