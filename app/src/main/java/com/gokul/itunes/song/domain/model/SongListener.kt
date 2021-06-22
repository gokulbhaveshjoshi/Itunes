package com.gokul.itunes.song.domain.model

interface SongListener {

    fun  onSongClicked(song: Result, position: Int)
}