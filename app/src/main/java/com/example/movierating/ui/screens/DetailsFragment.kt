package com.example.movierating.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.movierating.R
import com.example.movierating.data_source.models.MovieDetails
import com.example.movierating.data_source.utils.Others
import com.example.movierating.databinding.FragmentDetailsBinding
import com.example.movierating.ui.presentation.adapters.MainScreenViewAdapter
import com.example.movierating.ui.presentation.SharedViewModel
import com.example.movierating.ui.presentation.adapters.CastAdapter
import com.example.movierating.ui.presentation.adapters.SimilarMoviesAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
@SuppressLint("SetTextI18n")
class DetailsFragment : Fragment() {

    //Argument we got from navigation
    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel: SharedViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //View Binding to inflate view
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Calling the function to get details
        viewModel.getMovieDetails(args.id)

        //Making changes on change of state
        lifecycleScope.launchWhenCreated {
            viewModel.movieDetails.collectLatest { state ->
                when {
                    state.isLoading -> {
                        //While data is loading
                        binding.detailsScreen.visibility = View.INVISIBLE
                        binding.detailsTopbar.visibility = View.INVISIBLE
                        binding.loadingBar.visibility = View.VISIBLE
                    }

                    //If state catches error
                    state.errorMessage.isNotBlank() -> {
                        binding.detailsScreen.visibility = View.VISIBLE
                        binding.detailsTopbar.visibility = View.VISIBLE
                        binding.loadingBar.visibility = View.INVISIBLE

                        findNavController().navigateUp()
                        //Showing snack bar on error
                        Snackbar.make(
                            binding.root,
                            state.errorMessage,
                            Snackbar.LENGTH_LONG
                        ).setAction("Close") {
                            //closes snack bar
                        }.show()
                    }
                    else -> {
                        //Data collected successfully and showing it on recycler view
                        setupUi(state.result)
                        binding.detailsScreen.visibility = View.VISIBLE
                        binding.detailsTopbar.visibility = View.VISIBLE
                        binding.loadingBar.visibility = View.INVISIBLE

                    }
                }
            }
        }

    }


    //Function to setup all ui elements
    private fun setupUi(details: MovieDetails) {

        //Loading image poster
        Glide.with(requireContext())
            .load(details.thumbnail)
            .placeholder(Others.getShimmer())
            .into(binding.ivMovieThumbnail)

        //Setting up details
        binding.tvDuration.text =
            "${details.releaseYear} \u2022 ${details.contentRating?: "Not rated"} \u2022 ${details.duration ?: "N/A"}"
        binding.tvMovieName.text = details.movieName
        binding.tvIMDbRating.text = details.imdbRating

        //Adding chips for genre
        details.genre?.split(",")?.forEach {
            binding.genreChipGroup.addView(Others.createChip(requireContext(), it))
        }

        //Setting up text views
        binding.tvPlot.text = details.plot
        binding.tvAwards.text = if (details.awards.isNullOrEmpty()) "N/A" else details.awards
        binding.tvStars.text = if (details.stars.isNullOrEmpty()) "N/A" else details.stars
        binding.tvDirector.text = if (details.director.isNullOrEmpty()) "N/A" else details.director

        //Setting up cast recycler view
        val castAdapter = CastAdapter()
        castAdapter.differ.submitList(details.casts)


        binding.rvCasts.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
                adapter = castAdapter
        }


        //Setting up similar movies recycler view
        val similarAdapter = SimilarMoviesAdapter()
        similarAdapter.differ.submitList(details.similars)

        binding.rvSimilar.apply {
            layoutManager =
                LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = similarAdapter
        }

        //Navigation on clicking on similar movie
        similarAdapter.setOnItemClicked {
            val bundle = Bundle().apply {
                putString("id",it.id)
            }
            findNavController().navigate(R.id.action_detailsFragment_self,bundle)

        }
        binding.llMoreLikeThis.visibility = if(details.similars.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE

    }


}