package com.example.truecallerdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private val repository = MainRepository()

    private val _content = MutableLiveData<String>()
    val content: LiveData<String> get() = _content

    private val _char15 = MutableLiveData<Char>()
    val char15: LiveData<Char> get() = _char15

    private val _every15thChar = MutableLiveData<List<Char>>()
    val every15thChar: LiveData<List<Char>> get() = _every15thChar

    private val _wordCounts = MutableLiveData<Map<String, Int>>()
    val wordCounts: LiveData<Map<String, Int>> get() = _wordCounts


    fun fetchContentFromUrl(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Fetching the content from the url
                val content = repository.fetchContent(url)
                _content.postValue(content)

                // Create a coroutine scope
                val scope = CoroutineScope(Dispatchers.IO)

                // Launch a coroutine for each task parallel
                scope.launch {
                    val char15 = find15thCharacter(content)
                    _char15.postValue(char15)
                }

                scope.launch {
                    val every15thChar = findEvery15thCharacter(content)
                    _every15thChar.postValue(every15thChar)
                }

                scope.launch {
                    val wordCount = countWords(content)
                    _wordCounts.postValue(wordCount)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun find15thCharacter(content: String): Char? {
        val index = 14
        return if (index < content.length) {
            content[index]
        } else {
            null
        }
    }

    private fun findEvery15thCharacter(content: String): List<Char> {
        val result = mutableListOf<Char>()
        val step = 15
        var i = 14
        while (i < content.length) {
            result.add(content[i])
            i += step
        }
        return result
    }

    private fun countWords(content: String): Map<String, Int> {
        val wordCounts = mutableMapOf<String, Int>()
        var wordStart = 0
        var wordEnd = 0
        while (wordEnd < content.length) {
            if (content[wordEnd] == ' ' || content[wordEnd] == '\n' || content[wordEnd] == '\t') {
                val word = content.substring(wordStart, wordEnd)
                val lowerWord = word.lowercase()
                val count = wordCounts[lowerWord] ?: 0
                wordCounts[lowerWord] = count + 1
                wordStart = wordEnd + 1
            }
            wordEnd++
        }
        // Handling the last word
        if (wordStart < wordEnd) {
            val word = content.substring(wordStart, wordEnd)
            val lowerWord = word.lowercase()
            val count = wordCounts[lowerWord] ?: 0
            wordCounts[lowerWord] = count + 1
        }
        return wordCounts
    }
}
