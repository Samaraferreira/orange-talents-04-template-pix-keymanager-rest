package br.com.zup.edu

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.UUID
import javax.inject.Inject

@MicronautTest
internal class RemovePixKeyControllerTest {

    @field:Inject
    lateinit var removeStub: KeyManagerDeleteServiceGrpc.KeyManagerDeleteServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `should remove a pix key`() {
        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = DeleteKeyResponse.newBuilder()
                                        .setPixId(pixId)
                                        .build()

        Mockito.`when`(removeStub.delete(Mockito.any())).thenReturn(responseGrpc)

        val request = HttpRequest.DELETE<Any>("api/v1/clients/$clientId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
    }
}