package com.example.windsorformacion.model

/**
 * Clase para modelar al clan.
 */
class Clan {

    var rovers: ArrayList<Rover>

    init {
        var i = 0

        val preRovers: ArrayList<Rover> = ArrayList();
        preRovers.addAll(
            listOf(
                Rover("Presidente: Mariana Barragán", i++),
                Rover("Vice: Mariana Pinilla", i++),
                Rover("Camila Jaimes", i++),
                Rover("Laura Martelo", i++),
                Rover("Angélica Briceño", i++),
                Rover("Nicolás Arévalo Hurtado", i++),
                Rover("Gabriel Ortega", i++),
                Rover("Miguel Monsalve", i++),
                Rover("Anita", i++),
                Rover("Daniela Bogotá", i++),
                Rover("Camilo Zambrano", i++),
                Rover("Juan Pablo", i++),
                Rover("Efarin Pinilla", i++),
                //Rover("Mariana Pinilla", i++),           VICEPRECIDENTE
                Rover("Felipe", i++),
                Rover("Daniel Viteri", i++),
                Rover("Corinne", i++),
                Rover("Natalia", i++),
                Rover("Daniel González", i++),
                Rover("Kimberly López", i++),
                Rover("Camilo Ayala", i++),
                Rover("Mateo Floriano", i++),
                //Rover("Mariaa Barragán", i++),         PRESIDENTE
                Rover("Jonathan Amaya", i++),
                Rover("Juan Andrés Rodrígues", i++),
                Rover("Nicolas Cardozo", i++),
                Rover("Maria Gabriela Pabón", i++),
                Rover("Juan David Pabón", i)))

        rovers = preRovers
    }

}