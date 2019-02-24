package com.example.gold.taskexample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_b.*

class BActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        btn_go_c.setOnClickListener {
            val intent = Intent(this, CActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        println(Log.d("TaskTest", "B_Acitivity"))
    }

    override fun onResume() {
        super.onResume()
        println(Log.d("TaskTest", "B_Acitivity"))
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println(Log.d("TaskTest", "B_Acitivity"))
    }
    
}
