package fr.isen.buton.androiderestaurant

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.buton.androiderestaurant.data.CartItem
import fr.isen.buton.androiderestaurant.databinding.ActivityCategoryCellBinding
import fr.isen.buton.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.buton.androiderestaurant.databinding.ActivityEntreeBinding

class CartAdapter (private var cartList: List<CartItem>, val onClickListener: (item: MenuItem) -> Unit) : RecyclerView.Adapter<CartAdapter.CategoryViewHolder>() {

    public fun updateCart(cartList: List<CartItem>) {
        this.cartList = cartList
        Log.i("Cart Adapter", "Cart updated")
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_cart_cell, parent, false)

        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int { return cartList.size }

    override fun onBindViewHolder(holder: CartAdapter.CategoryViewHolder, position: Int) {
        val plat = cartList[position].plat
        holder.cell.name.text = plat.name_fr
        if (plat.images[0] != "") Picasso.get().load(plat.images[0]).into(holder.cell.image)

        holder.cell.name.setOnClickListener { onClickListener(plat) }
        holder.priceOne.text = plat.prices[0].price
        holder.priceTot.text ="Tot: "+(plat.prices[0].price.toDouble() * cartList[position].quantity).toString()+"€"
        holder.quantity.text = cartList[position].quantity.toString()
    }

    // Holds the views for adding it to image and text
    class CategoryViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cell: ActivityCategoryCellBinding = ActivityCategoryCellBinding.bind(ItemView)
        val priceTot: TextView = itemView.findViewById(R.id.priceTot)
        val priceOne: TextView = itemView.findViewById(R.id.priceOne)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
    }



}