package com.bytedance.jstu.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class listSearch : AppCompatActivity() {
//    private fun load(file_name:String): List<String> {
//        val fileInputStream = openFileInput(file_name)
//        var data: MutableList<String> =mutableListOf<String>()
//        //把文件内容读取进缓冲读取器（use方法会自动对BufferedReader进行关闭）
//        BufferedReader(InputStreamReader(fileInputStream)).use {
//            var line: String
//            while (true) {
//                line = it.readLine() ?: break //当有内容时读取一行数据，否则退出循环
//                data.add(line)
//            }
//        }
//        return data
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_search)
        val et = findViewById<EditText>(R.id.search_edit)
        val search_button = findViewById<Button>(R.id.search_button)
        val item_button = findViewById<Button>(R.id.search_item_tv)

        var isSearching = false
        var text_searching = ""

//        监测文本输入框
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (et.getText().toString() == "") {
                    isSearching = false
                    search_button.text = "取消"
                } else {
                    isSearching = true
                    search_button.text = "搜索"
                    text_searching = p0.toString()
                }
            }
        })


        val rv = findViewById<RecyclerView>(R.id.recycle)
        rv.layoutManager = LinearLayoutManager(this)

        val adapter = SearchItemAdapter()
        val data = (1..1000).map { "这是第${it}题" }
//        val data=load("problem_from_leetcode.txt")
//        val content = File("problem_from_leetcode.txt").readText()
//        var data: MutableList<String> =mutableListOf<String>()
//        File("problem_from_leetcode.txt").forEachLine { data.add(it) }
        adapter.setContentList(data)
        rv.adapter = adapter
        search_button.setOnClickListener {
            if (isSearching) {
                adapter.setFilter(text_searching.toString())
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
//        item_button.setOnClickListener {
//            val intent = Intent(this, problem_detail::class.java)
//            intent.putExtra("problemIndex",1)
//            startActivity(intent)
//        }
        search_button.setOnLongClickListener{
            val intentGo = Intent(this, problem_detail::class.java)
            intentGo.putExtra("problemIndex",1)
            startActivity(intentGo)
            true
        }

    }
}