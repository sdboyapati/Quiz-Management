package com.mini.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mini.quiz.R
import com.mini.quiz.databinding.FragmentQuizBinding
import com.mini.quiz.models.QuestionRecord
import com.mini.quiz.view_model.MyViewModel



class QuizFragment : Fragment() {

    lateinit var currentQuestion: String
    lateinit var currentOptions: MutableList<String>

    private lateinit var currentRecord: QuestionRecord
    private var currentIndex = 0

    private lateinit var binding: FragmentQuizBinding

    private var score = 0
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MyViewModel::class.java]
    }

    private lateinit var allRecords: List<QuestionRecord>

    private var totalQuestions = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        binding.quiz = this

        allRecords = viewModel.allQuestionRecord
        totalQuestions = allRecords.size
        currentRecord = allRecords[currentIndex]
        updateQuestion()


        binding.btnSubmit.setOnClickListener {
            updateScore()
            binding.rgOptions.clearCheck()
            ++currentIndex
            if (currentIndex < totalQuestions)
                currentRecord = allRecords[currentIndex]
            else {
                it.findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizOverFragment(score,allRecords.size))
            }
            updateQuestion()
        }
        return binding.root
    }



    private fun updateQuestion() {
        currentQuestion = currentRecord.Q
        currentOptions = mutableListOf(
            currentRecord.op1,
            currentRecord.op2,
            currentRecord.op3,
            currentRecord.op4
        )
        currentOptions.shuffle()
        binding.invalidateAll()
    }

    private fun updateScore() {
        val checkedId = binding.rgOptions.checkedRadioButtonId
        if (checkedId != -1) {
            val answerIndex = when (checkedId) {
                R.id.tv_option1 -> 0
                R.id.tv_option2 -> 1
                R.id.tv_option3 -> 2
                else -> 3
            }
            if (currentOptions[answerIndex] == currentRecord.op1) {
                ++score
            }
        }
    }
}