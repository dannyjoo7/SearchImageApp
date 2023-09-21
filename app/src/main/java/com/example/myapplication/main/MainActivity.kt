package com.example.myapplication.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewPagerAdapter by lazy {
        MainViewPagerAdapter(this@MainActivity)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)
            .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> searchLayout.visibility = View.VISIBLE
                    else -> searchLayout.visibility = View.INVISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        imageViewSearch.setOnClickListener {
            val searchWord = editTextSearch.text.toString()
            viewModel.updateSearchWord(searchWord)
        }
    }
}
