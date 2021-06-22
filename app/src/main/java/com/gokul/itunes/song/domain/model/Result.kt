package com.gokul.itunes.song.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "result_table")
class Result: Serializable {


    @PrimaryKey
    @ColumnInfo(name = "track_id")
    var trackId: Long = 0

    @ColumnInfo(name = "artistId")
    var artistId: Int =0

    @ColumnInfo(name = "artistName")
    lateinit var artistName: String

    @ColumnInfo(name = "artworkUrl100")
    lateinit var artworkUrl100: String

    @ColumnInfo(name = "collectionId")
    var collectionId: Int = 0

    @ColumnInfo(name = "collectionName")
    var collectionName: String = ""

    @ColumnInfo(name = "previewUrl")
    lateinit var previewUrl: String

    @ColumnInfo(name = "trackName")
     var trackName: String = ""

}