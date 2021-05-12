package ni.edu.uca.moviles2.comicviewer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ni.edu.uca.moviles2.comicviewer.R
import ni.edu.uca.moviles2.comicviewer.databinding.FragmentViewComicLocalBinding
import ni.edu.uca.moviles2.comicviewer.db.entity.ComicEntity

//Fragment para presentar la página de Wikipedia con la información de XKCD
class ViewComicLocal : Fragment(R.layout.fragment_view_comic_local)  {

    private lateinit var comicEntity : ComicEntity

    private var _binding: FragmentViewComicLocalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewComicLocalBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}