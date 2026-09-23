package `in`.koreatech.business.data.mapper

import `in`.koreatech.business.data.response.ErrorResponse
import `in`.koreatech.business.data.response.FieldErrorResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class ErrorMapperTest {
    @Test
    fun errorResponsePreservesServerErrorInformation() {
        val exception = ErrorResponse(
            code = "INVALID_REQUEST",
            message = "입력값이 올바르지 않습니다.",
            errorTraceId = "trace-id",
            fieldErrors = listOf(
                FieldErrorResponse(
                    field = "phone",
                    message = "전화번호 형식이 올바르지 않습니다.",
                    constraint = "Pattern"
                )
            )
        ).toApiException(
            statusCode = 400,
            fallbackMessage = "Bad Request"
        )

        assertEquals(400, exception.statusCode)
        assertEquals("INVALID_REQUEST", exception.code)
        assertEquals("입력값이 올바르지 않습니다.", exception.message)
        assertEquals("trace-id", exception.errorTraceId)
        assertEquals("phone", exception.fieldErrors.single().field)
        assertEquals("Pattern", exception.fieldErrors.single().constraint)
    }
}
