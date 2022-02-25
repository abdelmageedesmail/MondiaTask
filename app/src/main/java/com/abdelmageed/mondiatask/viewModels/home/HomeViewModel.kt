package com.abdelmageed.mondiatask.viewModels.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelmageed.mondiatask.data.remote.networkClasses.NetWorkConnection
import com.abdelmageed.mondiatask.data.remote.responses.FlatResponseItem
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * GET USER TOKEN
 * GET FILTER DATA FIRST I SET DEFAULT VALUE TO SHOW DATA (KOTLIN)
 * THEN YOU CAN FILTER THESE DATA BY SENT ANY PARAM DATA IN TEXT FILED
 */

class HomeViewModel() : ViewModel() {

    private val _homeStateFlow = MutableStateFlow<HomeStateFlow>(HomeStateFlow.empty)
    private val _flatStateFlow = MutableStateFlow<FlatStateFlow>(FlatStateFlow.empty)
    val homeStateFlow = _homeStateFlow
    val flatStateFlow = _flatStateFlow

    fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeStateFlow.value = HomeStateFlow.loading
            val response = NetWorkConnection.getToken()
            val json = JSONObject(response)
            val accessToken = json.getString("accessToken")
            _homeStateFlow.value = HomeStateFlow.Success(accessToken)
        }
    }

    fun getFlat(token: String?, keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _flatStateFlow.value = FlatStateFlow.loading
            val responseFlat = NetWorkConnection.getFlat(token, keyword)
            val gson = Gson()
            val type: Type = object : TypeToken<List<FlatResponseItem?>?>() {}.type
            val flatResponseItem: MutableList<FlatResponseItem> = gson.fromJson(responseFlat, type)
            _flatStateFlow.value = FlatStateFlow.Success(flatResponseItem)
        }
    }

}