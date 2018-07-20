package com.example.kotlinlab

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val path = "chmod /data/local/tmp/jre/bin/java +x | ./data/local/tmp/jre/bin/java -version"
        Thread(Runnable {
            val process = Runtime.getRuntime().exec(path)
            val line = process.errorStream.bufferedReader().readLine()
            Log.d("reslut", line)
        }).start()
    }
}
