package fr.isen.buton.androiderestaurant

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import fr.isen.buton.androiderestaurant.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val platString: String? = intent.getStringExtra("plat")
        val gson = Gson()
        val plat: MenuItem = gson.fromJson(platString, MenuItem::class.java)

        val adapter = ViewPagerAdapter(plat.images)
        val viewPager = binding.image
        viewPager.adapter = adapter

        binding.name.text = plat.name_fr
        var ListIng = String()
        plat.ingredients.forEach(){
            ListIng = ListIng + ", " + it.name_fr
        }
        ListIng = ListIng.substring(1)
        var prix = binding.prixList.text
        var listIngredient = binding.ingredientsList.text
        listIngredient = ListIng
        prix = "Prix: " + plat.prices[0].price + "€"



        val retour = binding.retour
        retour.setOnClickListener {
            // your code to perform when the user clicks on the button
            Toast.makeText(this, "Retour", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}