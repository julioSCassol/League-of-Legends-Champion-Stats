package com.example.league_of_legends_application.utils

import android.content.Context
import android.media.MediaPlayer
import com.example.league_of_legends_application.R

object ChampionSounds {

    var mediaPlayerFactory: (Context, Int) -> MediaPlayer? = { context, resId ->
        MediaPlayer.create(context, resId)
    }

    fun getSoundResId(championName: String): Int {
        return when (championName.lowercase()) {
            "aatrox" -> R.raw.aatrox
            "ahri" -> R.raw.ahri
            "akali" -> R.raw.akali
            "alistar" -> R.raw.alistar
            "amumu" -> R.raw.amumu
            "anivia" -> R.raw.anivia
            "annie" -> R.raw.annie
            "ashe" -> R.raw.ashe
            "blitzcrank" -> R.raw.blitzcrank
            "brand" -> R.raw.brand
            "caitlyn" -> R.raw.caitlyn
            "cassiopeia" -> R.raw.cassiopeia
            "cho'gath" -> R.raw.chogath
            "corki" -> R.raw.corki
            "darius" -> R.raw.darius
            "diana" -> R.raw.diana
            "dr. mundo" -> R.raw.drmundo
            "draven" -> R.raw.draven
            "elise" -> R.raw.elise
            "evelynn" -> R.raw.evelynn
            "ezreal" -> R.raw.ezreal
            "fiddlesticks" -> R.raw.fiddlesticks
            "fiora" -> R.raw.fiora
            "fizz" -> R.raw.fizz
            "galio" -> R.raw.galio
            "gangplank" -> R.raw.gangplank
            "garen" -> R.raw.garen
            "gragas" -> R.raw.gragas
            "graves" -> R.raw.graves
            "hecarim" -> R.raw.hecarim
            "heimerdinger" -> R.raw.heimerdinger
            "irelia" -> R.raw.irelia
            "janna" -> R.raw.janna
            "jarvan iv" -> R.raw.jarvaniv
            "jax" -> R.raw.jax
            "jayce" -> R.raw.jayce
            "jinx" -> R.raw.jinx
            "karma" -> R.raw.karma
            "karthus" -> R.raw.karthus
            "kassadin" -> R.raw.kassadin
            "katarina" -> R.raw.katarina
            "kayle" -> R.raw.kayle
            "kennen" -> R.raw.kennen
            "kog'maw" -> R.raw.kogmaw
            "leblanc" -> R.raw.leblanc
            "lee sin" -> R.raw.leesin
            "leona" -> R.raw.leona
            "lissandra" -> R.raw.lissandra
            "lucian" -> R.raw.lucian
            "lulu" -> R.raw.lulu
            "lux" -> R.raw.lux
            "malphite" -> R.raw.malphite
            "malzahar" -> R.raw.malzahar
            "maokai" -> R.raw.maokai
            "master yi" -> R.raw.masteryi
            "miss fortune" -> R.raw.missfortune
            "mordekaiser" -> R.raw.mordekaiser
            "morgana" -> R.raw.morgana
            "nami" -> R.raw.nami
            "nasus" -> R.raw.nasus
            "nautilus" -> R.raw.nautilus
            "nidalee" -> R.raw.nidalee
            "nocturne" -> R.raw.nocturne
            "nunu & willump" -> R.raw.nunu
            "olaf" -> R.raw.olaf
            "orianna" -> R.raw.orianna
            "pantheon" -> R.raw.pantheon
            "poppy" -> R.raw.poppy
            "quinn" -> R.raw.quinn
            "rammus" -> R.raw.rammus
            "renekton" -> R.raw.renekton
            "rengar" -> R.raw.rengar
            "riven" -> R.raw.riven
            "rumble" -> R.raw.rumble
            "ryze" -> R.raw.ryze
            "sejuani" -> R.raw.sejuani
            "shaco" -> R.raw.shaco
            "shen" -> R.raw.shen
            "shyvana" -> R.raw.shyvana
            "singed" -> R.raw.singed
            "sion" -> R.raw.sion
            "sivir" -> R.raw.sivir
            "skarner" -> R.raw.skarner
            "sona" -> R.raw.sona
            "swain" -> R.raw.swain
            "syndra" -> R.raw.syndra
            "talon" -> R.raw.talon
            "taric" -> R.raw.taric
            "teemo" -> R.raw.teemo
            "thresh" -> R.raw.thresh
            "tristana" -> R.raw.tristana
            "trundle" -> R.raw.trundle
            "tryndamere" -> R.raw.tryndamere
            "twitch" -> R.raw.twitch
            "udyr" -> R.raw.udyr
            "urgot" -> R.raw.urgot
            "varus" -> R.raw.varus
            "vayne" -> R.raw.vayne
            "veigar" -> R.raw.veigar
            "vi" -> R.raw.vi
            "viktor" -> R.raw.viktor
            "vladimir" -> R.raw.vladimir
            "volibear" -> R.raw.volibear
            "warwick" -> R.raw.warwick
            "wukong" -> R.raw.wukong
            "xerath" -> R.raw.xerath
            "xin zhao" -> R.raw.xinzhao
            "yasuo" -> R.raw.yasuo
            "yorick" -> R.raw.yorick
            "zac" -> R.raw.zac
            "zed" -> R.raw.zed
            "ziggs" -> R.raw.ziggs
            "zilean" -> R.raw.zilean
            "zyra" -> R.raw.zyra
            else -> 0
        }
    }

    fun playChampionSound(context: Context, championName: String): MediaPlayer? {
        val soundResId = getSoundResId(championName)
        return if (soundResId != 0) {
            mediaPlayerFactory(context, soundResId)?.apply { start() }
        } else {
            null
        }
    }
}
