package com.example.a120422

sealed class Item {

    data class Count(val value: Int): Item()

    object Loading : Item()

}