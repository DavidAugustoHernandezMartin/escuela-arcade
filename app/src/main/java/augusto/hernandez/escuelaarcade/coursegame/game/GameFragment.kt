package augusto.hernandez.escuelaarcade.coursegame.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import augusto.hernandez.escuelaarcade.R
import augusto.hernandez.escuelaarcade.coursegame.CourseGameViewModel
import augusto.hernandez.escuelaarcade.coursegame.course.CourseFragment
import augusto.hernandez.escuelaarcade.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
const val SCORE_INCREASE = 20
const val MAX_NO_OF_WORDS = 3
class GameFragment: Fragment() {
    private val viewModel:GameViewModel by activityViewModels()
    private val courseViewModel:CourseGameViewModel by activityViewModels()
    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding
    private var numero:Int = 0
    private val medium = (SCORE_INCREASE * MAX_NO_OF_WORDS)/2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            numero = it.getInt(CourseFragment.NUMERO)
        }
        binding = GameFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Inicialización de variables de diseño (databinding)

        binding.apply {
            gameViewModel = viewModel
            maxNoOfWords = MAX_NO_OF_WORDS
            appBinding = this@GameFragment
        }
        binding.gameViewModel!!.currentScrambledWord.observe(viewLifecycleOwner){ newWord ->
            binding.textViewUnscrambledWord.text = newWord ?: "_"
        }
        binding.gameViewModel!!.currentWordCount.observe(viewLifecycleOwner){ newWordCount ->
            binding.wordCount.text = newWordCount.toString()
        }
        binding.gameViewModel!!.score.observe(viewLifecycleOwner){ newScore ->
            binding.score.text = newScore.toString()
        }
        binding.gameViewModel!!.currentHint.observe(viewLifecycleOwner){ instructions ->
            binding.textViewInstructions.text = instructions.toString()
        }
        viewModel.setGame(courseViewModel.leccionActual.value!!.juego)

    }

     fun onSubmitWord() {
        val playerWord:String = binding.textInputEditText.text.toString().trim()
        if(viewModel.isUserWordCorrect(playerWord)){
            setErrorTextField(false)
            if (!viewModel.nextWord()) showFinalScoreDialog()
        } else {
            setErrorTextField(true)
        }
    }

     fun onSkipWord() {
        if(viewModel.nextWord()){
            setErrorTextField(false)
        }else{
            showFinalScoreDialog()
        }
    }

    private fun restartGame() {
        viewModel.reinitializationData()
        setErrorTextField(false)
    }

    private fun exitGame() {
        //llamar a una función de actualización
        findNavController().navigate(R.id.action_gameFragment_to_courseListFragment)
    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }



    private fun showFinalScoreDialog() {

        if (viewModel.score.value!! < medium) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.congratulations))
                .setMessage(getString(R.string.you_scored, viewModel.score.value ?: 0,medium))
                .setPositiveButton(getString(R.string.exit)) { _, _ ->
                    run {
                        exitGame()
                    }
                }
                .show()
        } else{
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.better_next_time))
                .setMessage(getString(R.string.you_scored, viewModel.score.value ?: 0,medium))
                .setPositiveButton(getString(R.string.exit)) { _, _ ->
                    run {
                        exitGame()
                    }
                }
                .show()
        }

    }
}