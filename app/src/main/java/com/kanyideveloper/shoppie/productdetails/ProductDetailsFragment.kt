package com.kanyideveloper.shoppie.productdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        setHasOptionsMenu(true)

        val application = requireNotNull(activity).application
        val product = ProductDetailsFragmentArgs.fromBundle(requireArguments()).productDetails

        val viewModelFactory = ProductDetailsViewModelFactory(product, application)
        productDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(ProductDetailsViewModel::class.java)

        productDetailsViewModel.productDetails.observe(viewLifecycleOwner, Observer { product ->
            binding.textViewProdName.text = product.productName
            binding.textViewProdPrice.text = "Ksh.${product.productPrice}"
            binding.textViewProdDescr.text = product.productDescription
            Glide.with(binding.prodImage)
                .load(product.productImageUrl)
                .into(binding.prodImage)
        })

        binding.addToCart.setOnClickListener {
            productDetailsViewModel.addItemToCart(product)
        }

        productDetailsViewModel.addedToCart.observe(viewLifecycleOwner, Observer { added ->
            if (added) {
                Toast.makeText(requireContext(), "Item added to the cart", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        productDetailsViewModel.cartStatus.observe(viewLifecycleOwner, Observer { cartStatus ->
            if (!cartStatus) {
                Toast.makeText(requireContext(), "Not Empty", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.prod_menu,menu)
    }
}