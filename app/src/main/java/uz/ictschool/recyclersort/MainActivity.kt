package uz.ictschool.recyclersort

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.ictschool.recyclersort.model.Product
import uz.ictschool.recyclersort.model.categories
import uz.ictschool.recyclersort.model.products
import uz.ictschool.recyclersort.model.sliderRangeValue
import uz.ictschool.recyclersort.ui.theme.RecyclerSortTheme
import uz.ictschool.recyclersort.ui.theme.productItemBG

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RecyclerSortTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MyView()
                }
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MyView() {

    var categorisedProducts by remember {
        mutableStateOf(products)
    }
    var sortedProducts by remember {
        mutableStateOf(categorisedProducts)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        categorisedProducts = Chips()

        sortedProducts = RangeSliderView(categorisedProducts)

LazyColumn {
            items(sortedProducts) { product ->
                ProductView(product = product)
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chips(

): MutableList<Product> {

    var selectedCategory by remember {
        mutableStateOf(categories[0])
    }

    var productList by remember {
        mutableStateOf(products)
    }

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(categories) { item ->
            FilterChip(modifier = Modifier.padding(horizontal = 6.dp), // gap between items
                selected = (item == selectedCategory), onClick = {
                    selectedCategory = item

                    var tempProducts = mutableListOf<Product>()

                    for (p in products) {
                        if (selectedCategory == categories[0]) {
                            tempProducts = products
                        } else if (selectedCategory == p.category) {
                            tempProducts.add(p)
                        }
                    }

                    productList = sortByPrice(sliderRangeValue, tempProducts)

                }, label = {
                    Text(text = item)
                })
        }
    }
    return productList
}

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangeSliderView(
    categorisedProducts: MutableList<Product>
): MutableList<Product> {

    var productList by remember {
        mutableStateOf(products)
    }
    productList = categorisedProducts

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        RangeSlider(
            value = sliderRangeValue, onValueChange = { newValues ->
                sliderRangeValue = newValues
            }, onValueChangeFinished = {

                productList = sortByPrice(sliderRangeValue, categorisedProducts)

            }, valueRange = 0f..1000f, steps = 0
        )
        Text(
            text = "Start: ${sliderRangeValue.start.toInt()}, " +
                    "End: ${sliderRangeValue.endInclusive.toInt()}"
        )
    }
    return productList
}

fun sortByPrice(
    range: ClosedFloatingPointRange<Float>,
    list: MutableList<Product>):MutableList<Product> {

    var tempProducts = mutableListOf<Product>()

    for (p in list){
        if (p.price.toFloat() in range){
            tempProducts.add(p)
        }
    }
    return tempProducts
}

@Composable
fun ProductView(product: Product) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(productItemBG)
                    .padding(vertical = 5.dp, horizontal = 10.dp)
            ) {
                Text(
                    text = product.name,
                    fontSize = 25.sp,
                )
                Text(text = "Price: ${product.price}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecyclerSortTheme {
        MyView()
    }
}