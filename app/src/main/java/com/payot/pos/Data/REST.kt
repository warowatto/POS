package com.payot.pos.Data

data class User(val id: Int, val companyNumber: String, val name: String, val ownerName:String, val email: String)

data class Machine(val mac: String, val name: String, val type: String, val info: String)