package com.github.cynoktyx.di

import com.github.cynoktyx.modules.Health
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by lukas on 27.01.17
 */
@Module
class HealthModule {
	@Provides
	@Singleton
	fun provideHealth() = Health()
}