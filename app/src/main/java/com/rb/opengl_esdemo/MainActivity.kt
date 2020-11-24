package com.rb.opengl_esdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "OpenGLSample01"

        val glSurfaceView = MyGlSurfaceView(this)
        setContentView(glSurfaceView)
    }
}
