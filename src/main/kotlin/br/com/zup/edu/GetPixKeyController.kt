package br.com.zup.edu

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.UUID
import javax.inject.Inject
import br.com.zup.edu.GetKeyRequest.FilterByPixId
import br.com.zup.edu.dto.DetailsPixKeyResponse
import br.com.zup.edu.dto.PixKeyResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import org.slf4j.LoggerFactory

@Controller("api/v1/clients/{clientId}")
class GetPixKeyController(
    @Inject val getPixKeyClient: KeyManagerGetServiceGrpc.KeyManagerGetServiceBlockingStub,
    @Inject val listPixKeyClient: KeyManagerGetAllServiceGrpc.KeyManagerGetAllServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{pixId}")
    fun getPixKeyByPixId(clientId: UUID, pixId: UUID): HttpResponse<DetailsPixKeyResponse> {
        LOGGER.info("[$clientId] looking for a key with the pixId: $pixId")

        val request = GetKeyRequest.newBuilder()
            .setPixId(FilterByPixId.newBuilder()
                .setClientId(clientId.toString())
                .setPixId(pixId.toString())
                .build()
            )
            .build()

        val response = getPixKeyClient.get(request)

        return HttpResponse.ok(DetailsPixKeyResponse(response))
    }

    @Get("/pix")
    fun listAllKeys(clientId: UUID): HttpResponse<List<PixKeyResponse>> {
        LOGGER.info("[$clientId] looking for your pix keys")

        val request = ListAllKeysRequest.newBuilder()
            .setClientId(clientId.toString())
            .build()

        val response = listPixKeyClient.getAll(request)

        return HttpResponse.ok(response.keysList.map {
            key -> PixKeyResponse(key)
        })
    }

}