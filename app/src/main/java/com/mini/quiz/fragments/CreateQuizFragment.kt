package com.mini.quiz.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mini.quiz.R
import com.mini.quiz.databinding.FragmentCreateQuizBinding
import com.mini.quiz.models.QuestionRecord

private const val TAG = "MainActivity"

class CreateQuizFragment : Fragment() {
    private val db by lazy {
        Firebase.firestore
    }
    private lateinit var binding: FragmentCreateQuizBinding
    lateinit var allQuestions: MutableList<QuestionRecord>
    private var record = QuestionRecord("", "", "", "", "")
    var currentIndex = 0
    var strCurrentIndex = (currentIndex + 1).toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        allQuestions = mutableListOf(record)
        //allQuestions.add(record)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_quiz, container, false)
        binding.dataPart = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnUploadQuiz.setOnClickListener {
            binding.tvUploading.visibility = View.VISIBLE
            val gameCode = binding.etvUploadGameCode.text.toString()

            val data = hashMapOf("code" to gameCode)

            //assume it to be true at first
            var validCode = true

            db.collection("GameCodes").get().addOnSuccessListener {
                //for all documents in "GameCodes" collection
                for (i in it) {
                    Log.i(TAG, "${i["code"]}")
                    //To make sure that the gameCode is not existed already
                    if (gameCode == i["code"]) {
                        validCode = false
                        break
                    }
                }
                val displayString: String
                if (validCode) {
                    displayString = "Created Successfully"
                    db.collection("GameCodes").add(data)
                    for (i in allQuestions)
                        db.collection(gameCode).add(i)
                    view.findNavController().popBackStack()
                } else
                    displayString = "Code Already exists"
                Toast.makeText(
                    activity?.applicationContext,
                    displayString,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.etvQuestion.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.i("MainActivity", "before Q -> " + s.toString())
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    allQuestions[currentIndex].Q = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    Log.i("MainActivity", "after Q -> " + s.toString())
                }
            }
        )

        binding.etvOption1.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.i("MainActivity", "before op1 -> " + s.toString())
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    allQuestions[currentIndex].op1 = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    Log.i("MainActivity", "after op1 -> " + s.toString())
                }
            }
        )

        binding.etvOption2.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.i("MainActivity", "before op2 -> " + s.toString())
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    allQuestions[currentIndex].op2 = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    Log.i("MainActivity", "after op2 -> " + s.toString())
                }
            }
        )

        binding.etvOption3.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.i("MainActivity", "before op3 -> " + s.toString())
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    allQuestions[currentIndex].op3 = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    Log.i("MainActivity", "after op3 -> " + s.toString())
                }
            }
        )

        binding.etvOption4.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    Log.i("MainActivity", "before op4 -> " + s.toString())
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.i("MainActivity", "on op4 -> " + s.toString())
                    allQuestions[currentIndex].op4 = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                    Log.i("MainActivity", "after op4 -> " + s.toString())
                }
            }
        )

        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.create, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.item_add_new_question -> {
                            record = QuestionRecord("", "", "", "", "")
                            allQuestions.add(++currentIndex, record)
                            strCurrentIndex = (currentIndex + 1).toString()
                            binding.invalidateAll()
                        }
                        R.id.item_delete_current_question -> {
                            allQuestions.removeAt(currentIndex)
                            if (allQuestions.size != 0) {
                                if (currentIndex != 0)
                                    --currentIndex
                                strCurrentIndex = (currentIndex + 1).toString()
                                binding.invalidateAll()
                            } else {
                                //navigate back to main screen
                                view.findNavController().popBackStack()
                            }
                        }
                    }
                    return true
                }
            },viewLifecycleOwner, Lifecycle.State.RESUMED
        )

        binding.btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                --currentIndex
                strCurrentIndex = (currentIndex + 1).toString()
                binding.invalidateAll()
            }
        }
        binding.btnNext.setOnClickListener {
            if (currentIndex < allQuestions.size - 1) {
                ++currentIndex
                strCurrentIndex = (currentIndex + 1).toString()
                binding.invalidateAll()
            }
        }
    }

}