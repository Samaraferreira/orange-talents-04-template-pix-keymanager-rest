package br.com.zup.edu.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class KeyTypeRequestTest {

    @Nested
    inner class RANDOM_KEY {
        @Test
        fun `should be valid if random key is null or blank`() {
            with(KeyTypeRequest.RANDOM_KEY) {
                assertTrue(isValid(null))
                assertTrue(isValid(""))
            }
        }

        @Test
        fun `should not be valid if random key is not null or blank`() {
            with(KeyTypeRequest.RANDOM_KEY) {
                assertFalse(isValid("uma chave qualquer"))
            }
        }
    }

    @Nested
    inner class EMAIL {
        @Test
        fun `should be valid if email key is valid`() {
            with(KeyTypeRequest.EMAIL) {
                assertTrue(isValid("random@email.com"))
            }
        }

        @Test
        fun `should not be valid if email key is null or blank`() {
            with(KeyTypeRequest.EMAIL) {
                assertFalse(isValid(""))
                assertFalse(isValid(null))
            }
        }

        @Test
        fun `should not be valid if email key is invalid`() {
            with(KeyTypeRequest.EMAIL) {
                assertFalse(isValid("uma chave qualquer"))
            }
        }
    }
}
