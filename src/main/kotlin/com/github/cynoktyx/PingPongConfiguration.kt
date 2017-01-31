package com.github.cynoktyx

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.client.JerseyClientConfiguration
import java.io.BufferedReader
import java.io.InputStreamReader

class PingPongConfiguration : Configuration() {
	lateinit var name: String
		internal set

	@JsonProperty("jerseyClient")
	private var jerseyClient: JerseyClientConfiguration? = null
		get() = field ?: JerseyClientConfiguration()

	// To prevent handling impossible null values
	val jerseyClientConfiguration get() = jerseyClient!!

	var applicationPort = 0
		internal set
	var adminPort = 0
		internal set
	
	val ip: String by lazy {
		val p = Runtime.getRuntime().exec("hostname -i")
		p.waitFor()

		val reader = BufferedReader(InputStreamReader(p.inputStream))
		val line = reader.readLine()

		line ?: "127.0.0.1"
	}
}