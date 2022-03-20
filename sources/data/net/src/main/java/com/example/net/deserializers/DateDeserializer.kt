package com.example.net.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer : JsonDeserializer<Date>() {

    override fun deserialize(parser: JsonParser?, ctxt: DeserializationContext?): Date =
        if (parser == null)
            Date(0)
        else {
            val deserializable = parser.codec.readValue(parser, String::class.java)
            try {
                val formatter =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply { isLenient = false }

                if (deserializable.isEmpty())
                    Date(0)
                else formatter.parse(deserializable)
            } catch (pEx: ParseException) {
                Date(0)
            }
        }
}