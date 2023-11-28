package uz.ictschool.recyclersort.model

data class Product(
    var name: String,
    var price: Int,
    var category: String
)

public var products = mutableListOf<Product>(
    Product(
        "Computer",
        8000,
        "Texnika"
    ),

    Product(
        "Choy",
        16,
        "Ovqat"
    ),

    Product(
        "Divan",
        354,
        "Mebel"
    ),

    Product(
        "Televizor",
        287,
        "Texnika"
    )
)