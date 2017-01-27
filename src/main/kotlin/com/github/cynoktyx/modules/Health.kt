package com.github.cynoktyx.modules

import com.codahale.metrics.health.HealthCheck

/**
 * Created by lukas on 27.01.17
 */
class Health {
	var healthStatus = HealthCheck.Result.healthy()!! // Dummy Health
}