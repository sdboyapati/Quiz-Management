package com.mini.quiz.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mini.quiz.repository.QuestionsRepository
import com.mini.quiz.db.QuestionsDatabase
import com.mini.quiz.models.QuestionRecord
import kotlinx.coroutines.launch

class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QuestionsRepository
    var allQuestionRecord: List<QuestionRecord>

    init {
        val db = QuestionsDatabase.getDatabase(application)
        repository = QuestionsRepository(db.getQuestionsDao())
        allQuestionRecord = repository.getAllRecords()
    }
fun deleteAll() = viewModelScope.launch {
    repository.deleteAll()
}
    fun insert(record: QuestionRecord) = viewModelScope.launch {
        repository.insert(record)
    }

    fun delete(record: QuestionRecord) = viewModelScope.launch {
        repository.delete(record)
    }

    fun update(record: QuestionRecord) = viewModelScope.launch {
        repository.update(record)
    }

    fun getRecord(id: Int) = viewModelScope.launch {
        repository.getRecord(id)
    }

    fun tableExists(gameCode: String) = repository.tableExists(gameCode)
}