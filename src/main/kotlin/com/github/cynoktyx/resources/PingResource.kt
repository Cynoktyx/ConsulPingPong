package com.github.cynoktyx.resources

import com.github.cynoktyx.consul.ConsulConnection
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * Created by lukas on 02.02.17
 */
@Path("pingPong")
class PingResource @Inject constructor(private val consulConnection: ConsulConnection) {

	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	fun ping(): Response {
		return Response.ok(Entity.text("Pong from " + consulConnection.fullServiceName).entity).build()
	}
}