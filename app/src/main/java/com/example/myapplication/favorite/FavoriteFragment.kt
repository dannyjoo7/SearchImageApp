package com.example.myapplication.favorite

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
import com.example.myapplication.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        FavoriteListAdapter()
    }

    private val favoriteViewModel by lazy {
        ViewModelProvider(
            this,
            FavoriteViewModelFactory(ItemRepository())
        )[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initModel()
    }

    override fun onResume() = with(binding) {
        super.onResume()
        recyclerView.adapter = listAdapter
    }

    private fun initView() = with(binding) {
        recyclerView.adapter = listAdapter
        val layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.layoutManager = layoutManager

        listAdapter.setOnItemLongClickListener(object :
            FavoriteListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(item: Item) {
                // 롱클릭 이벤트 처리
                // item 객체를 이용하여 필요한 작업 수행

                favoriteViewModel.removeFavoriteItem(item)
            }
        })
    }

    private fun initModel() = with(binding) {
        favoriteViewModel.favorite.observe(viewLifecycleOwner) { it ->
            listAdapter.submitList(it.toMutableList())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}