package com.example.datastore.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.datastore.SavedNumbers
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object Serializer : Serializer<SavedNumbers> {
    override val defaultValue = SavedNumbers.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SavedNumbers {
        try {
            return SavedNumbers.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: SavedNumbers, output: OutputStream) =
        t.writeTo(output)

}
