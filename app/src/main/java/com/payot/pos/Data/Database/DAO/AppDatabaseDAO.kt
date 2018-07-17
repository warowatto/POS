package com.payot.pos.Data.Database.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.payot.pos.Data.Database.Machine
import com.payot.pos.Data.Database.Product

@Dao
interface AppDatabaseDAO {

    // 모든 장치목록 가져오기
    @Query("SELECT * FROM Machine")
    fun getMachines(): List<Machine>

    @Query("SELECT * FROM machine WHERE id  = :id LIMIT 1")
    fun getMachinie(id: Int): Machine

    @Query("SELECT * FROM product WHERE machineId = :id")
    fun getProducts(id: Int): List<Product>

    @Query("SELECT * FROM product")
    fun allProducts(): List<Product>

    @Insert
    fun setMachine(machine: Machine)

    @Insert
    fun setProduct(product: Product)

    @Delete
    fun deleteMachine(machine: Machine)
}