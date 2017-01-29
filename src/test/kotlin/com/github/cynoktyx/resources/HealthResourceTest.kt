package com.github.cynoktyx.resources

import com.github.cynoktyx.di.DaggerTestPingPongComponent
import com.github.cynoktyx.di.TestPingPongComponent
import io.dropwizard.testing.junit.ResourceTestRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

/**
 * Created by lukas on 29.01.17
 */
class HealthResourceTest {

	companion object {
		private val component: TestPingPongComponent = DaggerTestPingPongComponent.create()
		@ClassRule
		@JvmField
		val resources = ResourceTestRule.builder()
				.addResource(component.healthResource)
				.build()!!
	}

	@Test
	fun testGetHealth() {
		val client = resources.client().target("/health")
		val health = client.path("/").request().get(HealthResult::class.java)

		assertThat(health.healthy).isTrue()
		assertThat(health.message).isNull()
	}

	@Test
	fun testSetHealth() {
		val path = resources.client().target("/health").path("/set")
		var health = path.queryParam("newHealth", "sick").request().get(HealthResult::class.java)

		assertThat(health.healthy).isFalse()
		assertThat(health.message).isEqualTo("sick")

		health = path.queryParam("newHealth", "healthy").request().get(HealthResult::class.java)

		assertThat(health.healthy).isTrue()
		assertThat(health.message).isNull()
	}

	data class HealthResult(val healthy: Boolean, val message: String?, val error: String?) {
		constructor() : this(false, null, null)
	}
}