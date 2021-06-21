package com.kanyideveloper.shoppie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kanyideveloper.shoppie.databinding.ProductRowBinding
import com.kanyideveloper.shoppie.model.Product

class ProductAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Product, ProductAdapter.MyViewHolder>(MyDiffUtil) {

    object MyDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

    }

    inner class MyViewHolder(private val binding: ProductRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product?) {
            Glide.with(binding.productPicImageView)
                .load(item?.productImageUrl)
                .into(binding.productPicImageView)

            binding.productNameTextView.text = item?.productName
            binding.productPriceTextView.text = item?.productPrice.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ProductRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(product)
        }
    }

    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }
}