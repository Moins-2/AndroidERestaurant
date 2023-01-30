package fr.isen.buton.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.buton.androiderestaurant.databinding.ActivityEntreeBinding

class EntreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntreeBinding

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

    }
}


