package ni.edu.uca.moviles2.comicviewer.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ni.edu.uca.moviles2.comicviewer.adapters.ComicListAdapter
import javax.inject.Singleton
//Inyección de dependencias adptadores
@Module
@InstallIn(ApplicationComponent::class)
object AdapterModule {

    @Singleton
    @Provides
    fun provideComicApapter(application: Application): ComicListAdapter {
        return ComicListAdapter()
    }
}