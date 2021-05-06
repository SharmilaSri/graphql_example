package com.example.githubapp.views

import ProfileDetailsQuery
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.apollographql.apollo.coroutines.toDeferred
import com.example.rocketreserver.R
import com.example.githubapp.adapters.RepoListAdapter
import com.example.githubapp.appollo.apolloClient
import com.example.rocketreserver.databinding.FragmentProfileBinding

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
            val response = apolloClient(
                requireContext()
            )
                .query(ProfileDetailsQuery()).toDeferred().await()

            binding.imageViewProfile.load(response.data?.user?.avatarUrl.toString()){
                crossfade(true)
                placeholder(R.drawable.circle_background)
                transformations(CircleCropTransformation())
            }
            binding.txtViewName.text = response.data?.user?.name

            val newLaunches = response.data?.user?.repositories
            repos.add(newLaunches)
            val adapter =
                RepoListAdapter(repos)
            binding.recycleViewPinnedItems.layoutManager = LinearLayoutManager(requireContext())
            binding.recycleViewPinnedItems.adapter = adapter
        }
    }

}