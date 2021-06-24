package com.kanyideveloper.shoppie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kanyideveloper.shoppie.databinding.CartRowBinding
import com.kanyideveloper.shoppie.model.Product

class CartAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Product, CartAdapter.MyViewHolder>(MyDiffUtil) {

    object MyDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

    }

    inner class MyViewHolder(private val binding: CartRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product?) {
            Glide.with(binding.cartImageImageView)
                .load(item?.itemImage)
                .into(binding.cartImageImageView)

            binding.cartItemNameTextView.text = item?.itemName
            binding.cartItemPricetextView.text = item?.itemPrice.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CartRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }

    override fun onBindViewHolder(holder: CartAdapter.MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}