package ni.edu.uca.moviles2.comicviewer.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ni.edu.uca.moviles2.comicviewer.db.AppDatabase
import ni.edu.uca.moviles2.comicviewer.db.dao.ComicDao
import javax.inject.Singleton


//Inyección de dependencias modulo de base de datos
@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideComicDao(appDatabase: AppDatabase): ComicDao {
        return appDatabase.comicDao()
    }

}