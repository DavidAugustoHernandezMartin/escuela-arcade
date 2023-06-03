package augusto.hernandez.escuelaarcade.coursegame.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val PREGUNTAS = "preguntas"
const val RESPUESTAS = "respuestas"
class GameViewModel:ViewModel(){


    private val _score: MutableLiveData<Int> = MutableLiveData(0)
    private val _currentWordCount:MutableLiveData<Int> = MutableLiveData(0)
    private val _currentScrambledWord:MutableLiveData<String> = MutableLiveData("")
    private val _currentHint:MutableLiveData<String> = MutableLiveData("")
    private var wordslist:MutableList<String> = mutableListOf()
    private var currentWord: String = ""

    private var game:MutableLiveData<HashMap<String,List<String>>> = MutableLiveData()
    fun setGame(map: HashMap<String,List<String>>){
        game.value = map
        getNextWord()
    }

    val score: LiveData<Int> get() = _score
    val currentWordCount: LiveData<Int> get() = _currentWordCount
    val currentScrambledWord: LiveData<String> get() = _currentScrambledWord
    val currentHint:LiveData<String> get() = _currentHint




    private fun getNextWord(){
        val tempWord = getWord()
        currentWord = String(tempWord)
        wordslist.add(addWord(tempWord))
    }

    private fun getWord():CharArray{
        currentWord = game.value?.get(RESPUESTAS)?.get(currentWordCount.value!!) ?: ""
        _currentHint.value = game.value?.get(PREGUNTAS)?.get(currentWordCount.value!!) ?: ""
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