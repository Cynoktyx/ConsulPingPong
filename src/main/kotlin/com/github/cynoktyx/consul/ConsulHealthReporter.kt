package com.github.cynoktyx.consul

import com.github.cynoktyx.PingPongApplication
import com.github.cynoktyx.PingPongConfiguration
import com.github.cynoktyx.modules.Health
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration
import java.util.*
import javax.inject.Inject

/**
 * Created by lukas on 31.01.17
 */
class ConsulHealthReporter(val delay: Long, val period: Long = delay) : TimerTask() {

	@Inject
	internal lateinit var consulConnection: ConsulConnection
	@Inject
	internal lateinit var configuration: PingPongConfiguration
	@Inject
	internal lateinit var health: Health
	val serviceId get() = consulConnection.serviceId.toString()

	init {
		PingPongApplication.component.inject(this)
	}

	private fun register() {
		val registration = ImmutableRegistration.builder()
				.check(Registration.RegCheck.ttl(30L))
				.port(configuration.applicationPort)
				.name(consulConnection.serviceName)
				.id(serviceId)
				.address(configuration.ip)
				.build()

		consulConnection.agentClient.register(registration)
		consulConnection.agentClient.pass(serviceId)
	}

	private fun deregister() {
		consulConnection.agentClient.deregister(serviceId)
	}

	override fun run() {
		if (health.healthStatus.isHealthy)
			consulConnection.agentClient.pass(serviceId, health.healthStatus.toString())
		else
			consulConnection.agentClient.fail(serviceId, health.healthStatus.toString())
	}

	private val timer = Timer()

	fun start() {
		register()
		timer.schedule(this, delay, period)
	}

	fun stop() {
		timer.cancel()
		deregister()
	}
}
