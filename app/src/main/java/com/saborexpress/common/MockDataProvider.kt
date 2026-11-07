package com.saborexpress.common

/**
 * Modelos de datos para mockups y pruebas en SaborExpress.
 */

data class Dish(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val cookTimeMinutes: Int,
    var quantity: Int = 1
) {
    fun formattedPrice(): String = String.format("$ %,d COP", price.toLong()).replace(',', '.')
}

data class Order(
    val id: String,
    val tableNumber: String,
    val arrivalTime: String,
    val customerName: String,
    val dishesSummary: String,
    val total: Double,
    var status: String // "Pendiente", "En Preparación", "Lista"
)

data class UserItem(
    val id: String,
    val fullName: String,
    val email: String,
    val documentId: String,
    val lastBooking: String,
    val roleBadge: String, // "VIP", "Cliente Frecuente", "Nuevo"
    var isSuspended: Boolean = false
)

object MockDataProvider {

    // Platos del menú gastronómico
    val catalogDishes = listOf(
        Dish(
            id = "d1",
            name = "Lomo al Trapo Tradicional",
            description = "Corte fino de lomo envuelto en lienzo con costra de sal marina y finas hierbas de campo.",
            price = 48500.0,
            category = "Platos Fuertes y Cortes",
            cookTimeMinutes = 25
        ),
        Dish(
            id = "d2",
            name = "Punta de Anca Angus al Carbón",
            description = "Jugosa carne madurada a la brasa con papas rústicas, chimichurri artesanal y ensalada.",
            price = 52000.0,
            category = "Platos Fuertes y Cortes",
            cookTimeMinutes = 20
        ),
        Dish(
            id = "d3",
            name = "Empanaditas Criollas Gourmet",
            description = "Porción de 5 empanadas crocantes de carne desmechada con ají casero y guacamole.",
            price = 18000.0,
            category = "Entradas y Aperitivos",
            cookTimeMinutes = 12
        ),
        Dish(
            id = "d4",
            name = "Ceviche de Camarón Caribeño",
            description = "Camarones frescos marinados en zumo de lima, cilantro cimarrón y tostones de plátano.",
            price = 32000.0,
            category = "Entradas y Aperitivos",
            cookTimeMinutes = 10
        ),
        Dish(
            id = "d5",
            name = "Limonada de Coco Natural",
            description = "Refrescante bebida batida con crema de coco natural, hielo picado y zumo de limón mandarino.",
            price = 14500.0,
            category = "Bebidas y Coctelería",
            cookTimeMinutes = 5
        ),
        Dish(
            id = "d6",
            name = "Postre Tres Leches Artesanal",
            description = "Bizcochuelo esponjoso bañado en salsa de tres leches con canela y merengue suizo.",
            price = 16000.0,
            category = "Postres Artesanales",
            cookTimeMinutes = 5
        )
    )

    // Platos precargados en la Preorden / Carrito
    val initialCartDishes = mutableListOf(
        catalogDishes[0].copy(quantity = 2), // 2x Lomo al trapo
        catalogDishes[2].copy(quantity = 1), // 1x Empanaditas
        catalogDishes[4].copy(quantity = 2)  // 2x Limonada de coco
    )

    // Comandas activas para el Pase de Cocina (Vendedor)
    val ordersList = mutableListOf(
        Order(
            id = "ord-101",
            tableNumber = "Mesa #04",
            arrivalTime = "Llegada: 7:30 PM (en 15 min)",
            customerName = "Cliente: Marlon Yecid Valderrama",
            dishesSummary = "• 2x Lomo al Trapo Tradicional (Término medio)\n• 1x Empanaditas Criollas Gourmet\n• 2x Limonada de Coco Natural",
            total = 144000.0,
            status = "Pendiente"
        ),
        Order(
            id = "ord-102",
            tableNumber = "Mesa #08",
            arrivalTime = "Llegada: 7:45 PM (en 30 min)",
            customerName = "Cliente: Laura Gómez Pardo",
            dishesSummary = "• 1x Punta de Anca Angus al Carbón\n• 1x Ceviche de Camarón Caribeño\n• 1x Copa de Vino Tinto Reserva",
            total = 105000.0,
            status = "Pendiente"
        ),
        Order(
            id = "ord-103",
            tableNumber = "Mesa #02",
            arrivalTime = "Llegada: 7:15 PM (En sala)",
            customerName = "Cliente: Andrés Felipe Mejía",
            dishesSummary = "• 2x Punta de Anca Angus\n• 2x Cerveza Artesanal IPA",
            total = 128000.0,
            status = "En Preparación"
        ),
        Order(
            id = "ord-104",
            tableNumber = "Mesa #12",
            arrivalTime = "Llegada: 7:00 PM (En mesa)",
            customerName = "Cliente: Diana Marcela Cárdenas",
            dishesSummary = "• 1x Lomo al Trapo Tradicional\n• 1x Postre Tres Leches Artesanal",
            total = 64500.0,
            status = "Lista"
        )
    )

    // Usuarios para el Panel de Administrador
    val usersList = mutableListOf(
        UserItem(
            id = "usr-01",
            fullName = "Marlon Yecid Valderrama",
            email = "marlon.valderrama@correo.com",
            documentId = "C.C. 1.023.456.789",
            lastBooking = "Última reserva: Hoy, 7:30 PM (Sede Parque 93)",
            roleBadge = "VIP"
        ),
        UserItem(
            id = "usr-02",
            fullName = "Laura Sofía Gómez",
            email = "laura.gomez@gmail.com",
            documentId = "C.C. 1.018.987.654",
            lastBooking = "Última reserva: Ayer, 8:00 PM (Sede Usaquén)",
            roleBadge = "Cliente Frecuente"
        ),
        UserItem(
            id = "usr-03",
            fullName = "Andrés Felipe Mejía",
            email = "andres.mejia@hotmail.com",
            documentId = "C.C. 80.123.456",
            lastBooking = "Última reserva: Hace 3 días (Sede Parque 93)",
            roleBadge = "VIP"
        ),
        UserItem(
            id = "usr-04",
            fullName = "Diana Marcela Cárdenas",
            email = "diana.cardenas@outlook.com",
            documentId = "C.C. 52.876.543",
            lastBooking = "Última reserva: Hace 1 semana (Sede Zona T)",
            roleBadge = "Nuevo"
        ),
        UserItem(
            id = "usr-05",
            fullName = "Carlos Alberto Morales",
            email = "carlos.morales@empresa.com",
            documentId = "C.C. 79.456.123",
            lastBooking = "Sin reservas recientes",
            roleBadge = "Inactivo",
            isSuspended = true
        )
    )
}

