package br.com.fetchpictures

import android.content.Context

class AssetsHelper {
    companion object {
        fun readContent(context: Context, fileName: String): String {
            return context.assets.open("shutter.response.json")
                .bufferedReader()
                .readLines()
                .joinToString("")
        }
    }
}