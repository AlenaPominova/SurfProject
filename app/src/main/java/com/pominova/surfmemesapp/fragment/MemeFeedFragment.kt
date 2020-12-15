package com.pominova.surfmemesapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pominova.surfmemesapp.R
import com.pominova.surfmemesapp.model.Meme
import com.pominova.surfmemesapp.service.MemeAdapter
import com.pominova.surfmemesapp.service.NetworkService
import com.pominova.surfmemesapp.util.AppConstant
import com.zharkovsky.memes.viewModels.DashboardViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timerTask

class MemeFeedFragment : Fragment() {
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.meme_feed_fragment, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_list)
        recyclerView.setHasFixedSize(true)
        val layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        loadMemes(recyclerView, root)
        return root
    }

    private fun loadMemes(recyclerView: RecyclerView, root: View) {
        val progressBar: ProgressBar = root.findViewById(R.id.load_memes_progress_bar)
        progressBar.visibility = View.VISIBLE

        Timer().schedule(timerTask {
            NetworkService.getInstance()
                .networkAPI
                .memes()
                .enqueue(object : Callback<List<Meme>> {
                    override fun onResponse(
                        call: Call<List<Meme>>,
                        response: Response<List<Meme>>
                    ) {
                        println(response.code())
                        val errorLoadMemesTextView =
                            root.findViewById<TextView>(R.id.error_load_memes_tv)
                        val retryTextView = root.findViewById<TextView>(R.id.retry_tv)
                        if (response.code() != 200) {
                            errorLoadMemesTextView.text = getText(R.string.load_memes_error_message)
                            retryTextView.text = getText(R.string.try_again_message)
                        } else {
                            val memes = response.body()!!
                            val adapter = MemeAdapter(activity, memes)
                            recyclerView.adapter = adapter
                            errorLoadMemesTextView.text = ""
                            retryTextView.text = ""
                        }
                        progressBar.visibility = View.GONE
                    }

                    override fun onFailure(call: Call<List<Meme>>, t: Throwable) {}
                })

        }, AppConstant.PROGRESS_BUTTON_DELAY)
    }
}