package ni.edu.uca.moviles2.comicviewer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ni.edu.uca.moviles2.comicviewer.db.dao.ComicDao
import ni.edu.uca.moviles2.comicviewer.repository.ComicRepository
import ni.edu.uca.moviles2.comicviewer.retrofit.ComicRetrofit
import javax.inject.Singleton

//Inyección de dependencias repositorio
@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCatRepository(
        comicDao: ComicDao
    ): ComicRepository {
        return ComicRepository(comicDao)
    }
}