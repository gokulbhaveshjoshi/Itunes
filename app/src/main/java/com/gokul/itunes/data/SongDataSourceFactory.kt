package com.gokul.itunes.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.gokul.itunes.data.remote.DataServices
import com.gokul.itunes.data.remote.SongDataSource
import com.gokul.itunes.song.domain.model.Result


class SongDataSourceFactory(val query: String, val context: Context): DataSource.Factory<Int, Result>() {

    val songLiveDataSource = MutableLiveData<SongDataSource>()
    val dataServices: DataServices = RetrofitInstance.getDataService()

    override fun create(): DataSource<Int, Result> {
        val userDataSource =
            SongDataSource(dataServices, query, context)
            songLiveDataSource.postValue(userDataSource)

        return  userDataSource
    }
}