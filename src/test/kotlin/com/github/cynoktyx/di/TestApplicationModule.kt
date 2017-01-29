package com.github.cynoktyx.di

import com.github.cynoktyx.PingPongConfiguration
import dagger.Module
import dagger.Provides
import io.dropwizard.setup.Environment
import javax.inject.Singleton

/**
 * Created by lukas on 05.01.17
 */
@Module
class TestApplicationModule(val configuration: PingPongConfiguration, val environment: Environment) {
	@Singleton
	@Provides
	fun provideConfiguration() = configuration

	@Singleton
	@Provides
	fun provideEnvironment() = environment
}