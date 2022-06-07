package com.loki.courotine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.loki.courotine.databinding.ActivityMainBinding
import com.loki.courotine.model.Tutorial

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var tutorialPagerAdapter: TutorialPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.kotlin_title)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.android_name)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.rxkotlin_name)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.kitura_name)))
        tutorialPagerAdapter = TutorialPagerAdapter(getTutorialData(), supportFragmentManager)
        binding.viewPager.adapter = tutorialPagerAdapter
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun getTutorialData(): List<Tutorial> {
        val tutorialList = arrayListOf<Tutorial>()
        tutorialList.add(Tutorial(getString(R.string.kotlin_title), getString(R.string.kotlin_url),
            getString(R.string.kotlin_desc)))
        tutorialList.add(Tutorial(getString(R.string.android_name), getString(R.string.android_url),
            getString(R.string.android_desc)))
        tutorialList.add(Tutorial(getString(R.string.rxkotlin_name), getString(R.string.rxkotlin_url),
            getString(R.string.rxkotlin_desc)))
        tutorialList.add(Tutorial(getString(R.string.kitura_name), getString(R.string.kitura_url),
            getString(R.string.kitura_desc)))
        return tutorialList
    }
}