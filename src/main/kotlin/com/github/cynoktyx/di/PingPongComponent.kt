package com.github.cynoktyx.di

import com.github.cynoktyx.health.PingPongHealthCheck
import dagger.Component
import javax.inject.Singleton

/**
 * Created by lukas on 05.01.17
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkingModule::class, HealthModule::class))
interface PingPongComponent {
	fun inject(healthCheck: PingPongHealthCheck)
}