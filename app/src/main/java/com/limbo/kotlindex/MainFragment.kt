package com.limbo.kotlindex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.limbo.kotlindex.models.SearchResultModel
import com.limbo.kotlindex.repository.PokeApiNetworkPagingRepository
import com.limbo.kotlindex.util.getViewModel
import com.limbo.kotlindex.viewmodels.SearchResultPagingViewModel
import kotlinx.android.synthetic.main.search_results_recycler_view.*

class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_results_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchResultPagedListAdapter()
        search_recycler_view.layoutManager = LinearLayoutManager(activity)
        search_recycler_view.adapter = adapter
        search_recycler_view.addItemDecoration(DividerItemDecoration(search_recycler_view.context, DividerItemDecoration.VERTICAL))

        val model = getViewModel{ SearchResultPagingViewModel(PokeApiNetworkPagingRepository()) }
        model.searchResults().observe(this, Observer<PagedList<SearchResultModel>> {
            adapter.submitList(it)
        })
    }

}