package com.bytedance.jstu.homework

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var clickCounter=0
        val hello_text = findViewById<TextView>(R.id.hello_text)
        val robotImg= findViewById<ImageView>(R.id.robotImg)
        val intentToList = Intent(this, listSearch::class.java)
        robotImg.setOnClickListener {
            clickCounter++
            if (clickCounter==1){
                hello_text.text = "Hello SJTU!"
                AlertDialog.Builder(this)
                .setTitle("Tips")
                .setMessage("Click One More Time, please!")
                .setPositiveButton("ok") { _: DialogInterface, _: Int ->
                    Log.d("ddd", "click ok")
                }
                .setNegativeButton("cancel") { _: DialogInterface, _: Int ->
                    Log.d("ddd", "click cancel")
                }
                .show()
            }
            else{
                clickCounter=0
                startActivity(intentToList)
                finish()
            }

        }
    }
}