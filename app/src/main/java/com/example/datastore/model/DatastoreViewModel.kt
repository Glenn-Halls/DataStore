package com.example.datastore.model

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.toUpperCase
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastore.SavedNumbers
import com.example.datastore.SavedNumbers.Number
import com.example.datastore.SavedNumbers.getDefaultInstance
import com.example.datastore.data.Serializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.IllegalArgumentException

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
                emit(getDefaultInstance())
            } else if (exception is IllegalArgumentException) {
//                val enum = context.savedNumbers.data.map { it.number }
                Log.e("Illegal", "Enum", exception)
                emit(getDefaultInstance())
            } else throw exception
        }



    val currentInt = dataStoreFlow.map { it.int }
    val currentStringInt = dataStoreFlow.map { it.stringInt }
    val currentNumber = dataStoreFlow.map { it.number }


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

    private fun getRandomEnum(): Number {
        val enumList = Number.entries.toList()
        val bannedEnums = listOf(
            Number.NUMBER_UNSPECIFIED,
            Number.UNKNOWN,
            Number.UNRECOGNIZED
        )
        val filteredEnums = enumList.minus(bannedEnums)
        return filteredEnums.random()
    }


    suspend fun updateNumber() {
        val newNumber = getRandomEnum()
        context.savedNumbers.updateData {
            it.toBuilder().setNumber(newNumber).build()
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
