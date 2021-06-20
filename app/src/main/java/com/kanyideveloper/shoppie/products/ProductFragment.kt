package com.kanyideveloper.shoppie.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.shoppie.adapter.ProductAdapter
import com.kanyideveloper.shoppie.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private val productViewModel by lazy { ViewModelProvider(this).get(ProductViewModel::class.java) }
    private val productAdapter by lazy {
        ProductAdapter(ProductAdapter.OnClickListener { product ->
            productViewModel.displaySelectedProduct(product)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        val view = binding.root

        productViewModel.products.observe(viewLifecycleOwner, Observer { productsList ->
            productAdapter.submitList(productsList)
            binding.productsRecyclerView.adapter = productAdapter
        })

        productViewModel.navigateToSelectedItem.observe(viewLifecycleOwner, Observer { product ->
            if (product != null) {
                findNavController().navigate(
                    ProductFragmentDirections.actionProductFragmentToProductDetailsFragment(product)
                )
                productViewModel.displayProductDetailsCompleted()
            }
        })

        return view
    }
}