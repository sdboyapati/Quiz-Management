package com.mini.quiz.repository

import com.mini.quiz.db.QuestionDao
import com.mini.quiz.models.QuestionRecord

class QuestionsRepository(private val questionsDao: QuestionDao) {

    suspend fun insert(questionRecord: QuestionRecord) = questionsDao.insert(questionRecord)

    suspend fun update(questionRecord: QuestionRecord) = questionsDao.update(questionRecord)

    suspend fun delete(questionRecord: QuestionRecord) = questionsDao.delete(questionRecord)

    suspend fun getRecord(id: Int) = questionsDao.getRecord(id)

    fun getAllRecords() = questionsDao.getAllRecords()

    fun tableExists(gameCode: String) = questionsDao.tableExists(gameCode)
    suspend fun deleteAll() {
        questionsDao.deleteAll()
    }

}