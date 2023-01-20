package fr.isen.buton.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.buton.androiderestaurant.databinding.ActivityEntreeBinding
import java.util.logging.Logger

class EntreeActivity : AppCompatActivity() {
    val log = Logger.getLogger(EntreeActivity::class.java.name)
    private lateinit var binding: ActivityEntreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntreeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val menuString: String? = intent.getStringExtra("menu")
        val gson = Gson()
        val menu: MenuData = gson.fromJson(menuString, MenuData::class.java)
        binding.textView5.text = menu.name_fr

        val myFunc = fun(plat: MenuItem) {
            val i = Intent(this@EntreeActivity, DetailActivity::class.java)
            i.putExtra("plat", gson.toJson(plat))

            startActivity(i)

        }
        val adapter = myAdapter(menu, myFunc)
        val recyView = binding.recyView
        recyView.adapter = adapter
        recyView.layoutManager = LinearLayoutManager(this)

        val retour = binding.retour
        retour.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this, "Retour", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}


