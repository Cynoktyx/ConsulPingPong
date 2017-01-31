package com.github.cynoktyx.di

import com.github.cynoktyx.StartupListener
import com.github.cynoktyx.consul.ConsulHealthReporter
import com.github.cynoktyx.health.PingPongHealthCheck
import com.github.cynoktyx.resources.HealthResource
import dagger.Component
import javax.inject.Singleton

/**
 * Created by lukas on 05.01.17
 */
@Singleton
@Component(
		modules = arrayOf(ApplicationModule::class, NetworkingModule::class, HealthModule::class, ConsulModule::class))
interface PingPongComponent {
	fun inject(healthCheck: PingPongHealthCheck)
	fun inject(healthCheck: StartupListener)
	fun inject(consulHealthReporter: ConsulHealthReporter)

	val healthResource: HealthResource
}