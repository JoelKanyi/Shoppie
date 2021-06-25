package com.kanyideveloper.shoppie.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.shoppie.R
import com.kanyideveloper.shoppie.adapter.ProductAdapter
import com.kanyideveloper.shoppie.databinding.FragmentProductBinding

private const val TAG = "ProductFragment"

class ProductFragment : Fragment(), Toolbar.OnMenuItemClickListener {

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

        binding.productToolbar.setOnMenuItemClickListener(this)

        productViewModel.products.observe(viewLifecycleOwner, Observer { productsList ->
            Log.d(TAG, "onCreateView: $productsList")
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

        productViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                Status.DONE -> {
                    binding.progressBar.isVisible = false
                }
                Status.ERROR -> {
                    binding.textViewError.isVisible = true
                    binding.progressBar.isVisible = false
                }
            }
        })

        productViewModel.cartStatus.observe(viewLifecycleOwner, Observer { cartStatus ->
            if (!cartStatus) {
                binding.productToolbar.inflateMenu(R.menu.prod_menu)
            }
        })


        productViewModel.navigateToCart.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(R.id.action_productFragment_to_cartFragment)
                productViewModel.navigatingToCartCompleted()
            }
        })
        return view
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.prod_cart_menu) {
            productViewModel.navigateToCart()
            true
        } else {
            false
        }
    }
}