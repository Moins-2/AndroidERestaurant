package fr.isen.buton.androiderestaurant

import android.view.ViewDebug.FlagToString

data class MenuResponse(val data: List<MenuData>)

data class MenuData(
    val name_fr: String,
    val name_en: String,
    val items: List<MenuItem>
)

data class MenuItem(
    val id: String,
    val name_fr: String,
    val name_en: String,
    val id_category: String,
    val categ_name_fr: String,
    val categ_name_en: String,
    val images: Array<String>,
    val ingredients: List<Ingredient>,
    val prices: List<Price>
)

data class Ingredient(
    val id: String,
    val id_shop: String,
    val name_fr: String,
    val name_en: String,
    val create_date: String,
    val update_date: String,
    val id_pizza: String,
)

data class Price(
    val id: String,
    val id_pizza: String,
    val id_size: String,
    val price: String,
    val create_date: String,
    val update_date: String,
    val size: String
)