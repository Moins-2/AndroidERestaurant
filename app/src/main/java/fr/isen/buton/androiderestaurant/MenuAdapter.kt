package fr.isen.buton.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter (private var menus: MenuResponse, val onClickListener: (menu: MenuData) -> Unit) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_cell, parent, false)

        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menus.data.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menus.data[position]
        holder.name.text = menu.name_fr

        holder.name.setOnClickListener {
            onClickListener(menu)
        }
    }

    // Holds the views for adding it to image and text
    class MenuViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name = itemView.findViewById<TextView>(R.id.menu)

    }

}