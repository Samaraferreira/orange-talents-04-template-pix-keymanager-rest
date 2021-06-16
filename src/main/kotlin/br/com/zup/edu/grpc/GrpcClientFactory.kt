package br.com.zup.edu.grpc

import br.com.zup.edu.KeyManagerDeleteServiceGrpc
import br.com.zup.edu.KeyManagerGetAllServiceGrpc
import br.com.zup.edu.KeyManagerGetServiceGrpc
import br.com.zup.edu.KeyManagerRegisterServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("keyManager")val channel: ManagedChannel) {

    @Singleton
    fun registerKey() = KeyManagerRegisterServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deleteKey() = KeyManagerDeleteServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listKeys() = KeyManagerGetAllServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun getKey() = KeyManagerGetServiceGrpc.newBlockingStub(channel)
}