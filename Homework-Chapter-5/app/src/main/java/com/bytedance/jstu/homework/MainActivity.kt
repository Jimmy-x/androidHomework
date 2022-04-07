package com.bytedance.jstu.homework


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bytedance.jstu.homework.api.Youdao
import com.bytedance.jstu.homework.api.YoudaoChar
import com.bytedance.jstu.homework.api.YoudaoRes
import com.bytedance.jstu.homework.api.YoudaoSentence
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var requestBtn: Button? = null
    private var showText: TextView? = null
    private var editText: EditText? = null
    private val client: OkHttpClient = OkHttpClient.Builder()
        .build()
    private val gson = GsonBuilder().create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestBtn = findViewById(R.id.myBtn)
        showText = findViewById(R.id.result)
        editText = findViewById(R.id.myInput)
        editText?.setOnKeyListener { view, i, keyEvent -> run {
            if(keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                translate()
                true
            } else {
                false
            }
        } }
        requestBtn?.setOnClickListener { translate() }
    }
    private fun updateShowTextView(text: String, append: Boolean = true) {
        if (Looper.getMainLooper() !== Looper.myLooper()) {
            runOnUiThread { updateShowTextView(text, append) }
        } else {
            showText?.text = if (append) showText?.text.toString() + text else text
        }
    }
    private fun translate() {
        val url = "https://dict.youdao.com/jsonapi?q=${editText?.text}"
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                updateShowTextView(e.message.toString(), false)
            }
            override fun onResponse(call: Call, response: Response) {
                val string = response.body?.string()
                val responseText = if (response.isSuccessful) {
                    var res: String = "No data"
                    val detect = gson.fromJson(string, Youdao::class.java)
                    if("fanyi" in detect.meta.dicts) {
                        val format = gson.fromJson(string, YoudaoSentence::class.java)
                        res = format.fanyi.tran
                    } else if("blng_sents_part" in detect.meta.dicts) {
                        try {
                            val format = gson.fromJson(string, YoudaoChar::class.java)
                            res = format.blng_sents_part.trs[1].tr
                        } catch (e:Exception) {
                            res = "No data"
                        }
                    } else if(res === "No data" && "web_trans" in detect.meta.dicts){
                        try {
                            val format = gson.fromJson(string, YoudaoRes::class.java)
                            res = format.webTrans.webTranslation[0].value
                        } catch (e:Exception) {
                            res = "No data"
                        }
                    } else {
                        res = "No data"
                    }
                    "\n${res}"
                } else {
                    "\n\n\nResponse fail: ${string}, http status code: ${response.code}."
                }
                updateShowTextView(responseText, false)
            }
        })
    }

}