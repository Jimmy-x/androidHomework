package com.bytedance.jstu.homework.homework5

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bytedance.jstu.homework.R
import java.util.*
import android.app.DatePickerDialog


class AddListActivity :  AppCompatActivity() {

    private val planname: EditText by lazy {
        findViewById(R.id.planname)
    }

    private val plantime: EditText by lazy {
        findViewById(R.id.plantime)
    }


    private val save: TextView by lazy {
        findViewById(R.id.save)
    }

    private lateinit var calendar: Calendar //定义日期



    private val dbHelper = MyDBHelper(this, "todolist.db",1)
    private var db : SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addlist)
        db = dbHelper.writableDatabase


        calendar = Calendar.getInstance()
        plantime.setOnClickListener {
            calendar = Calendar.getInstance()
            var mYear = calendar[Calendar.YEAR]
            var mMonth = calendar[Calendar.MONTH]
            var mDay = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
                    mYear = year
                    mMonth = month
                    mDay = dayOfMonth
                    if (mMonth+1<10 && mDay<10)
                    {
                        val mDate = "${year}/0${month + 1}/0${dayOfMonth}"
                        plantime.setText(mDate)
                    }
                    else if(mMonth+1>=10 && mDay<10)
                    {
                        val mDate = "${year}/${month + 1}/0${dayOfMonth}"
                        plantime.setText(mDate)
                    }
                    else if(mMonth+1>=10 && mDay>=10){
                        val mDate = "${year}/${month + 1}/${dayOfMonth}"
                        plantime.setText(mDate)
                    }
                    else
                    {
                        val mDate = "${year}/0${month + 1}/${dayOfMonth}"
                        plantime.setText(mDate)
                    }
                },
                mYear, mMonth, mDay

            )
            datePickerDialog.show()

        }


        save.setOnClickListener{
            if (planname.text.isEmpty()){
                Toast.makeText(this, "Input the task name!", Toast.LENGTH_SHORT).show()
            }
            else if (plantime.text.isEmpty()){
                Toast.makeText(this, "Input the Deadline!", Toast.LENGTH_SHORT).show()
            }

            else{

                val values = ContentValues().apply {
                    put("planName", planname.text.toString())
                    put("startTime", plantime.text.toString())
                }
                db?.insert("event", null, values)
                val intent = Intent()
                intent.action = "Main.close"
                sendBroadcast(intent)
                finish()
                startActivity(Intent().apply{setClass(this@AddListActivity, MainFiveActivity::class.java)})

            }
        }
    }
}