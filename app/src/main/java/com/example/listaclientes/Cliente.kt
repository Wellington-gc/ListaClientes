package com.example.listaclientes

class Cliente (val id: Int, val nome: String, val email: String, val fone: String){
    public override fun toString(): String {
        return this.id.toString() + " - " + this.nome + " (" + this.email + ")\n" + this.fone
    }
}