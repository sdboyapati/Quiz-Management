package com.mini.quiz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.mini.quiz.R

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.btn_create).setOnClickListener {
            it.findNavController().navigate(StartFragmentDirections.actionStartFragmentToCreateQuizFragment())
        }
        view.findViewById<Button>(R.id.btn_join).setOnClickListener {
            it.findNavController().navigate(StartFragmentDirections.actionStartFragmentToJoinQuizFragment())
        }
    }
}