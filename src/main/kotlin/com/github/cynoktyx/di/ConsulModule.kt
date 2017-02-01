package com.github.cynoktyx.di

import com.github.cynoktyx.PingPongConfiguration
import com.github.cynoktyx.consul.ConsulConnection
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by lukas on 31.01.17
 */
@Module
class ConsulModule {
	@Provides
	@Singleton
	fun provideConsulConnection(configuration: PingPongConfiguration) = ConsulConnection(configuration.name)
}