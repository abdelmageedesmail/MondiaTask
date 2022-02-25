package com.abdelmageed.mondiatask.viewModels.home

import com.abdelmageed.mondiatask.data.remote.responses.FlatResponseItem

sealed class FlatStateFlow {

    object loading : FlatStateFlow()
    class Success(val response: MutableList<FlatResponseItem>) : FlatStateFlow()
    object empty : FlatStateFlow()
}
