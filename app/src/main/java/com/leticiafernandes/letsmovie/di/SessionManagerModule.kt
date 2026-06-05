package com.leticiafernandes.letsmovie.di

import com.leticiafernandes.letsmovie.data.user.datasource.local.SessionManager
import com.leticiafernandes.letsmovie.data.user.datasource.local.SessionPreferencesManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionManagerModule {

    @Binds
    abstract fun bindSessionManager(impl: SessionPreferencesManager): SessionManager
}
