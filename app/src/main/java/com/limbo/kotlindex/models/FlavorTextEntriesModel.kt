package com.limbo.kotlindex.models

import com.google.gson.annotations.SerializedName

data class FlavorTextEntriesModel(@SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntryModel>)

data class FlavorTextEntryModel(@SerializedName("flavor_text") val flavorText: String,
                                val language: LanguageModel)

data class LanguageModel(val name: String)