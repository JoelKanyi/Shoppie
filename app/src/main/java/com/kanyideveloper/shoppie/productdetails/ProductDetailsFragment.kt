package com.kanyideveloper.shoppie.productdetails

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kanyideveloper.shoppie.R
import com.kanyideveloper.shoppie.databinding.FragmentProductDetailsBinding


class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var productDetailsViewModel: ProductDetailsViewModel

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

        })

        return view
    }
}