package augusto.hernandez.escuelaarcade.coursegame.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import augusto.hernandez.escuelaarcade.R
import augusto.hernandez.escuelaarcade.coursegame.course.CourseListFragment
import augusto.hernandez.escuelaarcade.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameFragment: Fragment() {
    private val viewModel:GameViewModel by viewModels()

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment

    private var numero:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            numero = it.getInt(CourseListFragment.NUMERO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        //Esta vez con databinding en vez de viewbinding
        binding = GameFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        //Inicialización de variables de diseño (databinding)
        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS
    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    */
    private fun onSubmitWord() {
        val playerWord:String = binding.textInputEditText.text.toString().trim()
        if(viewModel.isUserWordCorrect(playerWord)){
            setErrorTextField(false)
            if (!viewModel.nextWord()) showFinalScoreDialog()
        } else {
            setErrorTextField(true)
        }
    }

    /*
     * Skips the current word without changing the score.
     * Increases the word count.
     */
    private fun onSkipWord() {
        if(viewModel.nextWord()){
            setErrorTextField(false)
        }else{
            showFinalScoreDialog()
        }
    }

    /*
     * Gets a random word from the list of words and shuffles the letters in it.
     */
    /* sustituido por ViewModel
    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()
        return String(tempWord)
    }*/

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reinitializationData()
        setErrorTextField(false)
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        findNavController().navigateUp()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /*override fun onDetach() {
        super.onDetach()
        Log.d("GameFragment","GameFragment destroyed!")
    }*/

    private fun showFinalScoreDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored,viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)){
                    _,_ -> exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)){
                    _,_ -> restartGame()
            }
            .show()
    }
}