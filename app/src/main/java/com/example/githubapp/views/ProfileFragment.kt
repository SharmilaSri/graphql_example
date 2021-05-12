package com.example.githubapp.views

import ProfileDetailsQuery
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.apollographql.apollo.api.Response
import com.example.githubapp.R
import com.example.githubapp.adapters.RepoListAdapter
import com.example.githubapp.databinding.FragmentProfileBinding
import com.example.githubapp.contract.Contract
import com.example.githubapp.presenter.ProfilePresenter


class ProfileFragment: Fragment() , Contract.View{

    private lateinit var binding: FragmentProfileBinding
    lateinit var presenter: Contract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        presenter = ProfilePresenter(this)
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

    override fun displayData(array: Response<ProfileDetailsQuery.Data>,list:MutableList<ProfileDetailsQuery.Repositories?>) {

        binding.imageViewProfile.load(array.data?.user?.avatarUrl.toString()){
            crossfade(true)
            placeholder(R.drawable.circle_background)
            transformations(CircleCropTransformation())
        }
        binding.txtViewName.text = array.data?.user?.name
        binding.txtViewEmail.text = array.data?.user?.email
        binding.txtViewFollowers.text = "${array.data?.user?.followers?.totalCount.toString()} followers"
        binding.txtViewFollowing.text = "${array.data?.user?.following?.totalCount.toString()} following"

        val adapter = RepoListAdapter(list)
        binding.recycleViewPinnedItems.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewPinnedItems.adapter = adapter

        binding.recycleViewTopRepo.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recycleViewTopRepo.adapter = adapter

        binding.recycleViewStarredRepo.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recycleViewStarredRepo.adapter = adapter
    }


}