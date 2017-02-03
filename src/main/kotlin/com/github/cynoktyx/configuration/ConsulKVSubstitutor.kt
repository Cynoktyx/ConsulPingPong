package com.github.cynoktyx.configuration

import com.orbitz.consul.Consul
import org.apache.commons.lang3.text.StrLookup
import org.apache.commons.lang3.text.StrSubstitutor

/**
 * Created by lukas on 03.02.17
 */
class ConsulKVSubstitutor(strict: Boolean = true, substitutionInVariables: Boolean = false) :
		StrSubstitutor(ConsulKVLookup(strict)) {
	init {
		this.isEnableSubstitutionInVariables = substitutionInVariables
	}
}

class ConsulKVLookup(val strict: Boolean = true) : StrLookup<Any>() {
	val client = Consul.builder().withUrl(System.getenv("CONSUL_URL") ?: "http://localhost:8500").build()!!

	override fun lookup(key: String): String? {
		val value = client.keyValueClient().getValueAsString(key)

		if (value.isPresent)
			return value.get()
		else {
			if (strict)
				throw UndefinedConsulKVException(
						"The variable '$key' was not found in the Consul KV Store. " +
								"Could not substitute the expression '\${$key}'")
			else
				return null
		}
	}
}

class UndefinedConsulKVException(message: String) : RuntimeException(message)