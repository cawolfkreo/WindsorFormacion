package com.example.windsorformacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.windsorformacion.model.ClanPresente
import com.example.windsorformacion.model.Rover
import com.example.windsorformacion.worker.FormacionWorker
import kotlinx.android.synthetic.main.activity_formacion.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FormacionActivity : AppCompatActivity(), CoroutineScope  {

    private var clanPresente: ClanPresente = ClanPresente(ArrayList())
    private lateinit var textoMiembros: String

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object {
        const val clanActual: String = "actual"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formacion)

        textoMiembros = getString(R.string.txtNada)

        if(savedInstanceState != null)
            restoreState(savedInstanceState)     //Restaura el estado
        else {
            GlobalScope.launch { procesarFormacion() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putParcelableArrayList(clanActual, clanPresente.rovers)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        restoreState(savedInstanceState) //Restaura el estado
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    fun restoreState( savedInstanceState: Bundle?){
        if(savedInstanceState == null) {

        } else {
            val rovs = savedInstanceState.getParcelableArrayList<Rover>(clanActual)
            clanPresente.rovers = if(rovs != null)
                rovs as ArrayList<Rover>
            else
                clanPresente.rovers
        }
        ListarNombres()
    }

    private fun ListarNombres(){
        if (!clanPresente.rovers.isEmpty()) {
            var prefijo = ""
            var indice = 1
            val nombres: StringBuilder = StringBuilder()
            for (rover in clanPresente.rovers) {
                nombres.append(prefijo)
                prefijo = "\n"
                nombres.append(indice++)
                nombres.append(". ")
                nombres.append(rover.nombre)
            }
            txtFormacion.text = nombres
        } else {
            txtFormacion.text = textoMiembros
        }
    }

    private suspend fun procesarFormacion() {
        coroutineScope {
            val lista = intent.getParcelableArrayListExtra<Rover>(clanActual)

            clanPresente = ClanPresente (lista)

            val formacionTrabajador = FormacionWorker(clanPresente)
            val formacion = async(Dispatchers.Default) { formacionTrabajador.formacion() }
            val resultado = formacion.await()

            clanPresente.rovers = resultado

            launch (Dispatchers.Main){ ListarNombres() }
        }
    }
}
