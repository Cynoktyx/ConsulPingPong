package com.github.cynoktyx.resources

import com.github.cynoktyx.consul.ConsulConnection
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

/**
 * Created by lukas on 01.02.17
 */
@Path("/consul")
@Produces(MediaType.APPLICATION_JSON)
class ConsulDiscoveryResource @Inject constructor(internal var consulConnection: ConsulConnection) {
	@GET
	@Path("list")
	fun list(@QueryParam("serviceName") serviceName: String?) = consulConnection.getServicesByName(
			serviceName ?: consulConnection.serviceName).map { it.toString() }
}