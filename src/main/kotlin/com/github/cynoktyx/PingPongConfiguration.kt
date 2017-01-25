package com.github.cynoktyx

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.client.JerseyClientConfiguration

class PingPongConfiguration : Configuration() {
	lateinit var name: String
		internal set

	@JsonProperty("jerseyClient")
	private var jerseyClient: JerseyClientConfiguration? = null
		get() = field ?: JerseyClientConfiguration()

	// To prevent handling impossible null values
	val jerseyClientConfiguration get() = jerseyClient!!
}