package com.pominova.surfmemesapp.service

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pominova.surfmemesapp.R
import com.pominova.surfmemesapp.model.Meme

internal class MemeAdapter(private val context: Context?, var memes: List<Meme>)
    : RecyclerView.Adapter<MemeAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.meme_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mem = memes[position]
        holder.titleView.text = mem.title
        Glide.with(context!!).load(mem.photoUrl).into(holder.imageView);
    }

    override fun getItemCount(): Int {
        return memes.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById<View>(R.id.meme_image) as ImageView
        val titleView: TextView = view.findViewById<View>(R.id.meme_title) as TextView
    }
}