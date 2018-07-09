package com.payot.pos.Data

data class User(val id: Int, val name: String, val email: String)

data class Machine(val mac: String, val name: String, val type: String, val info: String)