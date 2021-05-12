package ni.edu.uca.moviles2.comicviewer.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ni.edu.uca.moviles2.comicviewer.db.dao.ComicDao
import ni.edu.uca.moviles2.comicviewer.db.entity.ComicEntity

/*
    Repositorio provee acceso a multiples fuentes de datos
    Declara el DAO como propiedad private en el constructor
 */
class ComicRepository(private val comicDao: ComicDao,

)  {

    // Room ejecuta todos los queries en hilos separados.
    // Flow will notifica al observer cuando los datos cambian.
    val allComics: Flow<List<ComicEntity>> = comicDao.getComics()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(comic: ComicEntity) {
        comicDao.insertComic(comic)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(comic: ComicEntity) {
        comicDao.deleteComic(comic)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        comicDao.deleteAllComics()
    }
}