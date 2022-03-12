package com.game.newUnscrambleApp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.game.newUnscrambleApp.R
import com.game.newUnscrambleApp.databinding.FragmentGameBinding
import com.game.newUnscrambleApp.viewModel.MyViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment : Fragment() {

    lateinit var binding: FragmentGameBinding
    val myViewModel: MyViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= DataBindingUtil.inflate(inflater, R.layout.fragment_game,container,false)
        // binding = FragmentGameBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel=myViewModel

       // updateUi()

        binding.lifecycleOwner=viewLifecycleOwner
        binding.submit.setOnClickListener {
            val userInputText = binding.editText.text.toString()
            binding.editText.text?.clear()
            if (userInputText == myViewModel.currentWord) {
                binding.textInputLayout.isErrorEnabled=false
                myViewModel.increaseScoreAndQuenum()
                nextScrambledWord()
               // updateUi()
                if (myViewModel.queNumbering.value == 11) {
                    showDialogOnCompetionOfGame()
                }
            } else {
                binding.textInputLayout.isErrorEnabled = true
                binding.textInputLayout.error = "Try Again"
            }
        }
        binding.skip.setOnClickListener {
            nextScrambledWord()
            binding.editText.text?.clear()
            binding.textInputLayout.isErrorEnabled=false
            // livedata used-  binding.scrambleText.text = myViewModel.currentScrambledWord.value
        }

     /*   //observers
        myViewModel.score.observe(
            viewLifecycleOwner,
            { newScore -> binding.score.text = newScore.toString() })
        myViewModel.currentScrambledWord.observe(
            viewLifecycleOwner,
            { newCurScWord -> binding.scrambleText.text = newCurScWord })
        myViewModel.queNumbering.observe(
            viewLifecycleOwner,
            { newVal -> binding.queNumber.text = newVal.toString() })

      */
    }

    private fun showDialogOnCompetionOfGame() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Game Complete")
            .setMessage("Your score is " + myViewModel.score)
            .setNegativeButton("Exit") { _, _ -> activity?.finish() }
            .setPositiveButton("Restart") { _, _ -> restartGame() }
    }

    private fun nextScrambledWord() {
        myViewModel.selectAword()
        myViewModel.scrambleTheWord()

    }

    private fun initializeGame() {

        myViewModel.selectAword()
        myViewModel.scrambleTheWord()
       // updateUi()
    }

    private fun restartGame() {
        myViewModel.setScoreQueNumToZero()
        myViewModel.alreadyDoneWordList.clear()
        initializeGame()
    }


    /* private fun updateUi() {
         binding.score.text = myViewModel.score.toString()
         binding.queNumber.text = myViewModel.queNumbering.toString()
         binding.scrambleText.text = myViewModel.currentScrambledWord

     }*/
}