package com.example.project_text

import android.app.Application
import com.google.gson.Gson
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor


class MyApplication: Application() {
     var myApplication:MyApplication?=null
     var flutterEngine : FlutterEngine?=null
    override fun onCreate() {
        super.onCreate()
        myApplication=this
//        val flutterEngine = FlutterEngine(this)
//        flutterEngine.dartExecutor.executeDartEntrypoint(
//            DartExecutor.DartEntrypoint.createDefault()
//        )
//        FlutterEngineCache.getInstance().put("cache_engine", flutterEngine)

        // Instantiate a FlutterEngine.
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
            .put("engine_id", flutterEngine)

    }
}