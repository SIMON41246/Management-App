package com.example.byadiproject.Client

data class Client(val id:String, val name:String, val price:Double, var total:Double, val tele:String){
    constructor():this("","",0.0,0.0,"")
}
