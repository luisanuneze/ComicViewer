package ni.edu.uca.moviles2.comicviewer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ni.edu.uca.moviles2.comicviewer.R
import ni.edu.uca.moviles2.comicviewer.adapters.ComicListAdapter
import ni.edu.uca.moviles2.comicviewer.db.entity.ComicEntity
import ni.edu.uca.moviles2.comicviewer.viewmodel.ComicViewModel
import java.util.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class Favorites : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ComicViewModel by viewModels()

    @Inject
    lateinit var comicsAdapter: ComicListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Layout para este fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.favorites_list).apply {
            setHasFixedSize(true)
            // especificar el viewAdapter
            adapter = comicsAdapter
        }

        // Agregar un observer del LiveData retornado por getComics.
        // El método onChanged() se ejecuta cuando la data observada cambia y la actividad
        // no está con enfoque
        viewModel.allComics.observe(viewLifecycleOwner) { comics ->
            // Actualizar la copia cached de los comics en el adapter.
            comics.let { this.comicsAdapter.submitList(it) }
        }
        return view
    }

}