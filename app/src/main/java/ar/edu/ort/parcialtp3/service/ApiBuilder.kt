package ar.edu.ort.parcialtp3.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder {
    private val BASE_URL = "https://rickandmortyapi.com/api/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): RickAndMortyService{
        return retrofit.create(RickAndMortyService::class.java)
    }
}