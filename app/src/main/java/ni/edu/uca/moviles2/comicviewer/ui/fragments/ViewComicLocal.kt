package ni.edu.uca.moviles2.comicviewer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ni.edu.uca.moviles2.comicviewer.R
import ni.edu.uca.moviles2.comicviewer.databinding.FragmentViewComicLocalBinding
import ni.edu.uca.moviles2.comicviewer.db.entity.ComicEntity
import ni.edu.uca.moviles2.comicviewer.viewmodel.ComicViewModel

//Fragment para presentar la página de Wikipedia con la información de XKCD
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ViewComicLocal : Fragment(R.layout.fragment_view_comic_local)  {

    private val viewModel: ComicViewModel by viewModels()

    private lateinit var comicEntity : ComicEntity

    private var _binding: FragmentViewComicLocalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewComicLocalBinding.inflate(inflater, container, false)
        comicEntity = arguments?.getParcelable<ComicEntity>("comic")?: ComicEntity(0,"","","","","","",0,0,0,0,0)

        binding.comicCard.fabOther.visibility = View.INVISIBLE
        binding.comicCard.fabAdd.setImageDrawable(resources.getDrawable(android.R.drawable.ic_menu_delete))
        binding.comicCard.fabList.setImageDrawable(resources.getDrawable(android.R.drawable.ic_media_rew))
        binding.comicTitle.text=comicEntity.title
        binding.comicCard.tvNumCont .text=comicEntity.num.toString()
        binding.comicCard.tvAltCont.text= comicEntity.alt
        binding.comicCard.tvDiaCont.text=comicEntity.day.toString()
        binding.comicCard.tvMesCont.text= comicEntity.month.toString()
        binding.comicCard.tvAnioCont.text=comicEntity.year.toString()
        Picasso.get()
            .load(comicEntity.img)
            .into(binding.ivComic)

        binding.comicCard.fabAdd.setOnClickListener {
            viewModel.delete(comicEntity)
            Snackbar.make(it,resources.getString(R.string.deleted) , Snackbar.LENGTH_LONG).show()
            Navigation.findNavController(it).navigate(R.id.action_view_comic_local_to_main_screen)
        }

        binding.comicCard.fabList.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_view_comic_local_to_main_screen)
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}