package com.mini.quiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mini.quiz.R

class QuizOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_over, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = QuizOverFragmentArgs.fromBundle(requireArguments())
        val score = "Score: ${args.score}/${args.totalQuestions}\n\nCompleted\n\n :)"
        view.findViewById<TextView>(R.id.tv_score).text = score
    }
}