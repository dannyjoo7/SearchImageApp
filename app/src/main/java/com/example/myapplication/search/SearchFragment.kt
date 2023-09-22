package com.example.myapplication.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Item
import com.example.myapplication.data.ItemRepository
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.main.MainEventForFavorite
import com.example.myapplication.main.MainEventForSearch
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

    // 무한 스크롤...
    private var isLoading = false // 데이터 로딩 중인지 여부를 나타내는 플래그

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

        // 롱클릭 시 북마크...
        listAdapter.setOnItemLongClickListener(object : SearchListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(item: Item) {
                // 롱클릭 이벤트 처리
                item.isFavorite = true
                mainViewModel.addFavoriteItem(item)
            }
        })



        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // 스크롤이 마지막 아이템 근처로 도달하면 추가 데이터를 로드합니다.
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition >= totalItemCount) && firstVisibleItemPosition >= 0) {
                    isLoading = true
                    searchViewModel.loadNextPage()
                }
            }
        })
    }

    private fun initModel() = with(binding) {
        searchViewModel.search.observe(viewLifecycleOwner) { itemList ->
            // 데이터 로드 완료...
            isLoading = false

            // 이미 북마크 된 요소들 처리
            listAdapter.submitList(itemList.toMutableList())
        }

        mainViewModel.searchEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                is MainEventForSearch.SearchItem -> {
                    searchViewModel.searchItem(event.word)
                }
            }
        }

        mainViewModel.favoriteEvent.observe(viewLifecycleOwner) { event ->
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