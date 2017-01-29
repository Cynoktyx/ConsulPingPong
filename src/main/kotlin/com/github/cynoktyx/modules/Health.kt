package com.github.cynoktyx.modules

import com.codahale.metrics.health.HealthCheck

/**
 * Created by lukas on 27.01.17
 */
class Health {
	var healthStatus = HealthCheck.Result.healthy()!! // Dummy Health
		private set

	fun setHealth(newHealth: String, message: String? = null) {
		if (newHealth.toLowerCase() == "healthy")
			healthStatus = HealthCheck.Result.healthy(message)
		else
			healthStatus = HealthCheck.Result.unhealthy(message ?: newHealth)
	}
}