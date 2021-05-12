package ni.edu.uca.moviles2.comicviewer.utils

import ni.edu.uca.moviles2.comicviewer.retrofit.ComicNetworkEntity
import java.lang.Exception

sealed class DataState {
    object Idle: DataState()
    data class Success(val comic: ComicNetworkEntity) : DataState()
    data class Error(val exception: Exception) : DataState()
    object Loading: DataState()
}