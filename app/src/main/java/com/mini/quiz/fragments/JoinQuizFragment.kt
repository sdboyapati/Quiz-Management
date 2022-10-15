package com.mini.quiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mini.quiz.R
import com.mini.quiz.models.QuestionRecord
import com.mini.quiz.view_model.MyViewModel


class JoinQuizFragment : Fragment() {
    private val db by lazy {
        Firebase.firestore
    }
    private var validGameCode = false
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MyViewModel::class.java]
    }
    private lateinit var gameCode: String
    private lateinit var buttonStart: Button
    private lateinit var joiningTextView : TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val gameCodeEditText: EditText = view.findViewById(R.id.etv_gameCode)
        buttonStart = view.findViewById(R.id.btn_start)
joiningTextView = view.findViewById(R.id.tv_joining)
        buttonStart.setOnClickListener { it_btn ->
            it_btn.isClickable = false
            gameCode = gameCodeEditText.text.toString()

            //To check whether code is valid or not
            db.collection("GameCodes").get().addOnSuccessListener { it ->
                for (doc in it) {
                    if (doc["code"] == gameCode) {
                        validGameCode = true
                        break
                    }
                }

                if (validGameCode) {
                    joiningTextView.visibility = View.VISIBLE
                    viewModel.deleteAll()
                    // Gets all the collection and adds to local database.
                    val questionsCollection = db.collection(gameCode)
                    questionsCollection.get()
                        .addOnSuccessListener {
                            for (doc in it) {
                                val record = QuestionRecord(
                                    "${doc["q"]}",
                                    "${doc["op1"]}",
                                    "${doc["op2"]}",
                                    "${doc["op3"]}",
                                    "${doc["op4"]}"
                                )
                                viewModel.insert(record)
                            }
                            it_btn.findNavController()
                                .navigate(R.id.action_joinQuizFragment_to_quizFragment)
                        }
                        .addOnFailureListener {
                        }
                } else {
                    //Log.i(TAG, "Invalid")
                }
                it_btn.isClickable = true
            }
        }
    }
}