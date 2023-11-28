package uz.ictschool.recyclersort.model

data class Product(
    var name: String,
    var price: Int,
    var category: String
)

val categories = listOf<String>(
    "Texnika",
    "Ovqat",
    "Mebel",
    "Kiyim"
)

public var products = mutableListOf<Product>(
    Product(
        "Komputer",
        789,
        categories[0]
    ),

    Product(
        "Choy",
        16,
        categories[1]
    ),

    Product(
        "Etik",
        68,
        categories[3]
    ),

    Product(
        "Divan",
        354,
        categories[2]
    ),

    Product(
        "Televizor",
        287,
        categories[0]
    ),

    Product(
        "Makaron",
        25,
        categories[1]
    ),

    Product(
        "Shkaf",
        145,
        categories[2]
    )
)