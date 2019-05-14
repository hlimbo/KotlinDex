package com.limbo.kotlindex

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.limbo.kotlindex.models.SearchResultModel
import com.squareup.picasso.Picasso

class SearchResultPagedListAdapter() : PagedListAdapter<SearchResultModel, RecyclerView.ViewHolder>(POST_COMPARATOR){
    val TAG = "SearchResultPLA"
    // called whenever ui requests a data holder for ui to render (can be cached internally by Android Framework since creating and destroying view objects during runtime is expensive process
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, ".onCreateViewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_view, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, ".onBindViewHolder called on position: $position")
        val searchResultRecord = getItem(position)
        (holder as SearchResultViewHolder).pokemonName.text = searchResultRecord?.name
        // no pictures for now
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<SearchResultModel>() {
            override fun areItemsTheSame(oldItem: SearchResultModel, newItem: SearchResultModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchResultModel, newItem: SearchResultModel): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}