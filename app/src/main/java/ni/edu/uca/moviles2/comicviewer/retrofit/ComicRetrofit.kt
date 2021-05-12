package ni.edu.uca.moviles2.comicviewer.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ComicRetrofit {
    @GET
    fun getCurrentComic(@Url url:String): Call<ComicNetworkEntity>
}