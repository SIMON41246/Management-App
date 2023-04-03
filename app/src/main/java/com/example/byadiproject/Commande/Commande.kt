package com.example.byadiproject.Commande

    data class Commande(val count:Int,val total:Double,val date:String ){
    constructor():this(0,0.0,"")
}