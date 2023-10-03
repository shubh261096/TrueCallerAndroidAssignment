package com.example.truecallerdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.truecallerdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.task1TextView.visibility = View.GONE
        binding.task2TextView.visibility = View.GONE
        binding.task3TextView.visibility = View.GONE

        binding.fetchContentButton.setOnClickListener {
            if (CommonUtils.getConnectivityStatus(this)) {
                binding.progressBar.visibility = View.VISIBLE
                val url =
                    "https://www.truecaller.com/blog/life-at-truecaller/life-as-an-android-engineer"
                viewModel.fetchContentFromUrl(url)
            } else {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.content.observe(this) {
            if (it != null && it.isNotEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.contentTextView.text = it
            }
        }

        viewModel.char15.observe(this) {
            if (it != null && it.toString().isNotEmpty()) {
                binding.task1TextView.visibility = View.VISIBLE
                Log.i(TAG, "onCreate: 15th Character: $it")
                binding.task1ResultTextView.text = "$it"
            }

        }
        viewModel.every15thChar.observe(
            this
        ) {
            if (it != null && it.isNotEmpty()) {
                binding.task2TextView.visibility = View.VISIBLE
                Log.i(TAG, "onCreate: Every 15th Character: ${it.joinToString(", ")}")
                binding.task2ResultTextView.text = it.joinToString(", ")
            }

        }
        viewModel.wordCounts.observe(this) {
            if (it != null && it.entries.isNotEmpty()) {
                binding.task3TextView.visibility = View.VISIBLE
                Log.i(TAG, "onCreate: Word Counts: \n$it")
                val jsonString = it.entries.joinToString(", ")
                binding.task3ResultTextView.text = jsonString
            }
        }
    }
}