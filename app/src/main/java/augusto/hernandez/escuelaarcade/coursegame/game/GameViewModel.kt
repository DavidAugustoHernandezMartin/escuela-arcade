package augusto.hernandez.escuelaarcade.coursegame.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import augusto.hernandez.escuelaarcade.model.data.Results

const val PREGUNTAS = "preguntas"
const val RESPUESTAS = "respuestas"
class GameViewModel:ViewModel(){


    private val _score: MutableLiveData<Long> = MutableLiveData(0)
    private val _currentWordCount:MutableLiveData<Int> = MutableLiveData(0)
    private val _currentScrambledWord:MutableLiveData<String> = MutableLiveData("")
    private val _currentHint:MutableLiveData<String> = MutableLiveData("")
    private var wordslist:MutableList<String> = mutableListOf()
    private var currentWord: String = ""
    var results:Results = Results("",0L)

    private val _game:MutableLiveData<HashMap<String,List<String>>> = MutableLiveData()
    fun setGame(map: HashMap<String,List<String>>){
        _game.value = map
        getNextWord()
    }

    val score: LiveData<Long> get() = _score
    val currentWordCount: LiveData<Int> get() = _currentWordCount
    val currentScrambledWord: LiveData<String> get() = _currentScrambledWord
    val currentHint:LiveData<String> get() = _currentHint




    private fun getNextWord(){
        val tempWord = getWord()
        currentWord = String(tempWord)
        wordslist.add(addWord(tempWord))
    }

    private fun getWord():CharArray{
        currentWord = _game.value?.get(RESPUESTAS)?.get(currentWordCount.value!!) ?: ""
        _currentHint.value = _game.value?.get(PREGUNTAS)?.get(currentWordCount.value!!) ?: ""
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
            results.apply {
                if(_score.value!! > maxPoints)
                    maxPoints = _score.value!!
            }
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