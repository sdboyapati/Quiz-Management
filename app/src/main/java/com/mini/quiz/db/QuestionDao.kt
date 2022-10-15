package com.mini.quiz.db

import androidx.room.*
import com.mini.quiz.models.QuestionRecord

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questionRecord: QuestionRecord)

    @Update
    suspend fun update(questionRecord: QuestionRecord)

    @Delete
    suspend fun delete(questionRecord: QuestionRecord)

    @Query("delete from Questions")
    suspend fun deleteAll()

    @Query("select * from Questions where id LIKE :id")
    suspend fun getRecord(id: Int): QuestionRecord

    @Query("select * from Questions")
    fun getAllRecords(): List<QuestionRecord>

    @Query("select name from sqlite_master where name = :gameCode")
    fun tableExists(gameCode: String): String?
}