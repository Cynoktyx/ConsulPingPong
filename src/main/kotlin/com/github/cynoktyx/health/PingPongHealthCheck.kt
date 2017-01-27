package com.github.cynoktyx.health

import com.codahale.metrics.health.HealthCheck
import com.github.cynoktyx.PingPongApplication
import com.github.cynoktyx.modules.Health
import javax.inject.Inject

/**
 * Created by lukas on 27.01.17
 */
class PingPongHealthCheck : HealthCheck() {
	@Inject
	lateinit var health: Health

	init {
		PingPongApplication.component.inject(this)
	}

	override fun check() = health.healthStatus
}