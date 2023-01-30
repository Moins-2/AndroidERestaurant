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
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.buton.androiderestaurant.databinding.ActivityHomeBinding
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var mService: CartService
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as CartService.LocalBinder
            mService = binder.getService()
            mService.setContext(this@HomeActivity)
            mBound = true
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
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getMenu()
    }

    private  fun getMenu(){
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, "http://test.api.catering.bluecodegames.com/menu", jsonObject,
            Response.Listener { response -> updateData(Gson().fromJson(response.toString(), MenuResponse::class.java)) },
            Response.ErrorListener { error -> Log.e("api", "Error: ${error.message}") }
        )

        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    private fun updateData(data: MenuResponse){
        val toCategory = fun(menu: MenuData) {
            val i = Intent(this@HomeActivity, EntreeActivity::class.java)
            i.putExtra("menu",Gson().toJson(menu))
            startActivity(i)
        }
        val recyView = binding.listMenu
        recyView.adapter = MenuAdapter(data, toCategory)
        recyView.layoutManager = LinearLayoutManager(this)
    }

}


