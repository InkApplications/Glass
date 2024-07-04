package com.inkapplications.glassconsole.structures.pin

import kotlin.jvm.JvmInline

/**
 * Pre-shared key used to obscure the PIN in its digest.
 */
@JvmInline
value class Psk(val value: String)
