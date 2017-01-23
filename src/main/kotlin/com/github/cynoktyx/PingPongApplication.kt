package com.github.cynoktyx

import io.dropwizard.Application
import io.dropwizard.setup.Environment

/**
 * Created by lukas on 04.01.17
 */
fun main(args: Array<String>) {
	val botApplication = PingPongApplication()
	botApplication.run(*args)
}

class PingPongApplication : Application<PingPongConfiguration>() {

	override fun getName(): String {
		return "ping-pong-service"
	}

	override fun run(configuration: PingPongConfiguration, environment: Environment) {
	}
}

