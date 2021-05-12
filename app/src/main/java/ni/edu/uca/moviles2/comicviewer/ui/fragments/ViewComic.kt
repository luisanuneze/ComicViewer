package ni.edu.uca.moviles2.comicviewer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ni.edu.uca.moviles2.comicviewer.R
import ni.edu.uca.moviles2.comicviewer.databinding.FragmentViewComicBinding
import ni.edu.uca.moviles2.comicviewer.retrofit.ComicNetworkEntity
import ni.edu.uca.moviles2.comicviewer.retrofit.ComicNetworkMapper
import ni.edu.uca.moviles2.comicviewer.retrofit.ComicRetrofit
import ni.edu.uca.moviles2.comicviewer.viewmodel.ComicViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


//Fragment para presentar ver comic
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ViewComic : Fragment(R.layout.fragment_view_comic) {

    private val viewModel: ComicViewModel by viewModels()
    private val comicNetworkMapper: ComicNetworkMapper = ComicNetworkMapper()


    private lateinit var comicFromNetwork: ComicNetworkEntity
    private var ultNum: Int = 0

    private var _binding: FragmentViewComicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewComicBinding.inflate(inflater, container, false)


        binding.comicCard.fabOther.setOnClickListener {
            //Pone la vista  cargando
            onlyShowProgressBar()
            //busca nuevo comic generando aleatorio entre 1 y el ultimo
            var query = rand(ultNum).toString() + "/info.0.json"
            searchComic(query)
        }


        binding.comicCard.fabAdd.setOnClickListener {
            //Crea un nuevo ComicEntity usando el mapper
            val comicFavorite = this.comicNetworkMapper.mapFromEntity(comicFromNetwork)
            //Actualiza el id y la fecha para insertar en la base de datos
            comicFavorite.id = comicFavorite.num
            comicFavorite.dateAdded = Date().time
            //inserta en la base de datos
            viewModel.insert(comicFavorite)
            view?.let { it -> Snackbar.make(it,requireActivity().resources.getString(R.string.added) , Snackbar.LENGTH_LONG).show() }
        }

        binding.comicCard.fabList.setOnClickListener {
            //Se mueve a la lista de favorit0s
            view?.let { it -> Navigation.findNavController(it).navigate(R.id.action_view_comic_to_favorites) }
        }

        //Inicializar la vista con el progress bar hasta que carga el comic
        onlyShowProgressBar()

        //Inicializa el comic en blanco
        comicFromNetwork= ComicNetworkEntity("","","","","","",0,0,0,0)


        //Busca el comic del día
        searchComic("info.0.json")

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Retrofit Builder
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://xkcd.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchComic(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ComicRetrofit::class.java).getCurrentComic(query).execute()
            comicFromNetwork = call.body() as ComicNetworkEntity
            requireActivity().runOnUiThread {
                if (comicFromNetwork.title != "") {
                    showComic(comicFromNetwork)
                    if(query == "info.0.json"){
                        ultNum=comicFromNetwork.num
                    }
                } else {
                    onlyShowError()
                }
            }
        }
    }

    private fun onlyShowProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.ivComic.visibility = View.INVISIBLE
        binding.view2.visibility = View.INVISIBLE
        binding.comicTitle.visibility = View.INVISIBLE
        binding.errorMsg.visibility = View.INVISIBLE
        binding.root.findViewById<View>(R.id.comicCard).visibility = View.INVISIBLE
    }

    private fun showComic(comic:ComicNetworkEntity) {
        binding.comicTitle.text=comic.title
        binding.comicCard.tvNumCont .text=comic.num.toString()
        binding.comicCard.tvAltCont.text= comic.alt
        binding.comicCard.tvDiaCont.text=comic.day.toString()
        binding.comicCard.tvMesCont.text= comic.month.toString()
        binding.comicCard.tvAnioCont.text=comic.year.toString()
        Picasso.get()
            .load(comic.img)
            .into(binding.ivComic)
        showResults()

    }

    private fun showResults() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.ivComic.visibility = View.VISIBLE
        binding.view2.visibility = View.VISIBLE
        binding.comicTitle.visibility = View.VISIBLE
        binding.errorMsg.visibility = View.INVISIBLE
        binding.root.findViewById<View>(R.id.comicCard).visibility = View.VISIBLE
    }

    private fun onlyShowError() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.ivComic.visibility = View.INVISIBLE
        binding.view2.visibility = View.INVISIBLE
        binding.comicTitle.visibility = View.INVISIBLE
        binding.errorMsg.visibility = View.VISIBLE
        binding.root.findViewById<View>(R.id.comicCard).visibility = View.INVISIBLE
        Toast.makeText(requireActivity(),requireActivity().resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
    }


    private fun rand(end: Int): Int {
        require(1 <= end) { "Illegal Argument" }
        return (1..end).random()
    }

}