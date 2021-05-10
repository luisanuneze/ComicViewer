package ni.edu.uca.moviles2.comicviewer.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ni.edu.uca.moviles2.comicviewer.db.entity.ComicEntity

/*
    Objeto de acceso a datos DAO (Data Access Object)
    Se especifican las consultas SQLy se asocia con la llamada a métodos
    Se usan anotaciones como "@Insert" para generar consultas sin especificar SQL
    DAO debe ser una interface o una clase abstracta
    Para observar cambios en los datos se usa Flow de las kotlinx-coroutines. Al agregar Flow al método Room genera el código necesario
    para actualizar el Flow cuando la base de datos es actualizada

 */
@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertComic(comicEntity : ComicEntity)

    @Delete()
    suspend fun deleteComic(comicEntity : ComicEntity)

    @Query("SELECT * FROM comics ORDER BY date_added DESC")
    fun getComics(): Flow<List<ComicEntity>>

    @Query("DELETE FROM comics")
    suspend fun deleteAllComics()
}