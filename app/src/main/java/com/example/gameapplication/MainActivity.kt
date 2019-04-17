package com.example.gameapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var isSettingBallDone = false
    private var isPause = false
    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val t = Timer()

        startGameButton.setOnClickListener(View.OnClickListener {
            gameView.tag = ButtonFlags(isPause, isSettingBallDone, isGameOver)
            t.schedule(object : TimerTask() {
                override fun run() {
                    gameView.postInvalidate()
                    /*//var score  = resources.getString(R.string.score_text_view) + SimpleView.score.toString()
                    scoreTextView.text  = SimpleView.score.toString()
                    //var life  = resources.getString(R.string.lives) + SimpleView.lives.toString()
                    lifeTextView.text = SimpleView.lives.toString()*/
                    runOnUiThread {
                        var score = resources.getString(R.string.score_text_view) + SimpleView.score.toString()
                        scoreTextView.text = score
                        var life = resources.getString(R.string.lives_text_view) + SimpleView.lives.toString()
                        lifeTextView.text = life
                        if (SimpleView.lives <= 0) {
                            t.cancel()
                        }
                    }
                }
            }, 0, 1)

            startGameButton.visibility = Button.GONE
            endGameButton.visibility = Button.VISIBLE
        })

        gameView.tag = ButtonFlags(isPause,isSettingBallDone, isGameOver)
        newGameButton.setOnClickListener(View.OnClickListener {
            isSettingBallDone = true

            newGameButton.visibility = Button.GONE
            startGameButton.visibility = Button.VISIBLE
            endGameButton.visibility = Button.GONE
        })

        endGameButton.setOnClickListener(View.OnClickListener {
            t.cancel()
            /*isGameOver=true
            isSettingBallDone = false
            endGameButton.visibility = Button.GONE*/
            newGameButton.visibility = Button.VISIBLE
        })

        pauseButton.setOnClickListener(View.OnClickListener {
            isPause = true
            gameView.tag =  ButtonFlags(isPause, isSettingBallDone, isGameOver)
            pauseButton.visibility = Button.GONE
            resumeButton.visibility = Button.VISIBLE
        })

        resumeButton.setOnClickListener(View.OnClickListener {
            isPause = false
            gameView.tag = ButtonFlags(isPause, isSettingBallDone, isGameOver)
            resumeButton.visibility = Button.GONE
            pauseButton.visibility = Button.VISIBLE
        })
    }


}