package com.github.cynoktyx.di

import com.github.cynoktyx.PingPongConfiguration
import dagger.Module
import dagger.Provides
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.setup.Environment
import javax.inject.Named
import javax.inject.Singleton
import javax.ws.rs.client.Client

/**
 * Created by lukas on 24.01.17
 */
@Module
class NetworkingModule {
	@Provides
	fun provideClient(environment: Environment, configuration: PingPongConfiguration): Client {
		return JerseyClientBuilder(environment).using(configuration.jerseyClientConfiguration).build(configuration.name)
	}

	@Provides
	@Singleton
	@Named("global")
	fun provideGlobalClient(environment: Environment, configuration: PingPongConfiguration): Client {
		return JerseyClientBuilder(environment).using(configuration.jerseyClientConfiguration).build(configuration.name)
	}
}