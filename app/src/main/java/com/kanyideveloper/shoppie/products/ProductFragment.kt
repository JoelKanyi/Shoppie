package com.kanyideveloper.shoppie.products

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.shoppie.R
import com.kanyideveloper.shoppie.adapter.ProductAdapter
import com.kanyideveloper.shoppie.databinding.FragmentProductBinding

private const val TAG = "ProductFragment"

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

        setHasOptionsMenu(true)

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