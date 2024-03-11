package com.example.products_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.products_app.data.model.Product
import com.example.products_app.databinding.ProductListItemBinding
import com.squareup.picasso.Picasso

class ProductsAdapter(private val onItemClick: (Product) -> Unit) :
    PagingDataAdapter<Product, ProductsAdapter.ProductsViewHolder>(ProductsDiffCallback()) {

    class ProductsViewHolder(val binding: ProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = getItem(position) ?: return
        holder.binding.apply {
            root.setOnClickListener { onItemClick(product) }
            Picasso.get().load(product.thumbnail).fit()
                .into(ivProductPic)
            tvProductName.text = product.title
            tvProductDesc.text =
                if (product.description.length <= 30) {
                    product.description
                } else {
                    product.description.substring(0, 29) + "..."
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductsViewHolder(ProductListItemBinding.inflate(inflater, parent,false))
    }
}

class ProductsDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}