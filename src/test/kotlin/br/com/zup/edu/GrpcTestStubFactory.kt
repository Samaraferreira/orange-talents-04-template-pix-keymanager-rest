package br.com.zup.edu

import br.com.zup.edu.shared.grpc.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import org.mockito.Mockito
import javax.inject.Singleton

@Factory
@Replaces(factory = GrpcClientFactory::class)
@Suppress("unused")
class GrpcTestStubFactory {

    @Singleton
    fun stubRegisterKey(): KeyManagerRegisterServiceGrpc.KeyManagerRegisterServiceBlockingStub =
        Mockito.mock(KeyManagerRegisterServiceGrpc.KeyManagerRegisterServiceBlockingStub::class.java)

    @Singleton
    fun stubDeleteService(): KeyManagerDeleteServiceGrpc.KeyManagerDeleteServiceBlockingStub =
        Mockito.mock(KeyManagerDeleteServiceGrpc.KeyManagerDeleteServiceBlockingStub::class.java)

    @Singleton
    fun stubGetService(): KeyManagerGetServiceGrpc.KeyManagerGetServiceBlockingStub =
        Mockito.mock(KeyManagerGetServiceGrpc.KeyManagerGetServiceBlockingStub::class.java)

    @Singleton
    fun stubGetAllService(): KeyManagerGetAllServiceGrpc.KeyManagerGetAllServiceBlockingStub =
        Mockito.mock(KeyManagerGetAllServiceGrpc.KeyManagerGetAllServiceBlockingStub::class.java)

}