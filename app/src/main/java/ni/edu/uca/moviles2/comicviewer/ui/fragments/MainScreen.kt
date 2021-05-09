package ni.edu.uca.moviles2.comicviewer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import ni.edu.uca.moviles2.comicviewer.R

/**
 * Muestra la pantalla principal de la aplicación.
 */
class MainScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)


        view.findViewById<Button>(R.id.favs_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_main_screen_to_favorites)
        }

        view.findViewById<Button>(R.id.info_btn).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_main_screen_to_info)
        }

        return view
    }
}