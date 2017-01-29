package com.github.cynoktyx.resources

import com.github.cynoktyx.di.DaggerTestPingPongComponent
import com.github.cynoktyx.di.TestPingPongComponent
import com.pholser.junit.quickcheck.Property
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck
import io.dropwizard.testing.junit.ResourceTestRule
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.core.AnyOf
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot
import org.hamcrest.core.StringContains
import org.junit.Assert
import org.junit.Assume.assumeThat
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by lukas on 29.01.17
 */
@RunWith(JUnitQuickcheck::class)
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

	@Property(trials = 1000)
	fun propertyTestSetHealth(state: String) {
		assumeThat(state, IsNot(AnyOf(listOf(IsEqual("healthy"), StringContains("{"), StringContains("}")))))

		val path = resources.client().target("/health").path("/set")
		val health: HealthResult?
		try {
			health = path.queryParam("newHealth", state).request().get(HealthResult::class.java)
		} catch(e: Exception) {
			e.printStackTrace()
			Assert.assertTrue(false)
			return
		}

		assertThat(health.healthy).isFalse()
		assertThat(health.message).isEqualTo(state)
	}

	data class HealthResult(val healthy: Boolean, val message: String?, val error: String?) {
		constructor() : this(false, null, null)
	}
}