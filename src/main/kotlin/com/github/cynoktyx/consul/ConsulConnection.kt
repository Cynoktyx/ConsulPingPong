package com.github.cynoktyx.consul

import com.orbitz.consul.Consul

/**
 * Created by lukas on 31.01.17
 */

class ConsulConnection(val serviceName: String, url: String = "") {
	val consul: Consul
	val agentClient
		get() = consul.agentClient()!!
	val serviceId: Long
	val fullServiceName
		get() = "$serviceName:$serviceId"

	init {
		if (url.isEmpty())
			consul = Consul.builder().build()
		else
			consul = Consul.builder().withUrl(url).build()

		var id = 0L
		while (agentClient.isRegistered(id.toString())) {
			id++
		}
		serviceId = id
	}

	fun getHealthyServicesByName(serviceName: String = this.serviceName) =
			consul.healthClient().getHealthyServiceInstances(serviceName).response

	fun getServicesByName(serviceName: String = this.serviceName) =
			consul.healthClient().getAllServiceInstances(serviceName).response
}