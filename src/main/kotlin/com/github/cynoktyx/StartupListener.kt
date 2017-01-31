package com.github.cynoktyx

import io.dropwizard.lifecycle.ServerLifecycleListener
import org.eclipse.jetty.server.Server
import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel
import javax.inject.Inject

/**
 * Created by lukas on 31.01.17
 */
class StartupListener : ServerLifecycleListener {
	@Inject
	internal lateinit var configuration: PingPongConfiguration

	init {
		PingPongApplication.component.inject(this)
	}

	override fun serverStarted(server: Server) {
		for (connector in server.connectors) {
			when (connector.name) {
				"admin" -> configuration.adminPort = (connector.transport as ServerSocketChannel).getPort()
				"application" -> configuration.applicationPort = (connector.transport as ServerSocketChannel).getPort()
			}
		}
	}

	private fun ServerSocketChannel.getPort() = (localAddress as InetSocketAddress).port
}