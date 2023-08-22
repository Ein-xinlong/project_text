package com.example.project_text

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StandardMessageCodec

class MainActivity : AppCompatActivity() {
    var flutterEngine : FlutterEngine?=null
    var intColor = intArrayOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flutterEngine = FlutterEngine(this)
        // Configure an initial route.

        val map = HashMap<String,Any>()
        map["id"] = 10010
        map["type"] = "电信"
        map["routeName"] = "/a"
        val json = Gson().toJson(map)
        flutterEngine?.navigationChannel?.setInitialRoute(json)
        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine?.dartExecutor?.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
        FlutterEngineCache
            .getInstance()
            .put("engine_iddddd", flutterEngine)
        findViewById<Button>(R.id.normal)?.setOnClickListener {
            val intent = FlutterActivity.withCachedEngine("engine_iddddd")
                .build(this)
            startActivity(intent)
            setMessageChannel(flutterEngine!!)
        }


    }


    fun setMessageChannel(flutterEngine: FlutterEngine) {
        val lobjectBasicMessageChannel = BasicMessageChannel(
            flutterEngine.dartExecutor.binaryMessenger,//flutter 引擎
            "101",//通道表示
            StandardMessageCodec.INSTANCE//信息编码
        )
        lobjectBasicMessageChannel.setMessageHandler { message, reply ->
            Log.i("TAG", "setMessageChannel: $message")
            findViewById<TextView>(R.id.btn3).text="flutter 返回的消息："+message.toString()
            reply.reply("我是 Android端发送的回执消息")
        }

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}