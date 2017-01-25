package com.github.cynoktyx

import com.github.cynoktyx.di.ApplicationModule
import com.github.cynoktyx.di.DaggerPingPongComponent
import com.github.cynoktyx.di.PingPongComponent
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

	companion object {
		lateinit var component: PingPongComponent
			private set
	}

	override fun getName(): String {
		return "ping-pong-service"
	}

	override fun run(configuration: PingPongConfiguration, environment: Environment) {
		configuration.name = name
		component = DaggerPingPongComponent.builder().applicationModule(
				ApplicationModule(configuration, environment)).build()
	}
}

