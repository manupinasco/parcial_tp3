package ar.edu.ort.parcialtp3.service

import ar.edu.ort.parcialtp3.model.ApiData
import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyService {

    @GET("character")
    fun getAllCharacters(): Call<ApiData>
}