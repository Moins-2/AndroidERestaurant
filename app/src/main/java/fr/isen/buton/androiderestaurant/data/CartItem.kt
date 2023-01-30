package fr.isen.buton.androiderestaurant.data

import fr.isen.buton.androiderestaurant.MenuItem

data class CartItem(
    val plat: MenuItem,
    var quantity: Int
)
