package br.com.zup.edu.dto

import br.com.zup.edu.AccountType
import br.com.zup.edu.CreateKeyRequest
import br.com.zup.edu.KeyType
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import br.com.zup.edu.shared.validations.ValidPixKey

@Introspected
@ValidPixKey
data class PixKeyRequest(
    @field:NotNull val keyType: KeyTypeRequest?,
    @field:Size(max = 77) val keyValue: String?,
    @field:NotNull val accountType: AccountTypeRequest?
) {
    fun toModel(clientId: String): CreateKeyRequest {
        return CreateKeyRequest.newBuilder()
            .setAccountType(accountType?.grpcType ?: AccountType.UNKNOWN_ACCOUNT_TYPE)
            .setClientId(clientId)
            .setKeyType(keyType?.grpcType ?: KeyType.UNKNOWN_KEY_TYPE)
            .setKeyValue(keyValue)
            .build()
    }
}

enum class KeyTypeRequest(val grpcType: KeyType) {
    CPF(KeyType.CPF) {
        override fun isValid(key: String?): Boolean {
            if (key.isNullOrBlank()) return false

            if (!key.matches("^[0-9]{11}\$".toRegex())) return false

            return CPFValidator().run {
                initialize(null)
                isValid(key, null)
            }
        }
    },
    PHONE_NUMBER(KeyType.PHONE_NUMBER) {
        override fun isValid(key: String?): Boolean {
            if (key.isNullOrBlank()) return false

            return key.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    EMAIL(KeyType.EMAIL) {
        override fun isValid(key: String?): Boolean {
            if (key.isNullOrBlank()) return false

            return EmailValidator().run {
                initialize(null)
                isValid(key, null)
            }
        }
    },
    RANDOM_KEY(KeyType.RANDOM_KEY) {
        override fun isValid(key: String?) = key.isNullOrBlank()
    };

    abstract fun isValid(key: String?): Boolean
}

enum class AccountTypeRequest(val grpcType: AccountType) {
    CONTA_CORRENTE(AccountType.CONTA_CORRENTE),
    CONTA_POUPANCA(AccountType.CONTA_POUPANCA)
}