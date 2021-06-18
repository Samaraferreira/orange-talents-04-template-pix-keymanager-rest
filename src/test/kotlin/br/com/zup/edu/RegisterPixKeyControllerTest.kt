package br.com.zup.edu

import br.com.zup.edu.dto.AccountTypeRequest
import br.com.zup.edu.dto.KeyTypeRequest
import br.com.zup.edu.dto.PixKeyRequest
import br.com.zup.edu.shared.grpc.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RegisterPixKeyControllerTest {

    @field:Inject
    lateinit var registerStub: KeyManagerRegisterServiceGrpc.KeyManagerRegisterServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `should register a new pix key`() {
        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = CreateKeyResponse.newBuilder()
                                .setPixId(pixId)
                                .build()

        given(registerStub.register(Mockito.any())).willReturn(responseGrpc)

        val newPixkeyRequest = PixKeyRequest(
            keyType = KeyTypeRequest.EMAIL,
            keyValue = "random@email.com",
            accountType = AccountTypeRequest.CONTA_CORRENTE
        )
        val request = HttpRequest.POST("api/v1/clients/${clientId}/pix", newPixkeyRequest)
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.contains(pixId))
    }
}