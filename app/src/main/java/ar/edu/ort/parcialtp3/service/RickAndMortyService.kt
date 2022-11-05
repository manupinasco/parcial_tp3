package ar.edu.ort.parcialtp3.service

import ar.edu.ort.parcialtp3.model.ApiData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RickAndMortyService {

    @GET("character")
    fun getCharacters(@Query(value = "name", encoded = true) name: String): Call<ApiData>
}