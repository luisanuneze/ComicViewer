package ni.edu.uca.moviles2.comicviewer.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ni.edu.uca.moviles2.comicviewer.db.entity.ComicEntity
import ni.edu.uca.moviles2.comicviewer.repository.ComicRepository
/*
    El ViewModel sirve para proveer datos a la UI y mantenerse con cambios de configuración
    También se usa para compartir datos entre fragments

 */
@ExperimentalCoroutinesApi
class ComicViewModel
@ViewModelInject
    constructor (private val repository: ComicRepository) : ViewModel() {

    // Usar LiveData y caching lo que allComics regresa tienes muchos beneficios:
    // - Podemos poner un observdor en los datos(en lugar de pedir los cambios) y solamente actualizar la UI
    //   cuando los datos cambian.
    // - Repositorio es completamente separado de la UI por medio del ViewModel.
    val allComics: LiveData<List<ComicEntity>> = repository.allComics.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(comic: ComicEntity) = viewModelScope.launch {
        repository.insert(comic)
    }

    /**
     * Launching a new coroutine to delete the data in a non-blocking way
     */
    fun delete(comic: ComicEntity) = viewModelScope.launch {
        repository.delete(comic)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

}

class ComicViewModelFactory(private val repository: ComicRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicViewModel::class.java)) {
            return ComicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}