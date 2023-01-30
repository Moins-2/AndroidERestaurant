package fr.isen.buton.androiderestaurant

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.buton.androiderestaurant.*
import fr.isen.buton.androiderestaurant.data.CartItem
import fr.isen.buton.androiderestaurant.data.CartList
import fr.isen.buton.androiderestaurant.databinding.ActivityCartBinding
import java.io.File

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val saveFile: String = "cart.json"
    private lateinit var cart: CartList

    private lateinit var recyView: RecyclerView

    private lateinit var cartService: CartService
    private var mBound: Boolean = false

    private var adapter: CartAdapter? = null
    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as CartService.LocalBinder
            cartService = binder.getService()
            mBound = true

            Log.i("Cart Activity", "Service connected")

            adapter?.updateCart(cartService.list.list)
        //    CartAdapter(listOf(), toItem)
            binding.total.text = "Prix: "+cartService.price.toString()+" €"


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
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toItem = fun(plat: MenuItem) {
            val i = Intent(this@CartActivity, DetailActivity::class.java)
            i.putExtra("plat", Gson().toJson(plat))

            startActivity(i)
            finish()

        }
        var addOne = fun(plat: MenuItem) {
            cartService.addItem(plat)
        }
        var removeOne = fun(plat: MenuItem) {
            cartService.removeItem(plat)
        }
        recyView = binding.basketList
        recyView.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter(listOf(), toItem,add, remove, update)
        recyView.adapter = adapter



    }

   var add = fun(item: MenuItem) {
        cartService.addItem(item)
        Log.i("Cart Activity", "Add item")
    }
    var remove= fun(item: MenuItem) {
        cartService.removeItem(item)
    }
    var update = fun() {
        adapter?.updateCart(cartService.list.list)
        binding.total.text = "Prix: "+cartService.price.toString()+" €"
    }

}