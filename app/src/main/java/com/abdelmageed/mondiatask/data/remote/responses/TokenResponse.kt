package com.abdelmageed.mondiatask.data.remote.responses

data class TokenResponse(
    val accessToken: String,
    val expiresIn: String,
    val tokenType: String
)