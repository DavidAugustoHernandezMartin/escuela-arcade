package augusto.hernandez.escuelaarcade.coursegame.game

import android.os.Bundle
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
import com.google.android.material.snackbar.Snackbar

const val SCORE_INCREASE = 20
const val MAX_NO_OF_WORDS = 3
class GameFragment: Fragment() {
    private val viewModel:GameViewModel by activityViewModels()
    private val courseViewModel:CourseGameViewModel by activityViewModels()
    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding
    private var numero:Int = 0
    private val medium = (SCORE_INCREASE * MAX_NO_OF_WORDS)/2
    private var completado = false

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
        viewModel.results.apply {
            lastLesson = courseViewModel.leccionActual.value!!.nombre
            maxPoints = courseViewModel.curso.value!!.maxima_puntuacion
        }
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

    private fun exitGame(passed:Boolean) {
        if(passed){
            val newData = courseViewModel.createModifiedCourse(viewModel.results.lastLesson,
                                                viewModel.results.maxPoints,
                                                numero,
                                                courseViewModel.curso.value!!.progreso[numero])

            if (newData != null) {
                courseViewModel.curso.value = newData
                courseViewModel.update(newData)
            }else{
                Snackbar.make(binding.root,"No se han podido sincronizar datos de la lección actual:  ${courseViewModel.leccionActual}",Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.reinitializationData()
        setErrorTextField(false)
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

        if (viewModel.score.value!! >= medium) {
            completado = courseViewModel.curso.value!!.progreso[numero] == 0
            if(completado){
                // Se obtiene la lista de progreso y se transforma a mutable
                val progreso = courseViewModel.curso.value?.progreso?.toMutableList()

                // incrementa el valor en el índice especificado
                progreso?.let {
                    it[numero] = 1

                    // actualiza el valor de progreso en el objeto Curso
                    val cursoActualizado = courseViewModel.curso.value?.copy(progreso = it)
                    courseViewModel.curso.value = cursoActualizado
                }
            }
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.congratulations))
                .setMessage(getString(R.string.you_scored, viewModel.score.value ?: 0,medium))
                .setPositiveButton(getString(R.string.exit)) { _, _ ->
                    run {
                        exitGame(true)
                    }
                }
                .show()
        } else{
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.better_next_time))
                .setMessage(getString(R.string.you_scored, viewModel.score.value ?: 0,medium))
                .setPositiveButton(getString(R.string.exit)) { _, _ ->
                    run {
                        exitGame(false)
                    }
                }
                .show()
        }

    }
}