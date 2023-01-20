package fr.isen.buton.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.buton.androiderestaurant.databinding.ActivityHomeBinding
import org.json.JSONObject
import java.util.logging.Logger
import kotlin.math.log

class HomeActivity : AppCompatActivity() {

    val log = Logger.getLogger(HomeActivity::class.java.name)
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getMenu()

    }

    private  fun getMenu(){
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
               // add Gson and log it
                val gson = Gson()
                val menu = gson.fromJson(response.toString(), MenuResponse::class.java)
                updateData(menu)

            },
            Response.ErrorListener { error ->
                Log.e("api", "Error: ${error.message}")
            }
        )
        queue.add(jsonObjectRequest)
    }

    private fun updateData(data: MenuResponse){
        val myFunc = fun(menu: MenuData) {
            val i = Intent(this@HomeActivity, EntreeActivity::class.java)
            val gson = Gson()
            i.putExtra("menu",gson.toJson(menu))
            startActivity(i)

        }
        val adapter = MenuAdapter(data, myFunc)
        val recyView = binding.listMenu
        recyView.adapter = adapter
        recyView.layoutManager = LinearLayoutManager(this)
    }

}


