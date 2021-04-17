package com.example.listaclientes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CadastroCliente : AppCompatActivity() {
    public lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_cliente)

        val dbHelper = ListaClienteDatabase(this)

        val editNome = findViewById<EditText>(R.id.edit_nome)
        val editEmail = findViewById<EditText>(R.id.edit_email)
        val editFone = findViewById<EditText>(R.id.edit_fone)

        val btnAdicionarCliente = findViewById<Button>(R.id.btn_add_cliente)

        val intent = intent
        val id = intent.getIntExtra("id", 0)


        if (id != 0) {
            cliente = dbHelper.read(id)
            editNome.setText(cliente.nome)
            editEmail.setText(cliente.email)
            editFone.setText(cliente.fone)
        }

        btnAdicionarCliente.setOnClickListener {
            if (editNome.text.isNotEmpty() && editEmail.text.isNotEmpty() && editFone.text.isNotEmpty()) {
                if (id != 0) {
                    val clienteUpdate: Cliente = Cliente(cliente.id, editNome.text.toString(), editEmail.text.toString(), editFone.text.toString())
                    if (dbHelper.update(clienteUpdate)) super.onBackPressed()
                    else Toast.makeText(applicationContext,"Erro ao atualizar Cliente!", Toast.LENGTH_LONG).show()
                } else {
                    if (dbHelper.create(editNome.text.toString(), editEmail.text.toString(), editFone.text.toString()))
                        super.onBackPressed()
                    else Toast.makeText(applicationContext,"Erro ao criar novo Cliente!", Toast.LENGTH_LONG).show()
                }
            } else {
                if (editNome.text.isEmpty()) editNome.error = "Digite um Nome!"
                if (editEmail.text.isEmpty()) editEmail.error = "Digite um E-mail!"
                if (editFone.text.isEmpty()) editFone.error = "Digite um Telefone!"
            }
        }
    }
}