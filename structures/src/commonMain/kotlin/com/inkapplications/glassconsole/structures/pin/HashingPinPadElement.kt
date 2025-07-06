package com.inkapplications.glassconsole.structures.pin

import ink.ui.render.remote.serialization.ElementId
import ink.ui.render.remote.serialization.event.UiEvent
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.render.remote.serialization.event.typedListener
import ink.ui.structures.elements.UiElement
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A [PinPadElement] that hashes the input and sends it to a server.
 */
data class HashingPinPadElement(
    val challengeNonce: Nonce,
    val witness: ChallengeResponse,
    val onSuccess: (ChallengeResponse) -> Unit,
): UiElement.Interactive

@Serializable
data class EnterHashedPinEvent(
    override val id: ElementId,
    val response: ChallengeResponse,
): UiEvent {
    object Listeners {
        val PinListener = typedListener<HashingPinPadElement, EnterHashedPinEvent> { element, event ->
            element.onSuccess(event.response)
        }
    }
}


class HashingPinPadElementSerializer(
    private val uiEvents: UiEvents,
): KSerializer<HashingPinPadElement> {
    private val surrogateSerializer = HashingPinPadElementSurrogate.serializer()
    override val descriptor: SerialDescriptor = surrogateSerializer.descriptor

    override fun deserialize(decoder: Decoder): HashingPinPadElement
    {
        val surrogate = decoder.decodeSerializableValue(surrogateSerializer)
        return HashingPinPadElement(
            challengeNonce = surrogate.nonce,
            witness = surrogate.witness,
            onSuccess = { response ->
                uiEvents.onEvent(
                    event = EnterHashedPinEvent(
                        id = surrogate.id,
                        response = response
                    )
                )
            }
        )
    }

    override fun serialize(
        encoder: Encoder,
        value: HashingPinPadElement
    ) {
        val id = ElementId()
        val surrogate = HashingPinPadElementSurrogate(
            id = id,
            nonce = value.challengeNonce,
            witness = value.witness
        )
        encoder.encodeSerializableValue(surrogateSerializer, surrogate)
        uiEvents.associateElementEvents(id, value)
    }

    @Serializable
    @SerialName("hashing-pin-pad")
    data class HashingPinPadElementSurrogate(
        val id: ElementId,
        val nonce: Nonce,
        val witness: ChallengeResponse
    )
}
