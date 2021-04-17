package com.example.listaclientes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.listaclientes.Cliente

class ListaClienteDatabase (ctx: Context): SQLiteOpenHelper(ctx, "clientes.db", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE clientes (id INTEGER PRIMARY KEY, nome TEXT, email TEXT, fone TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS clientes"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun create(nome: String, email: String, fone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("nome", nome)
        values.put("email", email)
        values.put("fone", fone)

        val _success = db.insert("clientes", null, values)
        return (("$_success").toInt() != -1)
    }

    fun read(): ArrayList<Cliente> {
        val clienteList = ArrayList<Cliente>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM clientes"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val email = cursor.getString(cursor.getColumnIndex("email"))
                    val fone = cursor.getString(cursor.getColumnIndex("fone"))
                    val cliente = Cliente(id, nome, email, fone)
                    clienteList.add(cliente)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return clienteList
    }

    fun read(id: Int): Cliente {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM clientes WHERE id = $id"
        val cursor = db.rawQuery(selectQuery, null)
        var cliente: Cliente = Cliente(id, "", "", "")
        if (cursor != null) {
            if(cursor.moveToFirst()) {
                val nome = cursor.getString(cursor.getColumnIndex("nome"))
                val email = cursor.getString(cursor.getColumnIndex("email"))
                val fone = cursor.getString(cursor.getColumnIndex("fone"))
                cliente = Cliente(id, nome, email, fone)
            }
        }
        cursor.close()
        return cliente
    }

    fun update(cliente: Cliente): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("id", cliente.id)
        values.put("nome", cliente.nome)
        values.put("email", cliente.email)
        values.put("fone", cliente.fone)
        val _success = db.update("clientes", values, "id = ?", arrayOf(cliente.id.toString()))
        return ("$_success").toInt() != -1
    }

    fun delete(id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete("clientes", "id = ?", arrayOf(id.toString()))
        return ("$_success").toInt() != -1
    }
}