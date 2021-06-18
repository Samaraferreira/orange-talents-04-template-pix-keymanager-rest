package br.com.zup.edu.shared.grpc

import br.com.zup.edu.KeyManagerDeleteServiceGrpc
import br.com.zup.edu.KeyManagerGetAllServiceGrpc
import br.com.zup.edu.KeyManagerGetServiceGrpc
import br.com.zup.edu.KeyManagerRegisterServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun register() = KeyManagerRegisterServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun delete() = KeyManagerDeleteServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun getAll() = KeyManagerGetAllServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun get() = KeyManagerGetServiceGrpc.newBlockingStub(channel)
}