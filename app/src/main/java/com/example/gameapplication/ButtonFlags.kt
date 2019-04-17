package com.example.gameapplication

class ButtonFlags {
    var isPause : Boolean = false
    var isSettingBallDone : Boolean = false
    var isGameOver : Boolean = false

    constructor(isPause: Boolean = false, isSettingBallDone: Boolean = false, isGameOver: Boolean = false) {
        this.isPause = isPause
        this.isSettingBallDone = isSettingBallDone
        this.isGameOver = isGameOver
    }
}