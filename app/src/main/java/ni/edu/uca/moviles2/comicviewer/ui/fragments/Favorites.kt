package ni.edu.uca.moviles2.comicviewer.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ni.edu.uca.moviles2.comicviewer.R
import ni.edu.uca.moviles2.comicviewer.adapters.ComicListAdapter
import ni.edu.uca.moviles2.comicviewer.viewmodel.ComicViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(recyclerView.adapter?.itemCount==0) {
            Snackbar.make(view, resources.getString(R.string.emptyList), Snackbar.LENGTH_LONG)
                .show()
        }
    }

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favoritos, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when(item.itemId) {
            R.id.action_delete -> {
                viewModel.deleteAll()
                Snackbar.make(requireView() ,resources.getString(R.string.deletedAll) , Snackbar.LENGTH_LONG).show()
                true
            }
            R.id.action_nav_home -> {
                Navigation.findNavController(requireView()).navigate(R.id.action_favorites_to_main_screen)
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        })
    }

}