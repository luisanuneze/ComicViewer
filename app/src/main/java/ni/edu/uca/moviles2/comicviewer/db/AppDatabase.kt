package ni.edu.uca.moviles2.comicviewer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ni.edu.uca.moviles2.comicviewer.db.dao.ComicDao
import ni.edu.uca.moviles2.comicviewer.db.entity.ComicEntity

/*
    Se agrega una base de datos de Room
    La clase debe se ser abstracta y extender RoomDatabase
    Se usan los parámetros de la anotación "@Database" para definir las entidades
    Nombre de la base de datos "ComicsDB"
 */
@Database(entities = [ComicEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    companion object{
        val DATABASE_NAME = "ComicsDB"
    }
    //La base de datos expone los DAOs por medio de metodos getter para cada @Dao
    abstract fun comicDao(): ComicDao


}