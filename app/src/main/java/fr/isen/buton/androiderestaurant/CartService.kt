package fr.isen.buton.androiderestaurant

import android.app.Service
import android.content.ContextWrapper
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.google.gson.Gson
import fr.isen.buton.androiderestaurant.data.CartItem
import fr.isen.buton.androiderestaurant.data.CartList
import java.io.File

class CartService : Service() {
    private val saveFile: String = "cart.json"
    private var cart: CartList = CartList(mutableListOf(), 0,0.0)
    private lateinit var file: File
    private val binder = LocalBinder()

    val list: CartList
        get() = cart
    val setContext: (ContextWrapper) -> Unit = { contextWrapper ->
        file = File(contextWrapper.cacheDir.absolutePath, saveFile)
        Log.i("Cart Service", "Context set")
        getCart()
    }
    val quantity: Int
        get() = cart.list.map { it.quantity }.sum()
    val price: Double
        get() = cart.list.map { it.plat.prices.first().price.toDouble() * it.quantity }.sum()
    val addItem: (MenuItem) -> Unit = { addToCart(it) }
    val removeItem: (MenuItem) -> Unit = { removeFromCart(it) }
    val clearCart: () -> Unit = { clearCart() }
    val itemQuantity: (MenuItem) -> Int = { itemQuantity(it) }

    inner class LocalBinder : Binder() {
        fun getService(): CartService = this@CartService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private fun getCart(): CartList {
        var savedJson = if (file.exists()) file.readText()
        else // If file doesn't exist, create it
            return CartList(mutableListOf(), 0, 0.0)

        return Gson().fromJson(savedJson, CartList::class.java)
    }

    private fun saveCart() {
        val file = File(filesDir, saveFile)
        file.writeText(Gson().toJson(cart))
    }

    private fun addToCart(plat: MenuItem) {

        var index = cart.list.map { it.plat.name_fr }.indexOf(plat.name_fr)
        if (index < 0)  cart.list.add(CartItem(plat, 1))
        else cart.list[index].quantity += 1

        cart.price += plat.prices[0].price.toDouble()
        cart.quantity ++
        saveCart()
    }

    private fun removeFromCart(plat: MenuItem) {
        var index = cart.list.map { it.plat.name_fr }.indexOf(plat.name_fr)
        if (index < 0)  return
        else if (cart.list[index].quantity > 1) cart.list[index].quantity --
        else cart.list.removeAt(index)

        cart.price -= plat.prices[0].price.toDouble()
        cart.quantity--

        saveCart()
    }

    private fun clearCart() {
        cart = CartList(mutableListOf(), 0, 0.0)
        saveCart()
    }

    private fun itemQuantity(plat: MenuItem): Int {
        var index = cart.list.map { it.plat.name_fr }.indexOf(plat.name_fr)
        if (index < 0)  return 0
        else return cart.list[index].quantity
    }

}