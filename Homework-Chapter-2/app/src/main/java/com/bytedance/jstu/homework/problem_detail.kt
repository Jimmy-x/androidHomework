package com.bytedance.jstu.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class problem_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_detail)
        val problemIndex=intent.extras?.getInt("problemIndex")
        val p_title=findViewById<TextView>(R.id.problem_title)
        val p_content=findViewById<TextView>(R.id.problem_content)
        p_title.text="第${problemIndex}题-题目详情"
        p_content.text="1. 两数之和\n给定一个整数数组 nums 和一个整数目标值 target，" +
        "请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。\n " +
        "你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。\n" +
        "你可以按任意顺序返回答案。"
    }
}