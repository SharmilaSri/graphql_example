package com.example.githubapp.views

import ProfileDetailsQuery
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.example.githubapp.R
import com.example.githubapp.adapters.RepoListAdapter
import com.example.githubapp.appollo.apolloClient
import com.example.githubapp.databinding.FragmentProfileBinding
import com.example.githubapp.model.Contract
import com.example.githubapp.model.ProfileModel
import com.example.githubapp.model.ProfilePresenter


class ProfileFragment : Fragment() ,Contract.View{

    private lateinit var binding: FragmentProfileBinding
    var presenter: ProfilePresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        presenter = ProfilePresenter(this, ProfileModel())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {

            getData(requireContext())

        }
    }

    override fun showProgress() {
       binding.progressBar.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility=View.GONE
    }

    override suspend fun  getData(context: Context) {
        presenter!!.getDataFromNetwork(context)
    }

    override fun displayData(response: Response<ProfileDetailsQuery.Data>) {

        binding.imageViewProfile.load(response.data?.user?.avatarUrl.toString()){
            crossfade(true)
            placeholder(R.drawable.circle_background)
            transformations(CircleCropTransformation())
        }
        binding.txtViewName.text = response.data?.user?.name
        binding.txtViewEmail.text = response.data?.user?.email
        binding.txtViewFollowers.text = "${response.data?.user?.followers?.totalCount.toString()} followers"
        binding.txtViewFollowing.text = "${response.data?.user?.following?.totalCount.toString()} following"

        val repos = mutableListOf<ProfileDetailsQuery.Repositories?>()
        val newLaunches = response.data?.user?.repositories
        repos.add(newLaunches)
        val adapter = RepoListAdapter(repos)

        binding.recycleViewPinnedItems.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewPinnedItems.adapter = adapter

        binding.recycleViewTopRepo.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recycleViewTopRepo.adapter = adapter

        binding.recycleViewStarredRepo.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recycleViewStarredRepo.adapter = adapter
    }


}