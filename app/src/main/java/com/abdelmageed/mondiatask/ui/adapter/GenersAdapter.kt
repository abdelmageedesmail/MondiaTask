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
import com.abdelmageed.mondiatask.databinding.ItemGenersBinding
import com.abdelmageed.mondiatask.databinding.ItemSongBinding
import com.abdelmageed.mondiatask.utils.DownloadImageTask
import com.abdelmageed.mondiatask.utils.ViewClick
import java.net.URL
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class GenersAdapter(
    val context: Context,
    val list: List<String>
) :
    RecyclerView.Adapter<GenersAdapter.MyHolder>() {

    lateinit var binding: ItemGenersBinding

    class MyHolder(binding: ItemGenersBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.item_geners, parent, false
        )
        val myHolder: MyHolder = MyHolder(binding)
        return myHolder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {


            binding.tvGenersName.setText(list.get(position))

    }

    override fun getItemCount(): Int {
        return list.size
    }
}