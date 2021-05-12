package ni.edu.uca.moviles2.comicviewer.ui.fragments

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
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ni.edu.uca.moviles2.comicviewer.R
import ni.edu.uca.moviles2.comicviewer.retrofit.ComicNetworkEntity
import ni.edu.uca.moviles2.comicviewer.retrofit.ComicRetrofit
import ni.edu.uca.moviles2.comicviewer.viewmodel.ComicViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Fragment para presentar ver comic
class ViewComic : Fragment()  {

    private val viewModel: ComicViewModel by viewModels()

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
        ivComicView = view.findViewById<ImageView>(R.id.ivComic)
        titleView = view.findViewById<TextView>(R.id.comic_title)
        errorMsgView = view.findViewById<TextView>(R.id.errorMsg)
        numView = view.findViewById<TextView>(R.id.tvNumCont)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        view2View = view.findViewById<View>(R.id.view2)
        comicCard = view.findViewById<View>(R.id.comicCard)
        onlyShowProgressBar()
        comicFromNetwork= ComicNetworkEntity(0,"","","","","","",0,0,0,0)
        searchComic("2459/info.0.json")
        return view
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

    private fun showErrorDialog() {
        Toast.makeText(requireActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()

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