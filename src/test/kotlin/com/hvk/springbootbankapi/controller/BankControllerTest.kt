package com.hvk.springbootbankapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.hvk.springbootbankapi.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper
) {

    private val baseUrl: String = "/api/v1/banks"

    @Nested
    @DisplayName("GET /api/v1/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks`() {
            //when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("123") }
                }
        }
    }


    @Nested
    @DisplayName("GET /api/v1/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank with given account number`() {
            //given
            val accountNumber = 123

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("1.0") }
                    jsonPath("$.transactionFee") { value("1") }
                }
        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            //given
            val accountNumber = "does_not_exist"

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }


    @Nested
    @DisplayName("POST /api/v1/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {

        @Test
        fun `should add the new bank`() {
            //given
            val newBank = Bank("new123", 1.23, 0)

            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }
        }

        @Test
        fun `should return BAD REQUEST if the bank with given account number already exists`() {
            //given
            val invalidBank = Bank("123", 1.0, 1)

            //when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }


    @Nested
    @DisplayName("PATCH /api/v1/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistBank {

        @Test
        fun `should update an exist banks`() {
            //given
            val updatedBank = Bank("123", 1.0, 1)

            //when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)

            }

            //then
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }


        }

        @Test
        fun `should return BAD REQUEST if not bank with given account number exists`() {
            //given
            val invalidBank = Bank("does_not_exist", 1.0, 1)

            //when
            val performPatchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }

    }

    @Nested
    @DisplayName("DELETE /api/v1/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistBank {

        @Test
        fun `should return the bank with given account number`() {
            //given
            val accountNumber = 123

            //when/then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNoContent() } }
        }

        @Test
        fun `should return NOT FOUND if the bank with given account number does not exist`() {
            //given
            val invalidAccountNumber = "does_not_exist"

            //when/then
            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

    }
}