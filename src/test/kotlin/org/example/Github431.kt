package org.example

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.junit.Test
import kotlin.test.assertEquals

class TestGithub431 {
    private val mapper = jacksonObjectMapper()
        .registerModule(kotlinModule())
        .registerModule(SimpleModule().addSerializer(Long::class.java, LongSerializer))


    @Test
    fun serializeLongMapValue() {
        assertEquals("""{"longValue":"123"}""", mapper.writeValueAsString(mapOf("longValue" to 123L)))
    }
    @Test
    fun serializeDataClass() {
        assertEquals("""{"time":"123","data":"12"}""", mapper.writeValueAsString(R.success(12L)))
    }

    @Test
    fun serializeList() {
        assertEquals("""["1232"]""",mapper.writeValueAsString(listOf(1232L)))
    }
}

object LongSerializer : JsonSerializer<Long>() {
    override fun serialize(p0: Long?, p1: JsonGenerator?, p2: SerializerProvider?) {
        p1?.writeString(p0?.toString())
    }

}

class R<T> {
    val time = 123L
    var data: T? = null

    companion object {
        fun <T : Any?> success(data: T? = null): R<T> {
            val r = R<T>()
            r.data = data
            return r
        }
    }

}

