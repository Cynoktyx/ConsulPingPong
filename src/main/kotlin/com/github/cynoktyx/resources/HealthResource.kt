package com.github.cynoktyx.resources

import com.codahale.metrics.health.HealthCheck
import com.github.cynoktyx.modules.Health
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

/**
 * Created by lukas on 27.01.17
 */

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
class HealthResource @Inject constructor(internal var health: Health) {

	@GET
	@Path("/")
	fun getHealth() = health.healthStatus

	@GET
	@Path("/set")
	fun setHealth(@QueryParam("newHealth") newHealth: String): HealthCheck.Result {
		health.setHealth(newHealth)
		return health.healthStatus
	}
}