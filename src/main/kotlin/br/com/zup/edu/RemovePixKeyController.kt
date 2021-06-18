package br.com.zup.edu

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import org.slf4j.LoggerFactory
import java.util.UUID
import javax.inject.Inject

@Controller("api/v1/clients/{clientId}")
class RemovePixKeyController(@Inject val grpcClient: KeyManagerDeleteServiceGrpc.KeyManagerDeleteServiceBlockingStub) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Delete("pix/{pixId}")
    fun removePixKey(clientId: UUID, pixId: UUID) : HttpResponse<Any> {
        LOGGER.info("[$clientId] removing a pix key with pixId: $pixId")
        grpcClient.delete(
            DeleteKeyRequest.newBuilder()
                .setClientId(clientId.toString())
                .setPixId(pixId.toString())
                .build()
        )

        return HttpResponse.ok()
    }
}