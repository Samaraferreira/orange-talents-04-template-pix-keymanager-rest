package br.com.zup.edu.shared

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GlobalExceptionHandlerTest {

    val requestGenerica = HttpRequest.GET<Any>("/")

    @Test
    fun `should return 404 if statusException is not found`() {

        val message = "not found"
        val notFoundException = StatusRuntimeException(Status.NOT_FOUND.withDescription(message))

        val response = GlobalExceptionHandler().handle(requestGenerica, notFoundException)

        assertEquals(HttpStatus.NOT_FOUND, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    fun `should return 400 if statusException is invalid argument`() {

        val message = "Invalid argument"
        val invalidArgumentException = StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(message))

        val response = GlobalExceptionHandler().handle(requestGenerica, invalidArgumentException)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    fun `should return 422 if statusException is already exists`() {

        val message = "already exists"
        val alreadyExistsException = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(message))

        val response = GlobalExceptionHandler().handle(requestGenerica, alreadyExistsException)

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status)
        assertNotNull(response.body())
        assertEquals(message, (response.body() as JsonError).message)
    }

    @Test
    fun `should return 500 when any other error thrown`() {

        
        val internalException = StatusRuntimeException(Status.INTERNAL)

        val response = GlobalExceptionHandler().handle(requestGenerica, internalException)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)
        assertNotNull(response.body())
        assertTrue((response.body() as JsonError).message.contains("INTERNAL"))
    }
}
