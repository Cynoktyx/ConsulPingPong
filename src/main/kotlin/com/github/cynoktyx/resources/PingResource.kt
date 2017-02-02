package com.github.cynoktyx.resources

import com.github.cynoktyx.consul.ConsulConnection
import java.util.stream.Collectors
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.client.Client
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * Created by lukas on 02.02.17
 */
@Path("pingPong")
class PingResource @Inject constructor(private val consulConnection: ConsulConnection, val client: Client) {

	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	fun ping(): Response {
		return Response.ok(Entity.text("Pong from " + consulConnection.fullServiceName).entity).build()
	}

	@GET
	@Path("/pingAll")
	@Produces(MediaType.APPLICATION_JSON)
	fun pong(): List<String> {
		val services = consulConnection.getHealthyServicesByName()

		return services.parallelStream().map {
			client.target("http://" + it.service.address + ":" + it.service.port)
					.path(consulConnection.getValueAsString("rootPath").or { "/api" })
					.path("/pingPong/ping")
					.request(MediaType.TEXT_PLAIN_TYPE)
					.get(String::class.java)
		}.collect(Collectors.toList<String>())
	}
}