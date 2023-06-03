package augusto.hernandez.escuelaarcade.coursegame.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel(){


    private val _score: MutableLiveData<Int> = MutableLiveData(0)
    private val _currentWordCount:MutableLiveData<Int> = MutableLiveData(0)
    private val _currentScrambledWord:MutableLiveData<String> = MutableLiveData("")
    private var wordslist:MutableList<String>
    private var currentWord: String


    val score: LiveData<Int> get() = _score
    val currentWordCount: LiveData<Int> get() = _currentWordCount
    val currentScrambledWord: LiveData<String> get() = _currentScrambledWord

    init {
        _score.value =0
        _currentWordCount.value = 0
        _currentScrambledWord.value = addWord(getWord())
        wordslist = mutableListOf()
        currentWord = getWord().toString()
    }


    /*override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment","GameView destroyed!")
    }*/

    private fun getNextWord(){
        val tempWord = getWord()
        currentWord = String(tempWord)
        if (wordslist.contains(currentWord)){
            getNextWord()
        }else{
            wordslist.add(addWord(tempWord))
        }
    }

    private fun getWord():CharArray{
        currentWord = allWordsList.random()
        return currentWord.toCharArray()
    }

    private fun addWord(tempWord:CharArray):String{
        while (String(tempWord).equals(currentWord,false)) tempWord.shuffle()
        _currentScrambledWord.value = String(tempWord)
        _currentWordCount.value = _currentWordCount.value!!.inc()
        return currentWord
    }

    private fun increaseScore(){
        _score.value= _score.value?.plus(SCORE_INCREASE)
    }

    fun isUserWordCorrect(playerWord:String):Boolean{
        if (playerWord.equals(currentWord,true)){
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializationData(){
        _score.value = 0
        _currentWordCount.value = 0
        wordslist.clear()
        wordslist.add(addWord(getWord()))
    }

    fun nextWord():Boolean{
        return if (currentWordCount.value!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else{
            false
        }
    }
}