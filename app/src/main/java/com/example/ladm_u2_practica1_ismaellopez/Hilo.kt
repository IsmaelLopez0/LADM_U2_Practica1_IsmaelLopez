package com.example.ladm_u2_practica1_ismaellopez

class Hilo(p: MainActivity): Thread() {
    private var puntero = p
    private var mantener = true
    private var pausa = true

    override fun run() {
        super.run()

        while (mantener) {
            if ( pausa ){
                sleep(5000L)
                pausa = false
            }
            puntero.runOnUiThread {
                puntero.lienzo!!.mover()
            }
            sleep(100L)
        }
    }
}