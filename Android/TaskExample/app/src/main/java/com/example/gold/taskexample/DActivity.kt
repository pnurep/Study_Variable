package com.example.gold.taskexample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_d.*

class DActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d)

        btn_go_b.setOnClickListener {
            val intent = Intent(this, BActivity::class.java)
            startActivity(intent)
        }
    }
}
