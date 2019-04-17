package com.example.gameapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.MotionEvent
import java.util.*

class SimpleView : View, View.OnTouchListener {
    private var circlePoints = ArrayList<Circle>()
    private var circlePassedList = ArrayList<Boolean>()
    private var i: Int = 0
    private var j: Int = 1
    private var y: Int = 0
    private var isCycleDone: Boolean = false
    private var isSettingBallDone: Boolean = false
    private var isPause: Boolean = false
    private var isGameOver: Boolean = false
    var blackBallX: Float = 0f
    var blackBallY: Float = 0f

    //constructor(context: Context): super(context) {}
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        setOnTouchListener(this)
    }

    companion object {
        val blackFill: Paint = Paint()
        val whiteFill: Paint = Paint()
        val blackThick: Paint = Paint()
        var score: Int = 0
        var lives: Int = 3
    }

    init {
        blackFill.color = Color.BLACK
        whiteFill.color = Color.WHITE
        blackThick.color = Color.BLACK
        blackThick.strokeWidth = 5.0f
        blackThick.style = Paint.Style.STROKE
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val action = event!!.action
        //val actionCode = action
        return when (action) {
            MotionEvent.ACTION_DOWN -> handleActionDown(event)
            MotionEvent.ACTION_POINTER_DOWN -> false
            //MotionEvent.ACTION_MOVE -> return handleActionMove(event)
            else -> false
        }
    }

    private fun handleActionDown(event: MotionEvent?): Boolean {
        var buttonFlags = tag as ButtonFlags
        isSettingBallDone = buttonFlags.isSettingBallDone
        Log.d("SimpleView handle", isSettingBallDone.toString())
        if (!isSettingBallDone) {
            var mX: Float = 0f
            var mY: Float = 0f
            // Getting X coordinate
            mX = event!!.x
            // Getting Y Coordinate
            mY = event.y
            circlePoints.add(Circle(mX, mY))
            circlePassedList.add(false)
            postInvalidate()
        } else {
            if (event?.x!! > (width.toFloat() / 2)) {
                if (blackBallX + 100 <= width.toFloat()) {
                    blackBallX += 100
                }
            } else {
                if (blackBallX - 100 >= 0f) {
                    blackBallX -= 100
                }
            }
            invalidate()
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var blackBallRadius: Float = 20f
        val blackBallUpperPoint: Float = blackBallY - blackBallRadius
        val blackBallLowerPoint: Float = blackBallY + blackBallRadius
        var buttonFlags = tag as ButtonFlags
        isPause = buttonFlags.isPause
        isGameOver = buttonFlags.isGameOver
        if (i == 0) {
            blackBallX = width.toFloat() / 2
            blackBallY = (height.toFloat() * 0.95).toFloat()
            i++
        } else {
            i++
        }
        if(!isGameOver) {
            //j = 0
            if (isCycleDone) {
                j++;
                isCycleDone = false
            }
            if (!isPause) {
                y += j
            }
            canvas?.drawCircle(blackBallX, blackBallY, blackBallRadius, blackFill)

            var circleSample: Circle? = null
            for (k in circlePoints.indices) {
                var isCollision: Boolean = false
                circleSample = circlePoints[k]
                var yPoint: Float = circleSample.centerY + y.toFloat()
                canvas?.drawCircle(circleSample.centerX, yPoint, 50f, blackThick)

                if ((yPoint + circleSample.radius) < height) {
                    if ((blackBallUpperPoint <= (yPoint + circleSample.radius)) || (blackBallLowerPoint <=
                                (yPoint - circleSample.radius))
                    ) {
                        if (!circlePassedList[k]) {
                            circlePassedList[k] = true
                            if (((blackBallX + blackBallRadius) > (circleSample.centerX - circleSample.radius)) &&
                                ((blackBallX - blackBallRadius) < (circleSample.centerX + circleSample.radius))
                            ) {
                                Log.d("COLLISION", "Yes")
                                isCollision = true
                            } else {
                                score++
                                isCollision = false
                                Log.d("SCORE", score.toString())
                            }
                            if (isCollision) {
                                lives--
                                Log.d("LIVES = ", lives.toString())
                            }
                        }
                    } else {
                        circlePassedList[k] = false
                    }
                }
            }

            if (y >= height) {
                isCycleDone = true
                y = 0
            }
        }
    }
}