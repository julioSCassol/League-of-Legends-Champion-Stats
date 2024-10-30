package com.example.league_of_legends_application.model

data class Champion(
    val id: String,
    var items: List<Item> = emptyList(),
    val key: String,
    val name: String,
    val title: String,
    val tags: List<String>,
    val icon: String,
    val sprite: Sprite?,
    val description: String,
    val stats: Stats?
)

data class Sprite(
    val url: String,
    val x: Int,
    val y: Int
)

data class Stats(
    val hp: Int,
    val hpperlevel: Int,
    val mp: Int,
    val mpperlevel: Int,
    val movespeed: Int,
    val armor: Double,
    val armorperlevel: Double,
    val spellblock: Double,
    val spellblockperlevel: Double,
    val attackrange: Int,
    val hpregen: Double,
    val hpregenperlevel: Double,
    val mpregen: Int,
    val mpregenperlevel: Int,
    val crit: Int,
    val critperlevel: Int,
    val attackdamage: Int,
    val attackdamageperlevel: Int,
    val attackspeedperlevel: Double,
    val attackspeed: Double
)
