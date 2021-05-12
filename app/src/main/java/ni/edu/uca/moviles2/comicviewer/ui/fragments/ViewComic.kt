package ni.edu.uca.moviles2.comicviewer.ui.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ni.edu.uca.moviles2.comicviewer.R
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
class ViewComic : Fragment()  {

    private val viewModel: ComicViewModel by viewModels()
    private val  comicNetworkMapper: ComicNetworkMapper = ComicNetworkMapper()

    lateinit var comicFromNetwork : ComicNetworkEntity

    private lateinit var progressBar:ProgressBar
    private lateinit var ivComicView:ImageView
    private lateinit var view2View:View
    private lateinit var titleView:TextView
    private lateinit var comicCard:View

    private lateinit var errorMsgView:TextView

    private lateinit var numView:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout para este fragment
        val view = inflater.inflate(R.layout.fragment_view_comic, container, false)

        //los views en el layout
        ivComicView = view.findViewById<ImageView>(R.id.ivComic)
        titleView = view.findViewById<TextView>(R.id.comic_title)
        errorMsgView = view.findViewById<TextView>(R.id.errorMsg)
        numView = view.findViewById<TextView>(R.id.tvNumCont)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        view2View = view.findViewById<View>(R.id.view2)
        comicCard = view.findViewById<View>(R.id.comicCard)


        view.findViewById<FloatingActionButton>(R.id.fabOther).setOnClickListener { view ->
            //Pone la vista  cargando
            onlyShowProgressBar()
            //busca nuevo comic
            searchComic("/614/info.0.json")
        }

        view.findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener { view ->
            //Crea un nuevo ComicEntity usando el mapper
            val comicFavorite = this.comicNetworkMapper.mapFromEntity(comicFromNetwork)
            //Actualiza la fecha para insertar en la base de datos
            comicFavorite.dateAdded = Date().time
            //inserta en la base de datos
            viewModel.insert(comicFavorite)
            Snackbar.make(view,requireActivity().resources.getString(R.string.added) , Snackbar.LENGTH_LONG).show()
        }

        view.findViewById<FloatingActionButton>(R.id.fabList).setOnClickListener { view ->
            //Se mueve a la lista de favorit0s
            Navigation.findNavController(view).navigate(R.id.action_view_comic_to_favorites)
        }


        //Inicializar la vista con el progress bar hasta que carga el comic
        onlyShowProgressBar()

        //Inicializa el comic en blanco
        comicFromNetwork= ComicNetworkEntity(0,"","","","","","",0,0,0,0)


        //Busca el comic del día
        searchComic("info.0.json")
        return view
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() != null) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onPause() {
        super.onPause()
        if (requireActivity() != null) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://xkcd.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchComic(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ComicRetrofit::class.java).getCurrentComic("$query").execute()
            comicFromNetwork = call.body() as ComicNetworkEntity
            requireActivity().runOnUiThread {
                if (comicFromNetwork.title != "") {
                    showComic(comicFromNetwork)
                } else {
                    onlyShowError()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.comic_title).text = comicFromNetwork.title
    }

    private fun showComic(comic:ComicNetworkEntity) {
        titleView.text=comic.title
        numView.text=comic.num.toString()
        Picasso.get()
            .load(comic.img)
            .into(ivComicView)
        showResults()

    }

    private fun onlyShowProgressBar() {
        progressBar.visibility = View.VISIBLE
        ivComicView.visibility = View.INVISIBLE
        view2View.visibility = View.INVISIBLE
        titleView.visibility = View.INVISIBLE
        errorMsgView.visibility = View.INVISIBLE
        comicCard.visibility = View.INVISIBLE
    }

    private fun showResults() {
        progressBar.visibility = View.INVISIBLE
        ivComicView.visibility = View.VISIBLE
        view2View.visibility = View.VISIBLE
        titleView.visibility = View.VISIBLE
        errorMsgView.visibility = View.INVISIBLE
        comicCard.visibility = View.VISIBLE
    }

    private fun onlyShowError() {
        progressBar.visibility = View.INVISIBLE
        ivComicView.visibility = View.INVISIBLE
        view2View.visibility = View.INVISIBLE
        titleView.visibility = View.INVISIBLE
        errorMsgView.visibility = View.VISIBLE
        comicCard.visibility = View.INVISIBLE
        Toast.makeText(requireActivity(),requireActivity().resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

}