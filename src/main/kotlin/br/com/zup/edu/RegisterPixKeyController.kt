package br.com.zup.edu

import br.com.zup.edu.dto.PixKeyRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.UUID
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("api/v1/clients/{clientId}")
class RegisterPixKeyController(
    @Inject val grpcClient: KeyManagerRegisterServiceGrpc.KeyManagerRegisterServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Post("/pix")
    fun register(@PathVariable clientId: UUID, @Valid @Body request: PixKeyRequest): HttpResponse<Any> {

        LOGGER.info("[$clientId] creating a new pix key with $request")
        val grpcResponse = grpcClient.register(request.toModel(clientId.toString()))

        return HttpResponse.created(location(clientId, grpcResponse.pixId))
    }

    private fun location(clientId: UUID, pixId: String) = HttpResponse
        .uri("/api/v1/clients/$clientId/pix/${pixId}")

}