package com.gokul.itunes.data.remote

import com.gokul.itunes.data.remote.restapi.Endpoints
import com.gokul.itunes.song.domain.model.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataServices {

    @GET(Endpoints.GET_SEARCH)
    fun getSongs(@Query("term") term: String,
                 @Query("mediaType") mediaType: String = "music",
                 @Query("offset") offset: Int? = null,
                 @Query("limit") limit: Int
    ): Call<Results>
}