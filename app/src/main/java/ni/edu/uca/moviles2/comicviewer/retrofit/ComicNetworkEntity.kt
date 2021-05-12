package ni.edu.uca.moviles2.comicviewer.retrofit

import com.google.gson.annotations.SerializedName


class ComicNetworkEntity (
    @SerializedName("title")
    var title: String,
    @SerializedName("img")
    var img: String,
    @SerializedName("alt")
    var alt: String,
    @SerializedName("safe_title")
    var safeTitle: String,
    @SerializedName("transcript")
    var transcript: String,
    @SerializedName("link")
    var link: String,
    @SerializedName("num")
    var num: Int,
    @SerializedName("day")
    var day: Int,
    @SerializedName("month")
    var month: Int,
    @SerializedName("year")
    var year: Int
)