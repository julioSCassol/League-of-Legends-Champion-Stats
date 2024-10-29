package com.example.league_of_legends_application.utils

import androidx.compose.runtime.Composable
import com.example.league_of_legends_application.model.Item

@Composable
fun formatDescription(item: Item): String {
    return item.description
        .replace("\u003Cstats\u003E", "")
        .replace("\u003Cmana\u003E", "")
        .replace("\u003C/mana\u003E", "")
        .replace("\u003C/stats\u003E", "")
        .replace("\u003Cunique\u003E", "")
        .replace("\u003C/unique\u003E", "")
        .replace("\u003Cbr\u003E", "\n")
        .replace("\u003Cfont color='#99BBBB'\u003E", "")
        .replace("\u003C/a\u003E\u003C/font\u003E", "")
        .replace("\u003CgroupLimit\u003E", "Limite de Grupo: ")
        .replace("\u003C/groupLimit\u003E", "")
        .replace("\u003Ca href='SpecialJungleExperience'\u003E", "Experiência Bônus Especial")
        .replace("</a>", "")
        .replace("\u003Cactive\u003E", "")
        .replace("\u003C/active\u003E", "")
        .replace("\u003Cconsumable\u003E", "Consumível: ")
        .replace("\u003C/consumable\u003E", "")
        .replace("\u003CrecipeOnly\u003E", "Apenas Receita")
        .replace("\u003C/recipeOnly\u003E", "")
        .replace("\u003Cpassive\u003E", "")
        .replace("\u003C/passive\u003E", "")
        .replace("\u003Cactive\u003E", "")
        .replace("\u003C/active\u003E", "")
}