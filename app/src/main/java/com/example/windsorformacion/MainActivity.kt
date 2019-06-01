package com.example.windsorformacion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import com.example.windsorformacion.model.Clan
import com.example.windsorformacion.model.ClanPresente
import com.example.windsorformacion.model.Rover
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var clan: Clan = Clan()
    private var clanPresente: ClanPresente = ClanPresente(ArrayList())
    private var nombresLista: ArrayList<String> = ArrayList()

    private lateinit var textoMiembros: String

    companion object {
        const val elClan = "rovers"
        const val clanActual = "actual"
        const val losNombres = "nombres"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        textoMiembros = getString(R.string.txtListaRovers)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        restoreState(savedInstanceState)   //Restaura el estado

        val nombres : Array<String> = Array(clan.rovers.size){indice ->
            clan.rovers[indice].nombre
        }
        val myAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombres)
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = myAdapter

        ajustarListeners()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putParcelableArrayList(clanActual, clanPresente.rovers)
        outState?.putParcelableArrayList(elClan, clan.rovers)
        outState?.putStringArrayList(losNombres, nombresLista)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        restoreState(savedInstanceState) //Restaura el estado
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    fun restoreState( savedInstanceState: Bundle?){
        if(savedInstanceState == null) {

        } else {
            var rovs = savedInstanceState.getParcelableArrayList<Rover>(elClan)
            clan.rovers = if(rovs != null)
                rovs as ArrayList<Rover>
            else
                clan.rovers

            rovs = savedInstanceState.getParcelableArrayList<Rover>(clanActual)
            clan.rovers = if (rovs != null)
                rovs as ArrayList<Rover>
            else
                clan.rovers

            val nombres = savedInstanceState.getStringArrayList(losNombres)
            nombresLista = if (nombres != null)
                nombres as ArrayList<String>
            else
                nombresLista
        }
        ListarNombres()
    }

    fun ajustarListeners() {
        btnAgregar.setOnClickListener { agregarRover() }
        btnEliminar.setOnClickListener { eliminarRover() }
        btnProcesar.setOnClickListener { procesarFormacion() }
    }

    fun agregarRover(){
        if( !nombresLista.contains(spinner.selectedItem) ){
            val roverSeleccionado : Rover = clan.rovers[spinner.selectedItemPosition]
            clanPresente.rovers.add(roverSeleccionado)
            clanPresente.ordenar()

            nombresLista.clear()
            clanPresente.rovers.map { rover -> nombresLista.add(rover.nombre) }
            ListarNombres()
        }
    }

    fun eliminarRover() {
        if( nombresLista.contains(spinner.selectedItem) ){
            val roverSeleccionado : Rover = clan.rovers[spinner.selectedItemPosition]
            clanPresente.rovers.remove(roverSeleccionado)
            nombresLista.remove(roverSeleccionado.nombre)

            ListarNombres()
        }
    }

    private fun ListarNombres () {
        if (nombresLista.size > 0) {
            var prefijo = ""
            var indice = 1
            val nombres: StringBuilder = StringBuilder()
            for (nombre in nombresLista) {
                nombres.append(prefijo)
                prefijo = "\n"
                nombres.append(indice++)
                nombres.append(". ")
                nombres.append(nombre)
            }

            txtIntegrantes.text = nombres
        } else {
            txtIntegrantes.text = textoMiembros
        }
    }

    private fun procesarFormacion() {
        val intent = Intent(this, FormacionActivity::class.java)
        intent.putExtra(clanActual, clanPresente.rovers)
        startActivity(intent)
    }
}