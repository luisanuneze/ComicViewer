package ni.edu.uca.moviles2.comicviewer.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
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
    constructor (private val repository: ComicRepository
) : ViewModel() {


    // Usar LiveData y caching lo que allComics regresa tienes muchos beneficios:
    // - Podemos poner un observador en los datos(en lugar de pedir los cambios) y solamente actualizar la UI
    //   cuando los datos cambian.
    // - Repositorio es completamente separado de la UI por medio del ViewModel.
    val allComics: LiveData<List<ComicEntity>> = repository.allComics.asLiveData()

    /**
     * Incia una nueva coroutine para insertar data
     */
    fun insert(comic: ComicEntity) = viewModelScope.launch {
        repository.insert(comic)
    }

    /**
     * Incia una nueva coroutine para borrar d
     */
    fun delete(comic: ComicEntity) = viewModelScope.launch {
        repository.delete(comic)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

}

class ComicViewModelFactory(private val repository: ComicRepository) : ViewModelProvider.Factory {
    @ExperimentalCoroutinesApi
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ComicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}