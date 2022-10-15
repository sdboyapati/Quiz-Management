package com.mini.quiz.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mini.quiz.models.QuestionRecord

@Database(entities = [QuestionRecord::class], version = 1, exportSchema = false)
abstract class QuestionsDatabase : RoomDatabase(){

    abstract fun getQuestionsDao(): QuestionDao

    companion object{
        private var INSTANCE: QuestionsDatabase?=null

        fun getDatabase(context: Context): QuestionsDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionsDatabase::class.java,
                    "quiz_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}