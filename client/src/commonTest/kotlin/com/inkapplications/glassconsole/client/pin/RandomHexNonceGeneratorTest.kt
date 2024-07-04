package com.inkapplications.glassconsole.client.pin

import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RandomHexNonceGeneratorTest {
    @BeforeTest
    fun initialize() {
        runBlocking {
            LibSodiumInitializer.initialize(TargetManagerDummy)
        }
    }

    @Test
    fun uniqueValues() {
        val generator = RandomHexNonceGenerator()
        val values = (0 until 1000).map {
            generator.generateNonce().value.also {
                assertEquals(32, it.length, "Generated Nonce should be length 32")
            }
        }

        assertEquals(1000, values.distinct().size)
    }
}
