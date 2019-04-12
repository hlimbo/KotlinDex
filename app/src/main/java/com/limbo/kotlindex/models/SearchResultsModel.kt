package com.limbo.kotlindex.models

import com.google.gson.annotations.SerializedName

data class SearchResultsModel(val count: Int,
                              @SerializedName("next") val nextPageUrl: String?,
                              @SerializedName("previous") val previousPageUrl: String?,
                              val results: List<SearchResultModel>)
data class SearchResultModel(val name: String, val url: String)