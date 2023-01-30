package fr.isen.buton.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PlatsAdapter (private var plats: List<MenuItem>, val onClickListener: (plats: MenuItem) -> Unit) : RecyclerView.Adapter<PlatsAdapter.CategoryViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_category_cell, parent, false)

            return CategoryViewHolder(view)
        }

        override fun getItemCount(): Int { return plats.size }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val plat = plats[position]
            holder.name.text = plat.name_fr
            if (plat.images[0] != "") Picasso.get().load(plat.images[0]).into(holder.image)

            holder.name.setOnClickListener { onClickListener(plat) }
        }

        // Holds the views for adding it to image and text
        class CategoryViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val name: TextView = itemView.findViewById(R.id.name)
            val image: ImageView = itemView.findViewById(R.id.image)
        }

}