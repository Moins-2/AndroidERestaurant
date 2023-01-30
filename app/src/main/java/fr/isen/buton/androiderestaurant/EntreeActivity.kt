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
import com.google.gson.Gson
import fr.isen.buton.androiderestaurant.databinding.ActivityEntreeBinding

class EntreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntreeBinding
    private lateinit var cartService: CartService
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as CartService.LocalBinder
            cartService = binder.getService()
            mBound = true

            Log.i("Detail Activity", "Service connected")
            refreshCart()
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
        binding = ActivityEntreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gson = Gson()
        val menu: MenuData = gson.fromJson(intent.getStringExtra("menu"), MenuData::class.java)
        binding.textView5.text = menu.name_fr

        val toItem = fun(plat: MenuItem) {
            val i = Intent(this@EntreeActivity, DetailActivity::class.java)
            i.putExtra("plat", gson.toJson(plat))
            startActivity(i)
        }
        val recyView = binding.recyView
        recyView.adapter = PlatsAdapter(menu.items, toItem)
        recyView.layoutManager = LinearLayoutManager(this)

        binding.retour.setOnClickListener { finish() }

        binding.cartMin.button.setOnClickListener {
            val i = Intent(this@EntreeActivity, CartActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshCart()
    }

    private fun refreshCart(){
        val priceTot = if (mBound) cartService.price else 0.0
        val qtt = if (mBound) cartService.quantity else 0

        binding.cartMin.button.text = "Panier: ${priceTot}€ (${qtt})"
    }

}


