package com.example.ladm_u2_practica1_ismaellopez

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import androidx.appcompat.app.AlertDialog
import java.lang.Thread.sleep

class Lienzo(p: MainActivity): View(p){
    //ancho: 1080 - alto: 1969
    private var tumba = Imagen(this, 375f, 1150f, R.drawable.tumba)// Tumba con tierra
    private var tumba2 = Imagen(this, 0f, 1500f, R.drawable.tumba2)// Tumba con pastito
    private var tumba3 = Imagen(this, 700f, 1500f, R.drawable.tumba2)// Tumba con pastito
    private var tumba4 = Imagen(this, 0f, 800f, R.drawable.tumba3)// Tumba sin pasto
    private var tumba5 = Imagen(this, 700f, 800f, R.drawable.tumba3)// Tumba sin pasto

    private var punteroMA = p

    private var iniciado = false
    private var incX = 0f
    private var incY = 0f
    private var incXNube = 0f
    private var incXYLuna = 1f

    private var estado = 0
    /*Estado->
    * 0: Saliendo de la tumba
    * 1: Mover de arriba a izquierda
    * 2: Mover de  izquierda a abajo
    * 3: Mover de abajo a derecha
    * 4: Mover de derecha a arriba*/

    @SuppressLint("DrawAllocation")
    override fun onDraw(c: Canvas) {
        super.onDraw(c)

        val p = Paint()

        p.color = Color.rgb(37, 40, 80)
        c.drawRect(0f, 0f, 1080f, 900f, p)

        //Pasto
        p.color = Color.rgb(56, 142, 60)
        c.drawRect(0f, 900f, 1080f, 2000f, p)

        dibujarLuna(c, p)
        dibujarArbol(c, p)
        dibujarNube(c, p)

        if( this.estado == 0 || this.estado == 1 || this.estado == 4 ){
            tumba4.pintar(c)//sin pasto
            tumba5.pintar(c)//sin pasto
            dibujarEsqueleto(c, p, this.incX, this.incY)
            tumba.pintar(c)//tierra
            tumba2.pintar(c)//pastito
            tumba3.pintar(c)//pastito
        } else {
            tumba4.pintar(c)//sin pasto
            tumba5.pintar(c)//sin pasto
            tumba.pintar(c)//tierra
            dibujarEsqueleto(c, p, this.incX, this.incY)
            tumba2.pintar(c)//pastito
            tumba3.pintar(c)//pastito
        }

    }

    fun mover(){
        when (estado){
            0 -> {
                if ( this.incY >= -350 ) { this.incY -= 5 }
                else { this.estado = 1 }
            }
            1 -> {
                if ( this.incX >= -350 ) { this.incY += 5; this.incX -= 5 }
                else { this.estado = 2 }
            }
            2 -> {
                if ( this.incY <= 350 ) { this.incY += 5; this.incX += 5 }
                else{ this.estado = 3 }
            }
            3 -> {
                if ( this.incX <= 350 ) { this.incY -= 5; this.incX += 5 }
                else { this.estado = 4 }
            }
            4 -> {
                if ( this.incY >= -350 ) { this.incY -= 5; this.incX -= 5 }
                else { this.estado = 1 }
            }
        }
        if( this.incXNube < 1500 )
            this.incXNube += 5
        else
            this.incXNube = -700f
        this.incXYLuna *= -1
        invalidate()
    }

