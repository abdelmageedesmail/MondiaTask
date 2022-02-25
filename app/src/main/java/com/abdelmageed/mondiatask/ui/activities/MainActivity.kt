package com.abdelmageed.mondiatask.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdelmageed.mondiatask.R
import com.abdelmageed.mondiatask.data.local.PrefrenceStorage
import com.abdelmageed.mondiatask.data.remote.responses.FlatResponseItem
import com.abdelmageed.mondiatask.databinding.ActivityMainBinding
import com.abdelmageed.mondiatask.ui.adapter.SongsAdapter
import com.abdelmageed.mondiatask.utils.ProgressLoading
import com.abdelmageed.mondiatask.utils.ViewClick
import com.abdelmageed.mondiatask.viewModels.home.FlatStateFlow
import com.abdelmageed.mondiatask.viewModels.home.HomeStateFlow
import com.abdelmageed.mondiatask.viewModels.home.HomeViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect


class MainActivity : AppCompatActivity(), ViewClick {

    lateinit var viewModel: HomeViewModel
    lateinit var storage: PrefrenceStorage
    lateinit var binding: ActivityMainBinding
    lateinit var loading: ProgressLoading
    lateinit var listFlats: MutableList<FlatResponseItem>
    var songsAdapter: SongsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUi()

        setUpObserver()
    }

    override fun onResume() {
        super.onResume()
        binding.etSearch.setText("")
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        storage = PrefrenceStorage(this)
        loading = ProgressLoading((this))
        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (listFlats.size > 0) {
                    listFlats.clear()
                }
                if (songsAdapter != null) {
                    songsAdapter!!.notifyDataSetChanged()
                }
                viewModel.getFlat(storage.getId(), etSearch.text.toString())
                val inputManager: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    this.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )

                returnFlatStateFlow()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun setUpObserver() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        if (storage.getId().equals("null")) {
            viewModel.getToken()
        } else {

            viewModel.getFlat(storage.getId(), "kotlin")
            returnFlatStateFlow()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.homeStateFlow.collect {
                when (it) {
                    is HomeStateFlow.loading -> {
                        loading.showLoading()
                    }

                    is HomeStateFlow.Success -> {
                        loading.cancelLoading()
                        storage.storeId(it.response)
                        loading.show
                        viewModel.getFlat(storage.getId(), "kotlin")
                        returnFlatStateFlow()
                    }
                }
            }
        }
    }


    fun returnFlatStateFlow() {
        lifecycleScope.launchWhenCreated {
            viewModel.flatStateFlow.collect {
                when (it) {
                    is FlatStateFlow.loading -> {
                        loading.showLoading()
                    }

                    is FlatStateFlow.Success -> {
                        loading.cancelLoading()
                        listFlats = it.response
                        if (listFlats.size > 0) {
                            songsAdapter =
                                SongsAdapter(this@MainActivity, listFlats, this@MainActivity)
                            binding.rvSongs.adapter = songsAdapter

                            binding.rvSongs.layoutManager = LinearLayoutManager(this@MainActivity)
                        }

                    }

                }
            }
        }

    }

    override fun onItemClick(position: Int) {
        val gsonObject = Gson()
        val item = gsonObject.toJson(listFlats.get(position))

        startActivity(
            Intent(this, DetailsActivity::class.java).putExtra("item", item)
        )
    }
}