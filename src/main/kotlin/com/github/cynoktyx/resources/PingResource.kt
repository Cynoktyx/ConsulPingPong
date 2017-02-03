package com.github.cynoktyx.resources

import com.github.cynoktyx.consul.ConsulConnection
import com.orbitz.consul.model.health.ServiceHealth
import java.util.stream.Collectors
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
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
	fun ping(@QueryParam("id") id: String?): Response {
		if (id == null || id == consulConnection.serviceId.toString())
			return Response.ok(Entity.text("Pong from " + consulConnection.fullServiceName).entity).build()

		val services = consulConnection.getHealthyServicesByName()
		val service = services.first { it.service.id == id }

		return getRootPath(service).path("/pingPong/ping").request(MediaType.TEXT_PLAIN_TYPE).get()
	}

	@GET
	@Path("/pingAll")
	@Produces(MediaType.APPLICATION_JSON)
	fun pong(): List<String> {
		val services = consulConnection.getHealthyServicesByName()

		return services.parallelStream().map {
			getRootPath(it)
					.path("/pingPong/ping")
					.request(MediaType.TEXT_PLAIN_TYPE)
					.get(String::class.java)
		}.collect(Collectors.toList<String>())
	}

	fun getRootPath(service: ServiceHealth) = client.target(
			"http://" + service.service.address + ":" + service.service.port)
			.path(consulConnection.getValueAsString("rootPath").or { "/api" })!!
}