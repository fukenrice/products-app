package com.example.products_app.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.products_app.databinding.ImageLayoutBinding
import com.squareup.picasso.Picasso

// Взято с https://stackoverflow.com/a/29991829
class ImagesAdapter(mContext: Context, private val images: List<String>) :
    PagerAdapter() {
    private val mLayoutInflater: LayoutInflater =
        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === (`object` as ImageLayoutBinding).root
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val itemView = ImageLayoutBinding.inflate(mLayoutInflater, container, false)
        Picasso.get().load(images[position])
            .fit()
            .into(itemView.imageView)
        container.addView(itemView.root)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView((`object` as ImageLayoutBinding).root)
    }
}