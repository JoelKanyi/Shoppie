package com.kanyideveloper.shoppie.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kanyideveloper.shoppie.adapter.CartAdapter
import com.kanyideveloper.shoppie.databinding.CartFragmentBinding

class CartFragment : Fragment() {

    private lateinit var binding: CartFragmentBinding
    private val cartAdapter by lazy { CartAdapter() }
    private val viewModel by lazy { ViewModelProvider(this).get(CartViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CartFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            cartAdapter.submitList(products)
            binding.cartRecyclerView.adapter = cartAdapter
        })

        viewModel.cartStatus.observe(viewLifecycleOwner, Observer { isEmpty ->
            binding.emptyCart.isVisible = isEmpty
        })

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                Status.LOADING -> {
                    binding.cartProgressBar.isVisible = true
                }
                Status.DONE -> {
                    binding.cartProgressBar.isVisible = false
                }
                Status.ERROR -> {
                    binding.cartProgressBar.isVisible = false
                }
            }
        })

        return view
    }
}