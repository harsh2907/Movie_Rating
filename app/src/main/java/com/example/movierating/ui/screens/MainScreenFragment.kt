package com.example.movierating.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movierating.R
import com.example.movierating.databinding.FragmentMainScreenBinding
import com.example.movierating.ui.presentation.SharedViewModel
import com.example.movierating.ui.presentation.adapters.MainScreenViewAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainScreenFragment : Fragment() {
  private lateinit var binding:FragmentMainScreenBinding
  private val viewModel:SharedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //View Binding to inflate view
        binding = FragmentMainScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Setting up adapter
        val searchAdapter = MainScreenViewAdapter()



        //Applying properties to recycler view
        binding.rvSearchResult.apply {
            layoutManager = StaggeredGridLayoutManager(
            StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS,
            StaggeredGridLayoutManager.VERTICAL)
            adapter = searchAdapter
        }


        //Setting up search function
        binding.searchBar.apply {
            setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    clearFocus()
                    query?.let {
                        viewModel.getSearchResults(it)
                        stopAnimation()
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText.isNullOrEmpty()){
                        searchAdapter.differ.submitList(emptyList())
                        binding.llAnimation.visibility = View.VISIBLE
                        binding.animationView.playAnimation()
                    }
                   return true
                }

            })
        }

        //On clicking on search result
        searchAdapter.setOnItemClicked {
                val bundle = Bundle().apply {
                    putString("id",it.id)
                }
                findNavController().navigate(R.id.action_mainScreenFragment_to_detailsFragment,bundle)
        }


        //Making changes in ui according to the state with help of flow
        lifecycleScope.launchWhenCreated {
            viewModel.searchResults.collectLatest {state->
                when {
                    state.isLoading -> {
                        //While data is loading
                        stopAnimation()
                        binding.rvSearchResult.visibility = View.INVISIBLE
                        binding.loadingBar.visibility = View.VISIBLE
                    }
                    state.errorMessage.isNotBlank() -> {
                        //If state catches error
                        if(searchAdapter.differ.currentList.isEmpty()) {
                            setupLottieAnimation()
                        }
                        else
                            stopAnimation()
                        binding.rvSearchResult.visibility = View.VISIBLE
                        binding.loadingBar.visibility = View.INVISIBLE
                        binding.animationView.pauseAnimation()

                        //Showing Snackbar on error
                        Snackbar.make(
                            binding.root,
                            state.errorMessage,
                            Snackbar.LENGTH_LONG
                        ).setAction("Try Again"){
                            viewModel.getSearchResults(binding.searchBar.query.toString())
                        }.show()
                    }
                    state.result.isEmpty()  ->{
                        binding.loadingBar.visibility = View.INVISIBLE
                        setupLottieAnimation()
                    }
                    state.result.isNotEmpty() -> {
                        //Data collected successfully and showing it on recycler view

                        //Stoping animation
                        stopAnimation()

                        binding.rvSearchResult.visibility = View.VISIBLE
                        binding.loadingBar.visibility = View.INVISIBLE


                        //Adding data to adapter
                        searchAdapter.differ.submitList(state.result)

                    }
                }
            }
        }

    }

    //Function to start animation
    private fun setupLottieAnimation(){
        binding.llAnimation.visibility = View.VISIBLE
        binding.animationView.playAnimation()
    }

    //Function to stop animation
    private fun stopAnimation(){
        binding.llAnimation.visibility = View.INVISIBLE
        binding.animationView.pauseAnimation()
    }
}