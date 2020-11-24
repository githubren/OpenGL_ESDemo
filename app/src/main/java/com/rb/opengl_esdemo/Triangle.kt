package com.rb.opengl_esdemo

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *
 * @author RenBing
 * @date 2020/11/23 0023
 */
class Triangle {

    private var vertexBuffer : FloatBuffer? = null
    //绘制形状的顶点数量
    private val COORDINATES_PRE_VERTEX = 3
    //用于存取attribute修饰的变量的位置编号
    private var positionHandle = 0
    //用于存取uniform修饰的变量的位置编号
    private var colorHandle = 0
    private val trainagleCoords = floatArrayOf(
        0f,1f,0f,
        -0.5f,0f,0f,
        0.5f,0f,0f
    )
    private val color = floatArrayOf(1f,0f,0f,1f)
    private val vertexCount = trainagleCoords.size/COORDINATES_PRE_VERTEX
    private val vertexStride = COORDINATES_PRE_VERTEX*4
    private var program = 0
    /**
     *  顶点着色器代码
     *  attribute变量（属性变量）只能用于顶点着色器中
     *  uniform变量（一致变量）用来将数据值从应用程序传递到顶点着色器或者元着色器
     *  varying变量（易变变量）是从顶点着色器传递到片元着色器的数据变量
     *  gl_Position（必须）为内建变量，表示变换后点的空间位置
     */
    private val vertexShaderCode =
        "attribute vec4 vPosition;"+//应用程序传入顶点着色器的顶点位置
                "void main(){" +
                "gl_Position = vPosition;"+//设置此次绘制此顶点位置
                "}"
    /**
     * 片元着色器代码
     */
    private val fragmentShaderCode =
        "precision mediump float;"+//设置工作精度
                "uniform vec4 vColor;"+//应用程序传入着色器的颜色变量
                "void main(){" +
                "gl_FragColor = vColor;" +//颜色值传给gl_FragColor内建变量，完成片元的着色
                "}"
    init {
        val byteBuffer = ByteBuffer.allocateDirect(trainagleCoords.size*4).apply {
            order(ByteOrder.nativeOrder())
        }
        vertexBuffer = byteBuffer.asFloatBuffer()
        vertexBuffer?.put(trainagleCoords)
        vertexBuffer?.position(0)
        //记载编译顶点渲染器
        val vertexShader = MyGLRender.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode)
        //加载片元编译渲染器
        val fragmentShader = MyGLRender.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode)
        //创建空的程式
        program = GLES20.glCreateProgram()
        //attach顶点渲染器
        GLES20.glAttachShader(program,vertexShader)
        //attach片元渲染器
        GLES20.glAttachShader(program,fragmentShader)
        //链接程式
        GLES20.glLinkProgram(program)
    }

    fun draw(){
        //使用GLSL程式
        GLES20.glUseProgram(program)
        //获取shader代码中的变量索引
        //Java代码中需要获取shader代码中定义的变量suo'yin，用于在后面的绘制代码中进行赋值
        //遍历索引在GLSL程式生命周期内都是固定的，只需获取一次
        positionHandle = GLES20.glGetAttribLocation(program,"vPosition")
        //绑定vertex坐标值，调用此方法告诉openGL，它可以在缓冲区vertexBuffer中获取vPosition的数据
        GLES20.glVertexAttribPointer(positionHandle,COORDINATES_PRE_VERTEX,
            GLES20.GL_FLOAT,false,
            vertexStride,vertexBuffer)
        GLES20.glEnableVertexAttribArray(positionHandle)
        colorHandle = GLES20.glGetUniformLocation(program,"vColor")
        GLES20.glUniform4fv(colorHandle,1,color,0)
        //开始绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,vertexCount)
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}