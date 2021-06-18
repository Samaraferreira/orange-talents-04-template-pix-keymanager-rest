package br.com.zup.edu

import br.com.zup.edu.GetKeyResponse.PixKey
import br.com.zup.edu.GetKeyResponse.PixKey.AccountInfo
import com.google.protobuf.Timestamp
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

@MicronautTest
internal class GetPixKeyControllerTest {

    @field:Inject
    lateinit var getStub: KeyManagerGetServiceGrpc.KeyManagerGetServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    val CHAVE_EMAIL = "teste@teste.com.br"
    val CHAVE_CELULAR = "+5511912345678"
    val CONTA_CORRENTE = AccountType.CONTA_CORRENTE
    val TIPO_DE_CHAVE_EMAIL = KeyType.EMAIL
    val TIPO_DE_CHAVE_CELULAR = KeyType.PHONE_NUMBER
    val INSTITUICAO = "Itau"
    val TITULAR = "Woody"
    val DOCUMENTO_DO_TITULAR = "34597563067"
    val AGENCIA = "0001"
    val NUMERO_DA_CONTA = "1010-1"
    val CHAVE_CRIADA_EM = LocalDateTime.now()

    @Test
    fun `should load a pix key`() {
        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = getKeyResponse(clientId, pixId)

        Mockito.`when`(getStub.get(Mockito.any())).thenReturn(responseGrpc)

        val request = HttpRequest.GET<Any>("api/v1/clients/$clientId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
    }

    private fun getKeyResponse(clientId: String, pixId: String) = GetKeyResponse.newBuilder()
        .setClientId(clientId)
        .setPixId(pixId)
        .setPixKey(
            PixKey.newBuilder()
                .setKeyType(TIPO_DE_CHAVE_EMAIL)
                .setKeyValue(CHAVE_EMAIL)
                .setAccount(
                    AccountInfo.newBuilder()
                        .setAccountType(CONTA_CORRENTE)
                        .setAgencyNumber(AGENCIA)
                        .setAccountNumber(NUMERO_DA_CONTA)
                        .setInstitutionName(INSTITUICAO)
                        .setClientName(TITULAR)
                        .setClientCpf(DOCUMENTO_DO_TITULAR)
                )
                .setCreatedAt(CHAVE_CRIADA_EM.let {
                    val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                    Timestamp.newBuilder()
                        .setSeconds(createdAt.epochSecond)
                        .setNanos(createdAt.nano)
                        .build()
                })
        )
        .build()
}