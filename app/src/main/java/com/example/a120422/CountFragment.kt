package com.example.a120422

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a120422.adapter.CountAdapter
import com.example.a120422.databinding.FragmentCountBinding

class CountFragment : Fragment() {

    private var _binding: FragmentCountBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val adapter by lazy {
        CountAdapter(requireContext())
    }

    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentCountBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            swipeLayout.setOnRefreshListener {
                Handler(Looper.getMainLooper())
                    .postDelayed(2000) {
                        swipeLayout.isRefreshing = false
                    }
            }
            val layoutManager = LinearLayoutManager(view.context)

            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            recyclerView.addSpaceDecoration(30)
            recyclerView.addPaginationScrollListener(layoutManager, 15) {
                if (!isLoading) {

                    isLoading = true
                    val currentList = adapter.currentList.toList().dropLast(1)
                    val lastIndex = (currentList.last() as Item.Count).value + 1
                    val resultList = currentList
                        .plus(
                            List(30) {
                                Item.Count(it + lastIndex)
                            }
                        )
                        .plus(
                            Item.Loading
                        )

                    adapter.submitList(resultList)

                    isLoading = false
                }
            }
        }
        adapter.submitList(
            List(30) { Item.Count(it) } + Item.Loading
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}