package com.inkapplications.glassconsole.client.pin

import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ChunkedHexPskGeneratorTest {
    @BeforeTest
    fun initialize() {
        runBlocking {
            LibSodiumInitializer.initialize(TargetManagerDummy)
        }
    }

    @Test
    fun uniqueValues() {
        val generator = ChunkedHexPskGenerator()
        val values = (0 until 1000).map {
            generator.generate().value.also {
                assertEquals(29, it.length, "Generated PSK should be length 29 (4*6 groups + 5 dashes)")
            }
        }

        assertEquals(1000, values.distinct().size)
    }
}
