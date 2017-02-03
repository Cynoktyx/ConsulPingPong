package com.github.cynoktyx

import com.github.cynoktyx.configuration.ConsulKVSubstitutor
import com.github.cynoktyx.consul.ConsulHealthReporter
import com.github.cynoktyx.di.ApplicationModule
import com.github.cynoktyx.di.DaggerPingPongComponent
import com.github.cynoktyx.di.PingPongComponent
import com.github.cynoktyx.health.PingPongHealthCheck
import io.dropwizard.Application
import io.dropwizard.configuration.SubstitutingSourceProvider
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import kotlin.concurrent.thread

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

	override fun initialize(bootstrap: Bootstrap<PingPongConfiguration>) {
		bootstrap.configurationSourceProvider = SubstitutingSourceProvider(
				bootstrap.configurationSourceProvider, ConsulKVSubstitutor(false)
		)
	}

	override fun run(configuration: PingPongConfiguration, environment: Environment) {
		configuration.name = name
		component = DaggerPingPongComponent.builder().applicationModule(
				ApplicationModule(configuration, environment)).build()

		environment.jersey().register(component.healthResource)
		environment.jersey().register(component.consulDiscoveryResource)
		environment.jersey().register(component.pingResource)
		environment.healthChecks().register("dummyHealth", PingPongHealthCheck())

		environment.lifecycle().addServerLifecycleListener(StartupListener())
		environment.lifecycle().addServerLifecycleListener {
			val consulHealthReporter = ConsulHealthReporter(15000)
			consulHealthReporter.start()
			Runtime.getRuntime().addShutdownHook(thread(start = false) { consulHealthReporter.stop() })
		}
	}
}

