package com.example.gameapplication

class Circle {
    var centerX : Float = 0f
    var centerY : Float = 0f
    var radius : Float = 70.0f

    constructor(centerX: Float, centerY: Float) {
        this.centerX = centerX
        this.centerY = centerY
    }

    constructor(centerX: Float, centerY: Float, radius: Float) {
        this.centerX = centerX
        this.centerY = centerY
        this.radius = radius
    }


}