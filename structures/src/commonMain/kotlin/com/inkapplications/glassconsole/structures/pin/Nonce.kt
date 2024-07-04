package com.inkapplications.glassconsole.structures.pin

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * A random value used to distinguish pin validation challenges.
 */
@JvmInline
@Serializable
value class Nonce(val value: String)
