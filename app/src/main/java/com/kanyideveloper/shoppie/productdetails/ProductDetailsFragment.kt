package com.kanyideveloper.shoppie.productdetails

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kanyideveloper.shoppie.R
import com.kanyideveloper.shoppie.databinding.FragmentProductDetailsBinding


@Suppress("NAME_SHADOWING")
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var productDetailsViewModel: ProductDetailsViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
       val view = binding.root
        val application = requireNotNull(activity).application
        val product = ProductDetailsFragmentArgs.fromBundle(requireArguments()).productDetails

        val viewModelFactory = ProductDetailsViewModelFactory(product, application)
        productDetailsViewModel = ViewModelProvider(this, viewModelFactory).get(ProductDetailsViewModel::class.java)

        productDetailsViewModel.productDetails.observe(viewLifecycleOwner, Observer { product ->
                binding.textViewProdName.text = product.productName
                binding.textViewProdPrice.text = "Ksh.${product.productPrice}"
                binding.textViewProdDescr.text  = product.productDescription
                Glide.with(binding.prodImage)
                    .load(product.productImageUrl)
                    .into(binding.prodImage)
        })

        binding.addToCart.setOnClickListener {

        }

        return view
    }
}