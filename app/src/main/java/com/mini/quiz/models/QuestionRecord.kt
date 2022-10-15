package com.mini.quiz.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Questions")
data class QuestionRecord(
    var Q : String,
    var op1 : String,
    var op2 : String,
    var op3 : String,
    var op4 : String,
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
