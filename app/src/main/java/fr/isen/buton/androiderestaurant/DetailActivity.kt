package fr.isen.buton.androiderestaurant

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import fr.isen.buton.androiderestaurant.data.CartList
import fr.isen.buton.androiderestaurant.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var cart: CartList
    private lateinit var plat: MenuItem
   // private val saveFile: String = "cart.json"

    private lateinit var cartService: CartService
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as CartService.LocalBinder
            cartService = binder.getService()
            mBound = true

            refreshCart()
            refreshQuantity()
            Log.i("Detail Activity", "Service connected")
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService
        Intent(this, CartService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        // Init
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //

        // Get current infos
        plat = Gson().fromJson(intent.getStringExtra("plat"), MenuItem::class.java)
        refreshCart()

        // Set Data
        binding.image.adapter = ViewPagerAdapter(plat.images)
        binding.name.text = plat.name_fr
        binding.ingredientsList.text = plat.ingredients.joinToString(", ") { it.name_fr }
        refreshQuantity()


        // Set buttons actions
        binding.more.setOnClickListener{
            addToCart()
            refreshQuantity()
        }
        binding.less.setOnClickListener{
            removeFromCart()
            refreshQuantity()
        }
        binding.retour.setOnClickListener { finish() }

        binding.cartMin.button.setOnClickListener {
            val i = Intent(this@DetailActivity, CartActivity::class.java)
            startActivity(i)
            finish()
        }
    }
    private fun refreshQuantity(){
        var res = if (mBound) cartService.itemQuantity(plat) else 0
        binding.quantity.text = res.toString()
    }

    private fun refreshCart(){
        val priceTot = if (mBound) cartService.price else 0.0
        val qtt = if (mBound) cartService.quantity else 0

        binding.cartMin.button.text = "Panier: ${priceTot}€ (${qtt})"
    }

    private fun addToCart(){
        cartService.addItem(plat)
        refreshCart()
    }
    private fun removeFromCart(){
        cartService.removeItem(plat)
        refreshCart()
    }


}