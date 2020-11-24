package com.rb.opengl_esdemo

import android.content.Context
import android.opengl.GLSurfaceView

/**
 *
 * @author RenBing
 * @date 2020/11/23 0023
 */
class MyGlSurfaceView @JvmOverloads constructor(context: Context) : GLSurfaceView(context){

    init {
        setEGLContextClientVersion(2)
        setRenderer(MyGLRender())
        renderMode = RENDERMODE_CONTINUOUSLY
    }
}