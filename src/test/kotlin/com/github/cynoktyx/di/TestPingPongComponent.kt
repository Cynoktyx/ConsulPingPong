package com.github.cynoktyx.di

import com.github.cynoktyx.health.PingPongHealthCheck
import com.github.cynoktyx.resources.HealthResource
import dagger.Component
import javax.inject.Singleton

/**
 * Created by lukas on 05.01.17
 */
@Singleton
@Component(modules = arrayOf(TestApplicationModule::class, TestNetworkingModule::class, TestHealthModule::class))
interface TestPingPongComponent {
	fun inject(healthCheck: PingPongHealthCheck)

	val healthResource: HealthResource
}