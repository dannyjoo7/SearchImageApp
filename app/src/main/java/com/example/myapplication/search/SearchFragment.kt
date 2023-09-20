package com.example.myapplication.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.data.Item
import com.example.myapplication.data.Data
import com.example.myapplication.data.ItemRepository
import com.example.myapplication.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        SearchListAdapter()
    }

    private val viewModel by lazy {
        ViewModelProvider(this, SearchViewModelFactory(ItemRepository()))
            .get(SearchViewModel::class.java)
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
                // item 객체를 이용하여 필요한 작업 수행

                viewModel.addFavoriteItem(item)

                // test
                viewModel.searchImage()
            }
        })
    }

    private fun initModel() = with(binding) {
        viewModel.getItemList().observe(viewLifecycleOwner) { itemList ->
            listAdapter.submitList(itemList)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}