package uz.ictschool.recyclersort.model

data class Product(
    var name: String,
    var price: Int,
    var category: String
)

val categories = listOf<String>(
    "Hammasi",
    "Texnika",
    "Ovqat",
    "Mebel",
    "Kiyim"
)
public var products = mutableListOf<Product>(
    Product(
        "Komputer",
        789,
        categories[1]
    ),

    Product(
        "Choy",
        16,
        categories[2]
    ),

    Product(
        "Etik",
        68,
        categories[4]
    ),

    Product(
        "Divan",
        354,
        categories[3]
    ),

    Product(
        "Televizor",
        287,
        categories[1]
    ),

    Product(
        "Makaron",
        25,
        categories[2]
    ),

    Product(
        "Shkaf",
        145,
        categories[3]
    )
)

var sliderRangeValue = 0f..1000f