package com.loki.courotine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.loki.courotine.databinding.FragmentTutorialBinding
import com.loki.courotine.model.Tutorial
import com.loki.courotine.utils.SnowFilter
import kotlinx.coroutines.*
import java.net.URL

class TutorialFragment : Fragment() {

    private lateinit var binding: FragmentTutorialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTutorialBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        const val TUTORIAL_KEY = "TUTORIAL"

        @JvmStatic
        fun newInstance(tutorial: Tutorial): TutorialFragment {
            val fragmentHome = TutorialFragment()
            val args = Bundle()
            args.putParcelable(TUTORIAL_KEY, tutorial)
            fragmentHome.arguments = args
            return fragmentHome
        }

    }

    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->

            coroutineScope.launch {

                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = getString(R.string.error_message)
            }

            GlobalScope.launch {
                println("caught $throwable")
            }
        }

    private val parentJob = Job()

    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob + coroutineExceptionHandler)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tutorial = arguments?.getParcelable<Tutorial>(TUTORIAL_KEY) as Tutorial
        binding.tutorialName.text = tutorial.name
        binding.tutorialDesc.text = tutorial.description


        coroutineScope.launch {

            val originalBitmap = getOriginalBitmapAsync(tutorial)
            val snowFilterBitmap = loadSnowFilterAsync(originalBitmap)

            loadImage(snowFilterBitmap)
        }

    }

    private fun loadImage(snowFilterBitmap: Bitmap) {

        binding.progressBar.visibility = View.GONE
        binding.snowFilterImage.setImageBitmap(snowFilterBitmap)
    }

    private suspend fun getOriginalBitmapAsync(tutorial: Tutorial): Bitmap = withContext(Dispatchers.IO) {

        URL(tutorial.url).openStream().use {

            return@withContext BitmapFactory.decodeStream(it)
        }
    }

    private suspend fun loadSnowFilterAsync(originalBitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {

        SnowFilter.applySnowEffect(originalBitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }
}