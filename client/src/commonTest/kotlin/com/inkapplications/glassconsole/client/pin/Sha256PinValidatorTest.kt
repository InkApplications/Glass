package com.inkapplications.glassconsole.client.pin

import com.inkapplications.glassconsole.structures.pin.ChallengeResponse
import com.inkapplications.glassconsole.structures.pin.Nonce
import com.inkapplications.glassconsole.structures.pin.Pin
import com.inkapplications.glassconsole.structures.pin.Psk
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Sha256PinValidatorTest {
    private val testPsk = Psk("123456")
    private val testTimestamp = Instant.fromEpochMilliseconds(2468)
    private val testClock = object: Clock {
        override fun now() = testTimestamp
    }

    @BeforeTest
    fun initialize() {
        runBlocking {
            LibSodiumInitializer.initialize(TargetManagerDummy)
        }
    }

    @Test
    fun validDigest() {
        val validator = Sha256PinValidator(testClock)
        val pin = Pin("test-pin")
        val nonce = Nonce("abcdefg")
        val result = validator.digest(
            psk = testPsk,
            pin = pin,
            timestamp = testClock.now(),
            nonce = nonce
        )

        assertEquals("720c72977271fba85919d318d933799d0417b917e9bd7feb2cae189f1cb2686e", result.digest)
    }

    @Test
    fun validateWitness() {
        val validator = Sha256PinValidator(testClock)
        val witness = ChallengeResponse(
            digest = "720c72977271fba85919d318d933799d0417b917e9bd7feb2cae189f1cb2686e",
            timestamp = testTimestamp,
            nonce = Nonce("abcdefg"),
        )
        val pin = Pin("test-pin")
        val challengeNonce = Nonce("zyxwvut")
        val result = validator.validate(
            psk = testPsk,
            witness = witness,
            pin = pin,
            challengeNonce = challengeNonce,
        )

        assertEquals("67c012ce8604030ed30c2761b8504caffcb7c73f1d71e27386bc409cadfcad12", result.digest)
    }

    @Test
    fun invalidPinThrows() {
        val validator = Sha256PinValidator(testClock)
        val witness = ChallengeResponse(
            digest = "720c72977271fba85919d318d933799d0417b917e9bd7feb2cae189f1cb2686e",
            timestamp = testTimestamp,
            nonce = Nonce("abcdefg"),
        )
        val pin = Pin("wrong-pin")
        val challengeNonce = Nonce("zyxwvut")
        val result = runCatching {
            validator.validate(
                psk = testPsk,
                witness = witness,
                pin = pin,
                challengeNonce = challengeNonce,
            )
        }

        assertTrue(result.isFailure, "Invalid pin should throw exception")
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun invalidPskThrows() {
        val validator = Sha256PinValidator(testClock)
        val witness = ChallengeResponse(
            digest = "720c72977271fba85919d318d933799d0417b917e9bd7feb2cae189f1cb2686e",
            timestamp = testTimestamp,
            nonce = Nonce("abcdefg"),
        )
        val pin = Pin("test-pin")
        val challengeNonce = Nonce("zyxwvut")
        val result = runCatching {
            validator.validate(
                psk = Psk("wrong-psk"),
                witness = witness,
                pin = pin,
                challengeNonce = challengeNonce,
            )
        }

        assertTrue(result.isFailure, "Invalid PSK should throw exception")
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }
}
