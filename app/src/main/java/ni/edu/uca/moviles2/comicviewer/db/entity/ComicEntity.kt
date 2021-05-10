package ni.edu.uca.moviles2.comicviewer.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    Clase "data class" que describe la entidad que respresenta la tabla en SQLite.
    Cada propiedad de la clase representa una columna en la tabla.
    Room usa estas propiedades para crear la tabla e instanciar objetos a partir de los registros en la base de datos
    @Entity(tableName = "comics") Nombre de la tabla es "comics"
    @PrimaryKey(autoGenerate = false) Nombre de la clave primaria es "id"
*/
@Entity(tableName =  "comics")
data class ComicEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "img")
    var img: String,
    @ColumnInfo(name = "alt")
    var alt: String,
    @ColumnInfo(name = "safe_title")
    var safeTitle: String,
    @ColumnInfo(name = "transcript")
    var transcript: String,
    @ColumnInfo(name = "link")
    var link: String,
    @ColumnInfo(name = "num")
    var num: Int,
    @ColumnInfo(name = "day")
    var day: Int,
    @ColumnInfo(name = "month")
    var month: Int,
    @ColumnInfo(name = "year")
    var year: Int,
    @ColumnInfo(name = "date_added")
    val dateAdded: Long
)