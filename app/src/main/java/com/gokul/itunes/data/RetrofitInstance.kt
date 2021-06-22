package com.gokul.itunes.data



import com.gokul.itunes.data.remote.DataServices
import com.gokul.itunes.data.remote.restapi.Endpoints
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    fun getDataService() : DataServices {

        return Retrofit.Builder()
            .baseUrl(Endpoints.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DataServices::class.java)

    }
}