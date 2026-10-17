package com.example.carsales

import androidx.annotation.DrawableRes

data class Car(
    val id: String,
    val name: String,
    val location: String,
    val price: String,
    val transmission: String,
    val fuelBadge: String,
    val year: Int,
    val mileage: String,
    val description: String,
    val isFavorite: Boolean = false,
    @DrawableRes val imageRes: Int
)

val mockCarList = listOf(
    Car(
        id = "1",
        name = "Tiggo 9 CSH",
        location = "Jakarta Selatan",
        price = "Rp 520.000.000",
        transmission = "Auto",
        fuelBadge = "PHEV",
        year = 2024,
        mileage = "5,000 km",
        description = "The Tiggo 9 CSH combines modern SUV styling with plug-in hybrid performance and luxury interior comfort.",
        isFavorite = true,
        imageRes = R.drawable.tiggo
    ),
    Car(
        id = "2",
        name = "Honda CRV",
        location = "Bandung",
        price = "Rp 185.000.000",
        transmission = "Auto",
        fuelBadge = "PETROL",
        year = 2013,
        mileage = "115,200 km",
        description = "A practical family SUV built for long distance comfort, proven engine durability, and reliability.",
        isFavorite = false,
        imageRes = R.drawable.crv
    ),
    Car(
        id = "3",
        name = "Toyota Yaris Cross",
        location = "Surabaya",
        price = "Rp 435.000.000",
        transmission = "Auto",
        fuelBadge = "HYBRID",
        year = 2024,
        mileage = "12,500 km",
        description = "Compact crossover featuring advanced hybrid efficiency, modern technology, and sleek city design.",
        isFavorite = true,
        imageRes = R.drawable.yariscross
    ),
    Car(
        id = "4",
        name = "Daihatsu Xenia",
        location = "Yogyakarta",
        price = "Rp 198.000.000",
        transmission = "Manual",
        fuelBadge = "PETROL",
        year = 2022,
        mileage = "28,000 km",
        description = "Reliable multi-purpose vehicle designed for day-to-day mobility with great fuel efficiency.",
        isFavorite = false,
        imageRes = R.drawable.xenia
    ),
    Car(
        id = "5",
        name = "Toyota Yaris",
        location = "Semarang",
        price = "Rp 245.000.000",
        transmission = "Auto",
        fuelBadge = "PETROL",
        year = 2021,
        mileage = "52,400 km",
        description = "The Toyota Yaris G AT is a sporty compact hatchback equipped with a 1.5L dual VVT-i engine and 7 airbags.",
        isFavorite = true,
        imageRes = R.drawable.yaris
    )
)