package com.gokul.itunes.song.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.gokul.itunes.R
import com.gokul.itunes.song.domain.model.Result
import kotlinx.android.synthetic.main.fragment_song_detail_dialog.*

private const val TAG = "SongDetailDialogFragmen"
class SongDetailDialogFragment : DialogFragment() {

    var mediaPlayer: MediaPlayer? = null

    var totalTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_song_dialog.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_baseline_close_24)

        toolbar_song_dialog.setTitleTextColor(Color.BLACK)
        val song = arguments?.getSerializable("song") as Result

         mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(song.previewUrl)
            prepare()

             totalTime = duration
        }

        toolbar_song_dialog.setNavigationOnClickListener {
            dismiss()
            mediaPlayer?.pause()
        }

        Glide.with(view.context)
            .load(song.artworkUrl100)
            .into(iv_detail_dialog_image)

        toolbar_song_dialog.title = song.trackName
        song.collectionName?.let {
            tv_details_dialog_name.text = it
        }
        tv_details_dialog_banda.text = song.artistName

       player()

    }

    fun player(){
        mediaPlayer?.let { media ->
            btn_play.setOnClickListener {

                if(media.isPlaying){
                    media.pause()

                    btn_play.setBackgroundResource(R.drawable.ic_baseline_play_circle_filled_24)

                }else {
                    media.start()
                    btn_play.setBackgroundResource(R.drawable.ic_baseline_pause_circle_filled_24)
                }

            }

        }

        seekBar.max = totalTime

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, from: Boolean) {
                if(from){
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        Thread(Runnable {

            while (mediaPlayer != null) {
                mediaPlayer?.let {
                    try {
                        var msg = Message()
                        msg.what = it.currentPosition
                        handler.sendMessage(msg)

                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {

                    }
                }
            }

        }).start()
    }

    private var handler = @SuppressLint("HandlerLeak")
    object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(seekBar != null) {
                val currentPosition = msg.what
                seekBar.progress = currentPosition

                val elapsedTime = createTimeLabel(currentPosition)
                elapsedTimeLb.text = elapsedTime

                var remainingTime = createTimeLabel(totalTime - currentPosition)

                remainingTimeLb.text = "- $remainingTime"
            }

        }
    }


    fun createTimeLabel(time: Int): String{

        var timeLabel = ""
        var min = time/ 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min: "
        if(sec < 10) timeLabel += 0
        timeLabel += sec

        return  timeLabel
    }




    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.let {
         it.pause()
        }
    }


}