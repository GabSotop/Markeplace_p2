package com.evalenzuela.navigation.data.repository

import com.evalenzuela.navigation.data.model.Item
import kotlinx.coroutines.delay

class SampleRepository {

    private val sampleItems = listOf(
        Item(
            1,
            "Laptop Gamer",
            "Alto rendimiento...",
            "$1.299.990",
            "https://storage-asset.msi.com/global/picture/news/2024/nb/nb-20240103-1.jpg"


        ),
        Item(
            2,
            "Monitor Curvo 27\"",
            "Ideal para diseño...",
            "$349.500",
            ""

        ),
        Item(
            3,
            "Teclado Mecánico",
            "Switches rojos...",
            "$89.990",
            ""


        ),
        Item(
            4,
            "Mouse Inalámbrico",
            "Ergonómico, 16000 DPI.",
            "$35.750",
            ""

        ),
        Item(
            5,
            "Disco SSD 1TB",
            "Velocidad ultrarrápida.",
            "$79.990",
            ""


        )
    )

    suspend fun getAll(): List<Item> {
        delay(500)
        return sampleItems
    }

    fun getById(id: Int): Item? = sampleItems.find { it.id == id }
}