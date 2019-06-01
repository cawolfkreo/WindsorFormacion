package com.example.windsorformacion.worker

import com.example.windsorformacion.model.ClanPresente
import com.example.windsorformacion.model.Rover

class FormacionWorker (var clan: ClanPresente) {

    fun formacion(): ArrayList<Rover>{
        val formacionProcesada: ArrayList<Rover> = clan.formacion()
        return formacionProcesada
    }
}