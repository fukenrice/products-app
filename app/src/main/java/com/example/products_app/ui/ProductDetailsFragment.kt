package com.example.products_app.ui

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.example.products_app.R
import com.example.products_app.databinding.FragmentProductDetailsBinding
import com.example.products_app.ui.adapters.ImagesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private var _binding: FragmentProductDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.apply {
            viewpagerImages.adapter = ImagesAdapter(requireContext(), args.product.images)
            tvDesc.text = args.product.description
            tvInStock.text = getString(R.string.in_stock, args.product.stock.toString())
            tvOldPrice.text = getString(R.string.price, String.format("%1$,.2f", args.product.price.toDouble()) )

            if (args.product.discountPercentage != null) {
                tvOldPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvNewPrice.text = getString(R.string.price, String.format("%1$,.2f", (args.product.price * (1 - args.product.discountPercentage!! / 100)).toDouble()) )
                tvNewPrice.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}