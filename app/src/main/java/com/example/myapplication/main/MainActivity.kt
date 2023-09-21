package com.example.myapplication.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
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
        ViewModelProvider(this)[MainViewModel::class.java]
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

        editTextSearch.text = loadSearchWord()

        imageViewSearch.setOnClickListener {
            val searchWord = editTextSearch.text.toString()

            // 기기에 저장
            saveSearchWord(searchWord)
            viewModel.updateSearchWord(searchWord)
        }
    }

    private fun loadSearchWord(): Editable? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val searchWord = sharedPreferences.getString("searchWord", "") ?: ""
        return Editable.Factory.getInstance().newEditable(searchWord)
    }

    private fun saveSearchWord(searchWord: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("searchWord", searchWord)
        editor.apply()
    }

}
