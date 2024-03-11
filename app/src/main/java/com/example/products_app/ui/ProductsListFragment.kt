package com.example.products_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.products_app.R
import com.example.products_app.data.model.Product
import com.example.products_app.databinding.FragmentProductsListBinding
import com.example.products_app.ui.adapters.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListFragment : Fragment(), MenuProvider {

    private var _binding: FragmentProductsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductsAdapter

    private val viewModel by viewModels<ProductsListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this)
        setupView()
        setupObservers()
    }

    private fun setupView() {
        adapter = ProductsAdapter(::onProductClick)
        binding.apply {
            rvProducts.layoutManager = LinearLayoutManager(requireContext())
            rvProducts.adapter = adapter
            pullToRefresh.setOnRefreshListener {
                adapter.refresh()
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productsFlow.collectLatest {
                adapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        binding.pullToRefresh.isRefreshing = true
                    }
                    is LoadState.Error -> {
                        binding.pullToRefresh.isRefreshing = false
                        Toast.makeText(requireContext(), (it.refresh as LoadState.Error).error.message, Toast.LENGTH_SHORT).show()
                    }
                    is LoadState.NotLoading -> {
                        binding.pullToRefresh.isRefreshing = false
                    }
                }
            }
        }
    }

    private fun onProductClick(product: Product) {
        val action = ProductsListFragmentDirections.actionProductsListFragmentToSecondFragment(product, product.title)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        searchItem.isVisible = true
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearch(newText.toString())
                return true
            }
        })
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
}
