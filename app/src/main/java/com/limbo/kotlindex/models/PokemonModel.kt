package com.limbo.kotlindex.models

import com.google.gson.annotations.SerializedName

// goal: Map each respective model to an endpoint providing the response body

data class PokemonModel(val abilities: List<AbilityContainer>,
                        @SerializedName("base_experience") val baseExperience: Int,
                        val forms: List<Form>,
                        @SerializedName("game_indices") val gameIndices: List<GameIndex>,
                        val height: Int,
                        @SerializedName("held_items") val heldItems: List<HeldItem>,
                        val id: Int,
                        @SerializedName("is_default") val isDefault: Boolean,
                        @SerializedName("location_area_encounters") val locationAreaEncounters: String,
                        val moves: List<MoveContainer>,
                        val name: String,
                        @SerializedName("order") val nationalDexNumber: Int,
                        val species: Species,
                        val sprites: Sprites,
                        val stats: List<StatContainer>,
                        val types: List<PokemonTypeInfo>,
                        val weight: Int)

// ** Abilities **//
data class AbilityContainer(val ability: Ability,
                            @SerializedName("is_hidden") val isHidden: Boolean,
                            val slot: Int)
data class Ability(val name: String, val url: String)
// ******************** //

// ** Form ** //
data class Form(val name: String, val url: String)
// ********* //

// ** Game Index ** //
data class GameIndex(@SerializedName("game_index") val gameIndex: Int,
                     val version: Version)
data class Version(val name: String, val url: String)
// *************** //

// ** Held Item ** //
data class HeldItem(val item: Item, @SerializedName("version_details") val versionDetails: List<VersionDetail>)
data class Item(val name: String, val url: String)
data class VersionDetail(val rarity: Int, val version: Version)
// ********************* //


// ** MoveContainer ** //
data class MoveContainer(val move: Move,
                         @SerializedName("version_group_details") val versionGroupDetails: List<VersionGroupDetail>)
data class Move(val name: String, val url: String)
data class VersionGroupDetail(@SerializedName("level_learned_at") val levelLearnedAt: Int,
                              @SerializedName("move_learn_method") val moveLearnMethod: MoveLearnMethod,
                              @SerializedName("version_group") val versionGroup: VersionGroup)
data class MoveLearnMethod(val name: String, val url: String)
data class VersionGroup(val name: String, val url: String)
// ********************** //

data class Species(val name: String, val url: String)

data class Sprites(@SerializedName("back_default") val backDefault: String,
                   @SerializedName("back_female") val backFemale: String?,
                   @SerializedName("back_shiny") val backShiny: String,
                   @SerializedName("back_shiny_female") val backShinyFemale: String?,
                   @SerializedName("front_default") val iconPath: String,
                   @SerializedName("front_female") val frontFemale: String?,
                   @SerializedName("front_shiny") val iconShinyPath: String,
                   @SerializedName("front_shiny_female") val frontShinyFemale: String?)

data class StatContainer(@SerializedName("base_stat") val baseStat: Int,
                         val effort: Int,
                         val stat: Stat)
data class Stat(val name: String, val url: String)

data class PokemonTypeInfo(val slot: Int, val type: PokemonType)
data class PokemonType(val name: String, val url: String)