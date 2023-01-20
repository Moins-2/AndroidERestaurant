package fr.isen.buton.androiderestaurant

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso


class ViewPagerAdapter (private val images: Array<String>):RecyclerView.Adapter<ViewPagerAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_cell, parent, false)

        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val itemsViewModel = images[position]

        if (itemsViewModel!= "")
        {
            Picasso.get().load(itemsViewModel).into(holder.imageViewVP)
        }
    }


    override fun getItemCount(): Int {
        return images.size
    }

    class DetailViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageViewVP: ImageView = itemView.findViewById(R.id.image)
    }
}