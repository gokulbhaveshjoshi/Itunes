package com.gokul.itunes.song.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.gokul.itunes.R
import kotlinx.android.synthetic.main.fragment_song_search_form.*


private const val TAG = "SongSearchFormFragment"

class SongSearchFormFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_search_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_query.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                p0?.let { query ->
                    if(query.length > 1){
                        btn_query.setOnClickListener {
                            sendQuery(query.toString())
                        }
                    }else {
                        btn_query.setOnClickListener(null)
                    }
                }
            }

        })

//        et_query.setOnKeyListener(object: View.OnKeyListener{
//            override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
//                if(event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
//
//                        sendQuery(et_query.text.toString())
//                        return true
//
//
//                }
//                return false
//            }
//
//        })

        et_query.setOnEditorActionListener { p0, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                // sendMessage()

                if(et_query.text.isNotEmpty()){
                    Log.e(TAG, "onViewCreated: Que pa-  ${et_query.text}" )
                   sendQuery(et_query.text.toString())
                }else {
                    Log.e(TAG, "onViewCreated: Que paso ?" )
                }

                handled = true
            }
            handled
        }

    }

    fun sendQuery(query: String) {
        et_query.text = null
        hideKeyboard()
        val bundle = bundleOf("query" to query)
        view?.let { Navigation.findNavController(it).navigate(R.id.action_songSearchFormFragment_to_songListFragment, bundle) }

    }


    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}