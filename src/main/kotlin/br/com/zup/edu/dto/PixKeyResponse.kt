package br.com.zup.edu.dto

import br.com.zup.edu.ListAllKeysResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class PixKeyResponse(key: ListAllKeysResponse.PixKey) {
    val pixId = key.pixId
    val keyValue = key.keyValue
    val keyType = key.keyType
    val accountType = key.accountType
    val createdAt = key.createdAt.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC).toString()
    }
}
