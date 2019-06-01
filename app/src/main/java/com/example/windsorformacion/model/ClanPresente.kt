package com.example.windsorformacion.model

/**
 * Clase para modelar el clan que se encuentra presente
 */
class ClanPresente(var rovers :ArrayList<Rover>) {

    fun formacion(): ArrayList<Rover> {
        val formacion : ArrayList<Rover> = ArrayList()
        formacion.addAll(rovers)
        var i = 0
        var direccion = true
        for(rover in rovers){
            if(direccion){
                formacion[i] = rover
                i++
            } else {
                formacion[rovers.size - i] = rover
            }
            direccion = !direccion
        }
        return formacion
    }

    fun ordenar() {
        val lista = rovers.sortedWith(compareBy { it.posicion })
        rovers = ArrayList(lista)
    }
}