package ar.edu.ort.parcialtp3.service

import ar.edu.ort.parcialtp3.model.ApiData
import ar.edu.ort.parcialtp3.model.PersonageWithOrigin
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickAndMortyService {

    /*/characters?name="name"
    * Aca el endpoint trae los personajes por nombre*/
    @GET("character")
    fun getCharacters(@Query(value = "name", encoded = true) name: String): Call<ApiData>

    /*/characters/ids
    * aca trae todos los personajes de la lista del user
    * El endpoint trae todos los personajes con el id separado por comas*/
    @GET("character/{ids}")
    fun getCharactersById(@Path("ids") ids : String): Call<List<PersonageWithOrigin>>
}