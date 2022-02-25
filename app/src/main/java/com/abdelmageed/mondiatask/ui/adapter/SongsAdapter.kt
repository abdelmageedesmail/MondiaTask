package com.abdelmageed.mondiatask.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdelmageed.mondiatask.R
import com.abdelmageed.mondiatask.data.remote.responses.FlatResponseItem
import com.abdelmageed.mondiatask.databinding.ItemSongBinding
import com.abdelmageed.mondiatask.utils.DownloadImageTask
import com.abdelmageed.mondiatask.utils.ViewClick
import java.net.URL
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class SongsAdapter(
    val context: Context,
    val list: List<FlatResponseItem>,
    val viewClick: ViewClick
) :
    RecyclerView.Adapter<SongsAdapter.MyHolder>() {

    lateinit var binding: ItemSongBinding

    class MyHolder(binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.item_song, parent, false
        )
        val myHolder: MyHolder = MyHolder(binding)
        return myHolder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (list.get(position).cover != null) {
            DownloadImageTask(binding.ivImage)
                .execute("https://" + list.get(position).cover!!.default)
        } else {
            binding.ivImage.setImageResource(R.drawable.mondia_logo)
        }

        if (list.get(position).title == null) {
            binding.tvName.setText("No Title...")
        } else {
            binding.tvName.setText(list.get(position).title)
        }

        binding.tvType.setText(list.get(position).type)
        if (list.get(position).mainArtist != null) {
            binding.tvArtist.setText(list.get(position).mainArtist!!.name)
        } else {
            binding.tvArtist.setText(list.get(position).name)
        }

        binding.root.setOnClickListener {
            if (viewClick != null) {
                viewClick.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}