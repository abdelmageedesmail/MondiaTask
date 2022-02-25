package com.abdelmageed.mondiatask.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.abdelmageed.mondiatask.R
import com.abdelmageed.mondiatask.data.remote.responses.FlatResponseItem
import com.abdelmageed.mondiatask.databinding.ActivityDetailsBinding
import com.abdelmageed.mondiatask.ui.adapter.GenersAdapter
import com.abdelmageed.mondiatask.utils.DownloadImageTask
import com.google.gson.Gson

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding
    lateinit var model: FlatResponseItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUi()

    }

    private fun setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        val stringExtra = intent.getStringExtra("item")
        model = Gson().fromJson(stringExtra, FlatResponseItem::class.java)
        fillViews()
    }

    private fun fillViews() {
        if (model.cover != null) {
            DownloadImageTask(binding.ivImage)
                .execute("https://" + model.cover!!.default)
        } else {
            binding.ivImage.setImageResource(R.drawable.mondia_logo)
        }
        if (model.publishingDate != null) {
            binding.tvDate.setText(model.publishingDate)
        }
        if (model.title != null) {
            binding.tvInnerTitle.setText(model.title)
            binding.tvTitle.setText(model.title)
        } else {
            binding.tvInnerTitle.setText("No title..")
            binding.tvTitle.setText("No title..")
        }
        if (model.licensorName != null) {
            binding.licensorName.setText(model.licensorName)

        } else {
            binding.licensorName.setText("No data found..")

        }
        if (model.mainArtist != null) {
            binding.tvInnerArtist.setText(model.mainArtist!!.name)
        } else {
            binding.tvInnerArtist.setText(model.name)
        }


        binding.tvType.setText(model.type)
        if (model.genres != null) {
            if (model.genres!!.size > 0) {
                binding.noDataFound.visibility = View.GONE
                binding.rvGeners.adapter = GenersAdapter(this, model.genres!!)
                binding.rvGeners.layoutManager = GridLayoutManager(this, 2)
            } else {
                binding.noDataFound.visibility = View.VISIBLE
            }
        } else {
            binding.noDataFound.visibility = View.VISIBLE
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }
}