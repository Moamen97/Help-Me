package com.helpme

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.helpme.LoadingActivity.progress.p
import kotlinx.android.synthetic.main.activity_loading.*
import Model.postData.post
import android.os.Handler


class LoadingActivity : AppCompatActivity() {
    object progress {
        var p = 10.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        LoadingActivity.progress.p = 10.0

        var handler = Handler()
        val r = object : Runnable {
            override fun run() {
                if (LoadingActivity.progress.p >= 100) {
                    loadingProgressbar.progress = 100
                    finish()
                }
                loadingProgressbar.progress = LoadingActivity.progress.p.toInt()
                LoadingActivity.progress.p += .05
                if (LoadingActivity.progress.p >= 100)
                    LoadingActivity.progress.p -= .05
                handler.postDelayed(this, 100)
            }
        }
        handler.postDelayed(r, 100)
    }
}
