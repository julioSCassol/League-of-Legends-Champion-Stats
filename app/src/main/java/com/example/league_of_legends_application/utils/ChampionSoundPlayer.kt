package com.example.league_of_legends_application.utils

import android.content.Context
import android.media.MediaPlayer
import com.example.league_of_legends_application.R

object ChampionSounds {
    fun getSoundResId(championName: String): Int {
        return when (championName.lowercase()) {
            "aatrox" -> R.raw.aatrox
            "ahri" -> R.raw.ahri
            "akali" -> R.raw.akali
            "alistar" -> R.raw.alistar
            "amumu" -> R.raw.amumu
            "anivia" -> R.raw.anivia
            "annie" -> R.raw.annie
//            "aphelios" -> R.raw.aphelios
            "ashe" -> R.raw.ashe
//            "aurelion sol" -> R.raw.aurelion_sol
//            "azir" -> R.raw.azir
//            "bard" -> R.raw.bard
            "blitzcrank" -> R.raw.blitzcrank
            "brand" -> R.raw.brand
//            "braum" -> R.raw.braum
//            "caitlyn" -> R.raw.caitlyn
//            "camille" -> R.raw.camille
//            "cassiopeia" -> R.raw.cassiopeia
//            "cho'gath" -> R.raw.chogath
//            "corki" -> R.raw.corki
//            "darius" -> R.raw.darius
//            "diana" -> R.raw.diana
//            "dr. mundo" -> R.raw.drmundo
//            "draven" -> R.raw.draven
//            "ekko" -> R.raw.ekko
//            "elise" -> R.raw.elise
//            "evelynn" -> R.raw.evelynn
//            "ezreal" -> R.raw.ezreal
//            "fiddlesticks" -> R.raw.fiddlesticks
//            "fiora" -> R.raw.fiora
//            "fizz" -> R.raw.fizz
//            "galio" -> R.raw.galio
//            "gangplank" -> R.raw.gangplank
//            "garen" -> R.raw.garen
//            "gnar" -> R.raw.gnar
//            "gragas" -> R.raw.gragas
//            "graves" -> R.raw.graves
//            "gwen" -> R.raw.gwen
//            "hecarim" -> R.raw.hecarim
//            "heimerdinger" -> R.raw.heimerdinger
//            "illaoi" -> R.raw.illaoi
//            "irelia" -> R.raw.irelia
//            "ivern" -> R.raw.ivern
//            "janna" -> R.raw.janna
//            "jarvan iv" -> R.raw.jarvan_iv
            "jax" -> R.raw.jax
//            "jayce" -> R.raw.jayce
//            "jhin" -> R.raw.jhin
//            "jinx" -> R.raw.jinx
//            "kaisa" -> R.raw.kaisa
//            "kalista" -> R.raw.kalista
//            "karma" -> R.raw.karma
//            "karthus" -> R.raw.karthus
//            "kassadin" -> R.raw.kassadin
//            "katarina" -> R.raw.katarina
//            "kayle" -> R.raw.kayle
//            "kayn" -> R.raw.kayn
//            "kennen" -> R.raw.kennen
//            "kha'zix" -> R.raw.khaxix
//            "kindred" -> R.raw.kindred
//            "kled" -> R.raw.kled
//            "kog'maw" -> R.raw.kogmaw
//            "leblanc" -> R.raw.leblanc
//            "lee sin" -> R.raw.leesin
//            "leona" -> R.raw.leona
//            "lillia" -> R.raw.lillia
//            "lissandra" -> R.raw.lissandra
//            "lucian" -> R.raw.lucian
//            "lulu" -> R.raw.lulu
//            "lux" -> R.raw.lux
//            "malphite" -> R.raw.malphite
//            "malzahar" -> R.raw.malzahar
//            "maokai" -> R.raw.maokai
//            "master yi" -> R.raw.masteryi
//            "miss fortune" -> R.raw.missfortune
//            "mordekaiser" -> R.raw.mordekaiser
//            "morgana" -> R.raw.morgana
//            "nami" -> R.raw.nami
//            "nasus" -> R.raw.nasus
//            "nautilus" -> R.raw.nautilus
//            "neeko" -> R.raw.neeko
//            "nidalee" -> R.raw.nidalee
//            "nocturne" -> R.raw.nocturne
//            "nunu & willump" -> R.raw.nunu_willump
//            "olaf" -> R.raw.olaf
//            "orianna" -> R.raw.orianna
//            "ornn" -> R.raw.ornn
//            "pantheon" -> R.raw.pantheon
//            "poppy" -> R.raw.poppy
//            "pyke" -> R.raw.pyke
//            "qiyana" -> R.raw.qiyana
//            "quinn" -> R.raw.quinn
//            "rakan" -> R.raw.rakan
//            "rammus" -> R.raw.rammus
//            "rek'sai" -> R.raw.reksai
//            "rell" -> R.raw.rell
//            "renekton" -> R.raw.renekton
//            "rengar" -> R.raw.rengar
//            "riven" -> R.raw.riven
//            "rumble" -> R.raw.rumble
//            "ryze" -> R.raw.ryze
//            "samira" -> R.raw.samira
//            "sejuani" -> R.raw.sejuani
//            "senna" -> R.raw.senna
//            "seraphine" -> R.raw.seraphine
//            "sett" -> R.raw.sett
//            "shaco" -> R.raw.shaco
//            "shen" -> R.raw.shen
//            "shyvana" -> R.raw.shyvana
//            "singed" -> R.raw.singed
//            "sion" -> R.raw.sion
//            "sivir" -> R.raw.sivir
//            "skarner" -> R.raw.skarner
//            "sona" -> R.raw.sona
//            "soraka" -> R.raw.soraka
//            "swain" -> R.raw.swain
//            "sylas" -> R.raw.sylas
//            "syndra" -> R.raw.syndra
//            "tahm kench" -> R.raw.tahmkench
//            "taliyah" -> R.raw.taliyah
//            "talon" -> R.raw.talon
//            "taric" -> R.raw.taric
//            "teemo" -> R.raw.teemo
//            "thresh" -> R.raw.thresh
//            "tristana" -> R.raw.tristana
//            "trundle" -> R.raw.trundle
//            "tryndamere" -> R.raw.tryndamere
//            "twisted fate" -> R.raw.twistedfate
//            "twitch" -> R.raw.twitch
//            "udyr" -> R.raw.udyr
//            "urgot" -> R.raw.urgot
//            "varus" -> R.raw.varus
//            "vayne" -> R.raw.vayne
//            "veigar" -> R.raw.veigar
//            "vel'koz" -> R.raw.velkoz
//            "vi" -> R.raw.vi
//            "viego" -> R.raw.viego
//            "viktor" -> R.raw.viktor
//            "vladimir" -> R.raw.vladimir
//            "volibear" -> R.raw.volibear
//            "warwick" -> R.raw.warwick
//            "wukong" -> R.raw.wukong
//            "xayah" -> R.raw.xayah
//            "xerath" -> R.raw.xerath
//            "xin zhao" -> R.raw.xinzhao
//            "yasuo" -> R.raw.yasuo
//            "yone" -> R.raw.yone
//            "yorick" -> R.raw.yorick
//            "yuumi" -> R.raw.yuumi
//            "zac" -> R.raw.zac
//            "zed" -> R.raw.zed
//            "zeri" -> R.raw.zeri
//            "ziggs" -> R.raw.ziggs
//            "zilean" -> R.raw.zilean
//            "zoe" -> R.raw.zoe
//            "zyra" -> R.raw.zyra
            else -> 0
        }
    }

    fun playChampionSound(context: Context, championName: String): MediaPlayer? {
        val soundResId = getSoundResId(championName)
        return if (soundResId != 0) {
            MediaPlayer.create(context, soundResId).apply {
                start()
            }
        } else {
            null
        }
    }
}
