package com.abdelmageed.mondiatask.data.remote.networkClasses

import android.util.Log
import com.abdelmageed.mondiatask.BuildConfig
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class NetWorkConnection {

    companion object {
        suspend fun getToken(): String {
            return suspendCancellableCoroutine {
                try {
                    val reader: BufferedReader
                    val url = URL(BuildConfig.BASE_URL + "/v0/api/gateway/token/client")
                    with(url.openConnection() as HttpURLConnection) {
                        requestMethod = "POST"
                        this.setRequestProperty("X-MM-GATEWAY-KEY", BuildConfig.API_KEY)
                        reader = BufferedReader(InputStreamReader(inputStream) as Reader)
                        val response = StringBuffer()
                        var inputLine = reader.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = reader.readLine()
                        }
                        reader.close()
                        if (it.isActive) {
                            it.resume(response.toString())
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    if (it.isActive) {
                        it.resumeWithException(e)
                    }
                }
            }

        }

        suspend fun getFlat(token: String?, keyword: String): String? {
            return suspendCancellableCoroutine {
                try {
                    val reader: BufferedReader
                    val url = URL(BuildConfig.BASE_URL + "/v2/api/sayt/flat?query=" + keyword)
                    Log.e("urlsss", "" + url.toString())
                    with(url.openConnection() as HttpURLConnection) {
                        requestMethod = "GET"
                        this.setRequestProperty("Authorization", "Bearer " + token)
                        reader = BufferedReader(InputStreamReader(inputStream) as Reader)
                        val response = StringBuffer()
                        var inputLine = reader.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = reader.readLine()
                        }
                        reader.close()
                        if (it.isActive) {
                            it.resume(response.toString())
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    if (it.isActive) {
                        it.resumeWithException(e)
                    }
                }
            }

        }
    }
}