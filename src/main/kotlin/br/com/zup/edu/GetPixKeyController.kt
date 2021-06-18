package br.com.zup.edu

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.UUID
import javax.inject.Inject
import br.com.zup.edu.GetKeyRequest.FilterByPixId
import br.com.zup.edu.dto.DetailsPixKeyResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import org.slf4j.LoggerFactory

@Controller("api/v1/clients")
class GetPixKeyController(
    @Inject val grpcClient: KeyManagerGetServiceGrpc.KeyManagerGetServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/{clientId}/pix/{pixId}")
    fun getPixKeyByPixId(clientId: UUID, pixId: UUID): HttpResponse<DetailsPixKeyResponse> {
        val request = GetKeyRequest.newBuilder()
            .setPixId(FilterByPixId.newBuilder()
                .setClientId(clientId.toString())
                .setPixId(pixId.toString())
                .build()
            )
            .build()

        val response = grpcClient.get(request)

        return HttpResponse.ok(DetailsPixKeyResponse(response))
    }

    @Get("/pix/{keyValue}")
    fun getPixKeyByValue(keyValue: String): HttpResponse<DetailsPixKeyResponse> {
        val request = GetKeyRequest.newBuilder()
            .setKey(keyValue)
            .build()

        val response = grpcClient.get(request)

        return HttpResponse.ok(DetailsPixKeyResponse(response))
    }
}