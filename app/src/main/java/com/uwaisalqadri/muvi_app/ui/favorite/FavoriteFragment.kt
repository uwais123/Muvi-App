package com.uwaisalqadri.muvi_app.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.uwaisalqadri.muvi_app.R
import com.uwaisalqadri.muvi_app.databinding.FragmentFavoriteBinding
import com.uwaisalqadri.muvi_app.databinding.IncludeToolbarBinding
import com.uwaisalqadri.muvi_app.ui.detail.DetailActivity
import com.uwaisalqadri.muvi_app.utils.showToast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Uwais Alqadri on April 05, 2021
 */
@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding: FragmentFavoriteBinding by viewBinding()
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    private lateinit var toolbarBinding: IncludeToolbarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarBinding = binding.toolbar

        with(viewModel) {
            getFavoriteMovies()

            favoriteMovieData.observe(viewLifecycleOwner) { favorites ->
                favoriteAdapter.differ.submitList(favorites)
                favoriteAdapter.notifyDataSetChanged()
            }

            messageData.observe(viewLifecycleOwner) {
                context?.showToast(it)
            }
        }

        with(binding.rvFavorites) {
            favoriteAdapter = FavoriteAdapter({ item -> viewModel.removeFavoriteMovie(item) }, {
                startActivity(Intent(context, DetailActivity::class.java).putExtra("movieId", it.id))
            })

            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }
    }
}








