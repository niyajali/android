package com.bitwarden.network.model

import androidx.annotation.Keep
import com.bitwarden.core.data.serializer.BaseEnumeratedIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents different types of ciphers.
 */
@Serializable(CipherTypeSerializer::class)
enum class CipherTypeJson {
    /**
     * A login containing a username and password.
     */
    @SerialName("1")
    LOGIN,

    /**
     * A secure note.
     */
    @SerialName("2")
    SECURE_NOTE,

    /**
     * A credit/debit card.
     */
    @SerialName("3")
    CARD,

    /**
     * Personal information for filling out forms.
     */
    @SerialName("4")
    IDENTITY,

    /**
     * A SSH key.
     */
    @SerialName("5")
    SSH_KEY,
}

@Keep
private class CipherTypeSerializer : BaseEnumeratedIntSerializer<CipherTypeJson>(
    className = "CipherTypeJson",
    values = CipherTypeJson.entries.toTypedArray(),
)
