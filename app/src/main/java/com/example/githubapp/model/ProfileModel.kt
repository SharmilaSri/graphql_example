package com.example.githubapp.model

import ProfileDetailsQuery
import android.content.Context
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.example.githubapp.appollo.apolloClient
import com.example.githubapp.contract.Contract

class ProfileModel(): Contract.Model {

    override suspend fun fetchData(context: Context): Response<ProfileDetailsQuery.Data> {
        return apolloClient(context)
            .query(ProfileDetailsQuery()).responseFetcher(ApolloResponseFetchers.CACHE_FIRST).toDeferred().await()

    }
}