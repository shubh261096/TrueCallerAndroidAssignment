package com.example.truecallerdemo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainRepository {

    suspend fun fetchContent(url: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val document = Jsoup.connect(url).get()
                document.text()
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    }
}
