package com.abdelmageed.mondiatask.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.io.InputStream
import java.net.URL


/**
 *
 * CONVERT IMAGE URL TO IMAGE BITMAP TO SHOW IN CARD
 */
class DownloadImageTask(val bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg p0: String?): Bitmap? {

        val urldisplay = p0[0];
        var bmp: Bitmap? = null;
        try {
            val inp = URL(urldisplay).openStream();
            bmp = BitmapFactory.decodeStream(inp);
        } catch (e: Exception) {
            e.message?.let { Log.e("Error", it) };
            e.printStackTrace();
        }
        return bmp;
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        bmImage.setImageBitmap(result)
    }
}