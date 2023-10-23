package com.example.datastore.model

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.toUpperCase
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import com.example.datastore.SavedNumbers
import com.example.datastore.data.Serializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.savedNumbers: DataStore<SavedNumbers> by dataStore(
    fileName = "saved_numbers",
    serializer = Serializer
)

class DatastoreViewModel(
    private val context: Context
) : ViewModel() {

    private val dataStoreFlow: Flow<SavedNumbers> = context.savedNumbers.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("ERROR", "reading preferences", exception)
                emit(SavedNumbers.getDefaultInstance())
            } else {
                throw exception
            }
        }


    val currentInt = dataStoreFlow.map { it.int }
    val currentStringInt = dataStoreFlow.map { it.stringInt }


    suspend fun updateInt() {
        context.savedNumbers.updateData { current ->
            current.toBuilder()
                .setInt(current.int + 1)
                .build()
        }
    }

    suspend fun updateStringInt() {
        context.savedNumbers.updateData {
            it.toBuilder().setStringInt(updateStringNumber(it.stringInt)).build()
        }
    }

    private fun updateStringNumber(input: String): String {
        return when (input.uppercase()) {
            "", "NINE" -> "One"
            "ONE" -> "Two"
            "TWO" -> "Three"
            "THREE" -> "Four"
            "FOUR" -> "Five"
            "FIVE" -> "Six"
            "SIX" -> "Seven"
            "SEVEN" -> "Eight"
            "EIGHT" -> "Nine"
            else -> "WHOOPS!!!"
        }
    }
}
