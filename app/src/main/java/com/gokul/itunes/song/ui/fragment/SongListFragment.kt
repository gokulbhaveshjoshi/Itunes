package com.gokul.itunes.song.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokul.itunes.R
import com.gokul.itunes.song.domain.model.Result
import com.gokul.itunes.song.domain.model.SongListener
import com.gokul.itunes.song.domain.viewmodel.SongListViewModel
import com.gokul.itunes.song.ui.adapter.SongAdapter
import kotlinx.android.synthetic.main.fragment_song_list.*

private const val TAG = "SongListFragment"
class SongListFragment : Fragment(), SongListener {

    lateinit var adapter: SongAdapter

    lateinit var songListViewModel: SongListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query= arguments?.getString("query") as String

        showScreen(query)


    }

    private fun showScreen(query: String) {
        adapter = SongAdapter(this)
        shimmer_view_container.startShimmer()

        rv_song.layoutManager = LinearLayoutManager(context)

        songListViewModel = ViewModelProvider(this).get(SongListViewModel::class.java)


        context?.let { songListViewModel.searchQuery(query, it) }


        songListViewModel.songsPageList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

        })

        songListViewModel.isLoading.observe(viewLifecycleOwner, Observer {

            if(it){
                shimmer_view_container.visibility = View.GONE
                shimmer_view_container.stopShimmer()
            }
        })

        songListViewModel.resultNotFound.observe(viewLifecycleOwner, Observer {
            if(it){
                iv_result_not_found.visibility = View.VISIBLE
                rv_song.visibility = View.INVISIBLE
            }
        })

        rv_song.adapter = adapter
    }

    override fun onSongClicked(song: Result, position: Int) {
        val bundle = bundleOf("song" to song)
        findNavController().navigate(R.id.songDetailDialogFragment, bundle)
    }

}