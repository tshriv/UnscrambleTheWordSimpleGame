package com.game.newUnscrambleApp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.game.newUnscrambleApp.Model.wordList

class MyViewModel : ViewModel() {

    val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    val _queNumbering = MutableLiveData(1)
    val queNumbering: LiveData<Int> get() = _queNumbering

    private var _currentWord: String = wordList.random()
    val currentWord get() = _currentWord

    private var _currentScrambledWord = MutableLiveData<String>(currentWord)
    val currentScrambledWord:LiveData<String> get() = _currentScrambledWord

    val alreadyDoneWordList: MutableList<String> = mutableListOf()

    init {
        selectAword()
        scrambleTheWord()
    }

    fun selectAword() {
        do {
            _currentWord = wordList.random().toString()
        } while (alreadyDoneWordList.contains(currentWord))
        alreadyDoneWordList.add(currentWord)
    }


    fun scrambleTheWord() {
        do {
            //  currentWord.toMutableList().shuffled().toString()
            //   _currentScrambledWord = currentWord.toCharArray().shuffle().toString()
            val tempWord = currentWord.toCharArray()
            tempWord.shuffle()
            _currentScrambledWord.value = String(tempWord)

        } while (currentWord == currentScrambledWord.value)
    }
    fun increaseScoreAndQuenum(){
        _score.value = _score.value?.plus(10)
        _queNumbering.value=_queNumbering.value?.inc()
    }
    fun setScoreQueNumToZero(){
        _score.value=0
        _queNumbering.value=0
    }


}