    private fun dibujarEsqueleto(c: Canvas, p: Paint, incX: Float, incY: Float) {
        // Cuerpo
        p.color = Color.WHITE
        c.drawRect(550f+incX, 1240f+incY, 570f+incX, 1390f+incY, p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 3f
        p.color = Color.BLACK
        c.drawRect(550f+incX, 1240f+incY, 570f+incX, 1390f+incY, p)

        // Cabeza
        p.color = Color.WHITE
        p.style = Paint.Style.FILL
        c.drawCircle(560f+incX, 1200f+incY, 50f, p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 3f
        p.color = Color.BLACK
        c.drawCircle(560f+incX, 1200f+incY, 50f, p)

        // Ojos
        p.style = Paint.Style.FILL
        c.drawCircle(540f+incX, 1180f+incY, 10f, p)
        c.drawCircle(580f+incX, 1180f+incY, 10f, p)

        // Nariz
        p.style = Paint.Style.STROKE
        c.drawLine(560f+incX, 1190f+incY, 555f+incX, 1205f+incY, p)
        c.drawLine(555f+incX, 1205f+incY, 565f+incX, 1205f+incY, p)

        // Boca
        c.drawPath( generarCurva(540f+incX, 1220f+incY, 580f+incX, 1220f+incY, -15), p)

        // Cadera
        p.color = Color.WHITE
        p.style = Paint.Style.FILL
        c.drawRect(535f+incX, 1390f+incY, 585f+incX, 1415f+incY, p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 3f
        p.color = Color.BLACK
        c.drawRect(535f+incX, 1390f+incY, 585f+incX, 1415f+incY, p)

        // Pierna derecha
        p.color = Color.WHITE
        p.style = Paint.Style.FILL
        c.drawRect(575f+incX, 1390f+incY, 595f+incX, 1490f+incY, p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 3f
        p.color = Color.BLACK
        c.drawRect(575f+incX, 1390f+incY, 595f+incX, 1490f+incY, p)
        // Pierna izquierda
        p.color = Color.WHITE
        p.style = Paint.Style.FILL
        c.drawRect(525f+incX, 1390f+incY, 545f+incX, 1490f+incY, p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 3f
        p.color = Color.BLACK
        c.drawRect(525f+incX, 1390f+incY, 545f+incX, 1490f+incY, p)

        // Brazo izquierdo
        p.color = Color.WHITE
        p.strokeWidth = 13f
        c.drawLine(555f+incX, 1270f+incY, 500f+incX, 1320f+incY, p)
        c.drawLine(505f+incX, 1320f+incY, 450f+incX, 1285f+incY, p)
        // Brazo derecho
        c.drawLine(565f+incX, 1270f+incY, 640f+incX, 1360f+incY, p)
    }

    private fun generarCurva(x1: Float, y1: Float, x2: Float, y2: Float, curveRadius: Int): Path {
        val midX = x1 + (x2 - x1) / 2
        val midY = y1 + (y2 - y1) / 2
        val xDiff = midX - x1
        val yDiff = midY - y1
        val angle = Math.atan2(yDiff.toDouble(), xDiff.toDouble()) * (180 / Math.PI) - 90
        val angleRadians = Math.toRadians(angle)
        val pointX = (midX + curveRadius * Math.cos(angleRadians)).toFloat()
        val pointY = (midY + curveRadius * Math.sin(angleRadians)).toFloat()
        val path = Path()
        path.moveTo(x1, y1)
        path.cubicTo(x1, y1, pointX, pointY, x2, y2)
        return path
    }

    private fun dibujarArbol(c: Canvas, p: Paint){
        //Tronco
        p.color = Color.rgb(114, 89, 63)
        c.drawRect(425f, 650f, 650f, 1050f, p)
        //"Hojas"
        p.color = Color.rgb(56, 142, 60)
        c.drawCircle(340f, 650f, 150f, p)//izquierda inferior
        p.color = Color.BLACK
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5f
        c.drawCircle(340f, 650f, 150f, p)
        p.style = Paint.Style.FILL
        p.color = Color.rgb(56, 142, 60)

        c.drawCircle(280f, 500f, 150f, p)//izquierda central
        p.color = Color.BLACK
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5f
        c.drawCircle(280f, 500f, 150f, p)
        p.style = Paint.Style.FILL
        p.color = Color.rgb(56, 142, 60)

        c.drawCircle(340f, 350f, 150f, p)//izquierda superior
        p.color = Color.BLACK
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5f
        c.drawCircle(340f, 350f, 150f, p)
        p.style = Paint.Style.FILL
        p.color = Color.rgb(56, 142, 60)

        c.drawCircle(540f, 300f, 150f, p)//central superior
        p.color = Color.BLACK
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5f
        c.drawCircle(540f, 300f, 150f, p)
        p.style = Paint.Style.FILL
        p.color = Color.rgb(56, 142, 60)

        c.drawCircle(735f, 650f, 150f, p)//derecha inferior
        p.color = Color.BLACK
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5f
        c.drawCircle(735f, 650f, 150f, p)
        p.style = Paint.Style.FILL
        p.color = Color.rgb(56, 142, 60)

        c.drawCircle(795f, 500f, 150f, p)//derecha central
        p.color = Color.BLACK
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5f
        c.drawCircle(795f, 500f, 150f, p)
        p.style = Paint.Style.FILL
        p.color = Color.rgb(56, 142, 60)

        c.drawCircle(735f, 350f, 150f, p)//derecha central
        p.color = Color.BLACK
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5f
        c.drawCircle(735f, 350f, 150f, p)
        p.style = Paint.Style.FILL
        p.color = Color.rgb(56, 142, 60)

        c.drawCircle(540f, 525f, 250f, p)//central
    }

    private fun dibujarNube(c: Canvas, p: Paint){
        p.color = Color.argb(245, 190, 190, 190)
        c.drawCircle(80f+incXNube, 350f, 80f, p)// 1
        c.drawCircle(150f+incXNube, 325f, 80f, p)// 2
        c.drawCircle(220f+incXNube, 325f, 80f, p)// 3
        c.drawCircle(150f+incXNube, 375f, 80f, p)// 4
        c.drawCircle(220f+incXNube, 375f, 80f, p)// 5
        c.drawCircle(290f+incXNube, 350f, 80f, p)// 6

        c.drawCircle(-420f+incXNube, 550f, 80f, p)// 1
        c.drawCircle(-350f+incXNube, 525f, 80f, p)// 2
        c.drawCircle(-280f+incXNube, 525f, 80f, p)// 3
        c.drawCircle(-350f+incXNube, 575f, 80f, p)// 4
        c.drawCircle(-280f+incXNube, 575f, 80f, p)// 5
        c.drawCircle(-210f+incXNube, 550f, 80f, p)// 6
    }

    private fun dibujarLuna(c: Canvas, p: Paint){
        p.color = Color.rgb(190, 190, 190)
        c.drawCircle(1030f+incXYLuna, 50f+incXYLuna, 120f, p)
    }
}