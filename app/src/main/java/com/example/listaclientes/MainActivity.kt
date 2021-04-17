package com.example.listaclientes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    public lateinit var lista: ListView;
    val dbHelper = ListaClienteDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdicionar = findViewById<Button>(R.id.btn_adicionar)
        lista = findViewById<ListView>(R.id.list_cliente)
        Atualizar()

        btnAdicionar.setOnClickListener {
            val aux = Intent(this, CadastroCliente::class.java)
            startActivity(aux)
        }

        lista.setOnItemLongClickListener { parent, view, position, id ->
            val cliente = lista.adapter.getItem(position) as Cliente
            dbHelper.delete(cliente.id)
            Atualizar()
            return@setOnItemLongClickListener(true)
        }

        lista.setOnItemClickListener { parent, view, position, id ->
            val cliente = lista.adapter.getItem(position) as Cliente
            val aux = Intent(this, CadastroCliente::class.java).apply {
                putExtra("id", cliente.id)
            }
            startActivity(aux)
        }
    }

    override fun onResume() {
        super.onResume()
        Atualizar()
    }

    fun Atualizar() {
        val ListaClientes = dbHelper.read()
        lista.adapter = ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, ListaClientes)
    }
}