package uz.ictschool.recyclersort

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import uz.ictschool.recyclersort.ui.theme.RecyclerSortTheme
import uz.ictschool.recyclersort.ui.theme.productItemBG

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RecyclerSortTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyView(products)
                }
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyView(products:MutableList<Product>) {

    var sliderValue by remember{
        mutableStateOf(0f..300f)
    }

    var productList by remember {
        mutableStateOf(products)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        //chips ishlatildi

        productList = Chips()

        RangeSlider(
            value = sliderValue,
            onValueChange = { newValues ->
                sliderValue = newValues
            },
            onValueChangeFinished = {
                var tempProduct = mutableListOf<Product>()

                for (p in products){
                    if (p.price.toFloat() in sliderValue){
                        tempProduct.add(p)
                    }
                }
                productList = tempProduct

            },
            valueRange = 0f..300f,
            steps = 0
        )
        Text(text = "Start: ${sliderValue.start.toInt()}, " +
                "End: ${sliderValue.endInclusive.toInt()}")



        LazyColumn{
            items(productList) { product ->
                ProductView(product = product)
            }
        }
    }
}


@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chips():MutableList<Product> {

    var selectedCategory by remember {
        mutableStateOf(categories[0])
    }

    var productList by remember {
        mutableStateOf(products)
    }

    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(categories) { item ->
            FilterChip(
                modifier = Modifier.padding(horizontal = 6.dp), // gap between items
                selected = (item == selectedCategory),
                onClick = {
                    selectedCategory = item

                    var tempProducts = mutableListOf<Product>()

                    for (p in products){
                        if (p.category == selectedCategory){
                            tempProducts.add(p)
                        }
                    }

                    productList = tempProducts

                },
                label = {
                    Text(text = item)
                }
            )
        }
    }

    return productList
}


@Composable
fun ProductView(product: Product){

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)){

        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(productItemBG)
                    .padding(5.dp)
            ) {
                Text(text = product.name,
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
        MyView(products)
    }
}