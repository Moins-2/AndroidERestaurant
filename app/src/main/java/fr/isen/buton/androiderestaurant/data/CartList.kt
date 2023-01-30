package fr.isen.buton.androiderestaurant.data

data class CartList(
    val list: MutableList<CartItem>,
    var quantity: Int,
    var price: Double
)
