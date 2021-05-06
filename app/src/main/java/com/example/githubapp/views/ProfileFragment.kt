package com.example.githubapp.views

import ProfileDetailsQuery
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import coil.load
import coil.transform.CircleCropTransformation
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.example.githubapp.R
import com.example.githubapp.adapters.RepoListAdapter
import com.example.githubapp.appollo.apolloClient
import com.example.githubapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repos = mutableListOf<ProfileDetailsQuery.Repositories?>()

        lifecycleScope.launchWhenResumed {
            val response = apolloClient(requireContext())
                .query(ProfileDetailsQuery()).responseFetcher(ApolloResponseFetchers.CACHE_FIRST).toDeferred().await()

            binding.imageViewProfile.load(response.data?.user?.avatarUrl.toString()){
                crossfade(true)
                placeholder(R.drawable.circle_background)
                transformations(CircleCropTransformation())
            }
            binding.txtViewName.text = response.data?.user?.name
            binding.txtViewEmail.text = response.data?.user?.email
            binding.txtViewFollowers.text = "${response.data?.user?.followers?.totalCount.toString()} followers"
            binding.txtViewFollowing.text = "${response.data?.user?.following?.totalCount.toString()} following"

            val newLaunches = response.data?.user?.repositories
            repos.add(newLaunches)
            val adapter =
                RepoListAdapter(repos)


            binding.recycleViewPinnedItems.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleViewPinnedItems.adapter = adapter

            binding.recycleViewTopRepo.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            binding.recycleViewTopRepo.adapter = adapter

            binding.recycleViewStarredRepo.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            binding.recycleViewStarredRepo.adapter = adapter


        }
    }

    private fun pullToRefresh(){
        Toast.makeText(context,"pulled",Toast.LENGTH_LONG).show()
    }

}