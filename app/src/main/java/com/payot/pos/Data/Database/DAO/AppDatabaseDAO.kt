package com.payot.pos.Data.Database.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.payot.pos.Data.Database.Machine

@Dao
interface AppDatabaseDAO {

    // 모든 장치목록 가져오기
    @Query("SELECT * FROM Machine")
    fun getMachines(): List<Machine>

    @Insert
    fun setMachine(machine: Machine)

    @Delete
    fun deleteMachine(machine: Machine)
}