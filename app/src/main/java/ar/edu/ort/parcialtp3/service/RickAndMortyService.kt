package ar.edu.ort.parcialtp3.service

import ar.edu.ort.parcialtp3.model.ApiData
import ar.edu.ort.parcialtp3.model.PersonageWithOrigin
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickAndMortyService {

    @GET("character")
    fun getCharacters(@Query(value = "name", encoded = true) name: String): Call<ApiData>

    @GET("character/{ids}")
    fun getCharactersById(@Path("ids") ids : String): Call<List<PersonageWithOrigin>>
}