package com.example.carsales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CarSalesApp()
            }
        }
    }
}

@Composable
fun CarSalesApp() {
    var cars by remember { mutableStateOf(mockCarList) }
    var selectedCar by remember { mutableStateOf<Car?>(null) }

    val onToggleFavorite: (Int) -> Unit = { index ->
        cars = cars.toMutableList().also {
            it[index] = it[index].copy(isFavorite = !it[index].isFavorite)
        }
    }

    Scaffold(
        containerColor = Color(0xFFF6F6F6)
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if (selectedCar == null) {
                CarListScreen(
                    cars = cars,
                    onToggleFavorite = onToggleFavorite,
                    onCarClick = { car -> selectedCar = car }
                )
            } else {
                CarDetailScreen(
                    car = selectedCar!!,
                    onBackClick = { selectedCar = null }
                )
            }
        }
    }
}

@Composable
fun CarListScreen(
    cars: List<Car>,
    onToggleFavorite: (Int) -> Unit,
    onCarClick: (Car) -> Unit
) {
    val savedCount = cars.count { it.isFavorite }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "On Sale",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Find your perfect ride",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFFFEAEA),
                contentColor = Color(0xFFFF4D4D)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$savedCount saved",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(cars) { index, car ->
                CarCardItem(
                    car = car,
                    onToggleFavorite = { onToggleFavorite(index) },
                    onCardClick = { onCarClick(car) }
                )
            }
        }
    }
}

@Composable
fun CarCardItem(
    car: Car,
    onToggleFavorite: () -> Unit,
    onCardClick: () -> Unit
) {
    val context = LocalContext.current
    val bitmap = remember(car.imageRes) {
        loadBitmapFromResource(context, car.imageRes)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ListItem(
            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 75.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = car.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(4.dp),
                        shape = RoundedCornerShape(4.dp),
                        color = Color.White.copy(alpha = 0.9f)
                    ) {
                        Text(
                            text = car.fuelBadge,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (car.fuelBadge) {
                                "HYBRID" -> Color(0xFF00796B)
                                "PHEV" -> Color(0xFF5E35B1)
                                else -> Color(0xFFE65100)
                            },
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            },
            headlineContent = {
                Text(
                    text = car.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            supportingContent = {
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFF0F0F0)
                    ) {
                        Text(
                            text = car.transmission,
                            fontSize = 11.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "• ${car.year}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = car.mileage,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            },
            trailingContent = {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (car.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (car.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        )
    }
}

@Composable
fun CarDetailScreen(
    car: Car,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val bitmap = remember(car.imageRes) {
        loadBitmapFromResource(context, car.imageRes)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = car.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White, shape = RoundedCornerShape(50))
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = car.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = car.location,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = car.price,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF4D4D)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "SPECIFICATIONS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SpecItem("YEAR", car.year.toString(), Modifier.weight(1f))
                    SpecItem("MILEAGE", car.mileage, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SpecItem("TRANSMISSION", car.transmission, Modifier.weight(1f))
                    SpecItem("FUEL TYPE", car.fuelBadge, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "ABOUT THIS CAR",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = car.description,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )
            }
        }

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4D4D)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
        ) {
            Text(
                text = "Contact Seller",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SpecItem(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFEFEFEF)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = label, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}