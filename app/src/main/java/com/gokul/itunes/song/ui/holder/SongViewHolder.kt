package com.gokul.itunes.song.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gokul.itunes.song.domain.model.Result
import com.gokul.itunes.song.domain.model.SongListener
import kotlinx.android.synthetic.main.item_song.view.*


class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    private val imageView = itemView.im_avatar
    private val tvSong = itemView.tv_song
    private val tvAccess = itemView.tv_access_id


    fun bind(song: Result, songListener: SongListener, position: Int) {
        tvSong.text = song.trackName
        tvAccess.text = song.artistName

        Glide.with(imageView.context)
            .load(song.artworkUrl100)
            .into(imageView)

        itemView.setOnClickListener {
            songListener.onSongClicked(song, position)
        }
    }

}