package br.com.zup.edu.dto

import br.com.zup.edu.AccountType
import br.com.zup.edu.GetKeyResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class DetailsPixKeyResponse(response: GetKeyResponse) {
    val keyType = response.pixKey.keyType
    val keyValue = response.pixKey.keyValue

    val accountType = when(response.pixKey.account.accountType) {
        AccountType.CONTA_CORRENTE -> "CONTA_CORRENTE"
        AccountType.CONTA_POUPANCA -> "CONTA_POUPANCA"
        else -> "NAO_RECONHECIDA"
    }

    val account = mapOf(
        Pair("type", accountType),
        Pair("institution", response.pixKey.account.institutionName),
        Pair("owner_name", response.pixKey.account.clientName),
        Pair("owner_cpf", response.pixKey.account.clientCpf),
        Pair("agency", response.pixKey.account.agencyNumber),
        Pair("number", response.pixKey.account.accountNumber)
    )

    val createdAt = response.pixKey.createdAt.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC).toString()
    }
}

