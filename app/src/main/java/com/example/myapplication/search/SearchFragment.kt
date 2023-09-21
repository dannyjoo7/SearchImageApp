package com.example.myapplication.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.data.Item
import com.example.myapplication.data.ItemRepository
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.main.MainEventForFavorite
import com.example.myapplication.main.MainViewModel


class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        SearchListAdapter()
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private val searchViewModel by lazy {
        ViewModelProvider(
            this,
            SearchViewModelFactory(ItemRepository())
        )[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initModel()
    }

    private fun initView() = with(binding) {
        recyclerView.adapter = listAdapter
        val layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.layoutManager = layoutManager

        listAdapter.setOnItemLongClickListener(object : SearchListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(item: Item) {
                // 롱클릭 이벤트 처리
                item.isFavorite = true
                mainViewModel.addFavoriteItem(item)
            }
        })
    }

    private fun initModel() = with(binding) {
        searchViewModel.search.observe(viewLifecycleOwner) { itemList ->
            listAdapter.submitList(itemList.toMutableList())
        }

        mainViewModel.searchWord.observe(viewLifecycleOwner) {
            searchViewModel.searchImage(it)
        }

        mainViewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is MainEventForFavorite.AddFavoriteItem -> Unit
                is MainEventForFavorite.RemoveFavoriteItem -> {
                    searchViewModel.updateItem(event.item)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}