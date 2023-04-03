package com.example.byadiproject.Payments

import java.util.Date

data class Payment(val id:String,val payment:Double,val date:String){
    constructor():this("",0.0,"",)
}
