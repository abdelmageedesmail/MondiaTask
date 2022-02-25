package com.abdelmageed.mondiatask.viewModels.home

import com.abdelmageed.mondiatask.data.remote.responses.TokenResponse

sealed class HomeStateFlow {

    object loading : HomeStateFlow()
    class Success(val response: String) : HomeStateFlow()
    class error(val e: Throwable) : HomeStateFlow()
    object empty : HomeStateFlow()

}
