package fr.isen.buton.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.buton.androiderestaurant.data.CartList

class CartMinActivity : AppCompatActivity() {
    private lateinit var cart: CartList
    private val saveFile: String = "cart.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_min)

     //   cart = getCart()


    }

    public fun getQuantity(item: MenuItem): Int {
        var index = cart.list.map { it.plat.name_fr }.indexOf(item.name_fr)
        return if (index < 0)  0 else cart.list[index].quantity
    }

    public fun refreshCart(){
        // TODO
    }
    public fun addToCart(){
        // TODO
    }
    public fun removeFromCart(){
        // TODO
    }
}