package com.example.githubapp.model

import ProfileDetailsQuery
import android.content.Context
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.example.githubapp.appollo.apolloClient
import dagger.Module
import dagger.Provides
import javax.inject.Inject


class ProfileModel @Inject constructor() :Contract.Model {

    override suspend fun fetchData(onFinishedListener: Contract.Model.OnFinishedListener?,context: Context): Response<ProfileDetailsQuery.Data> {
        return apolloClient(context)
            .query(ProfileDetailsQuery()).responseFetcher(ApolloResponseFetchers.CACHE_FIRST).toDeferred().await()

    }
}