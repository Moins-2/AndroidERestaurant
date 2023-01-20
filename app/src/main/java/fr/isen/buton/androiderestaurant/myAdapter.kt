package fr.isen.buton.androiderestaurant

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class myAdapter (private var plats: MenuData, val onClickListener: (plats: MenuItem) -> Unit) : RecyclerView.Adapter<myAdapter.CategoryViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_cell, parent, false)

            return CategoryViewHolder(view)
        }

        override fun getItemCount(): Int {
            return plats.items.size
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val plat = plats.items[position]
            holder.name.text = plat.name_fr

            holder.name.setOnClickListener {
                onClickListener(plat)
            }


        }

        // Holds the views for adding it to image and text
        class CategoryViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val name = itemView.findViewById<TextView>(R.id.name)

        }

}