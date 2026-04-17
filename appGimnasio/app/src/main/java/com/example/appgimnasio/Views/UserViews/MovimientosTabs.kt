package com.example.appgimnasio.Views.UserViews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.appgimnasio.Model.Items_movimientos
import com.example.appgimnasio.ViewModels.GimnasioViewModel
import com.example.appgimnasio.util.Util
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovimientosTabs(array: Array<Double>,array2: Array<Double>,gimnasioVM: GimnasioViewModel,navController: NavController){
    val tabs = listOf(
        Items_movimientos.tab_usuarios,
        Items_movimientos.tab_gimnasio,
        Items_movimientos.tab_update
    )
    val pagerState = rememberPagerState(pageCount = {tabs.size})
    Column(modifier = Modifier.background(Color.White).fillMaxSize()) {
        Tabs(tabs,pagerState)
        TabsContent(tabs,pagerState,array,array2,gimnasioVM,navController)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(tabs: List<Items_movimientos>, pagerState: PagerState,array: Array<Double>,array2: Array<Double>,gimnasioVM: GimnasioViewModel, navController: NavController) {
    HorizontalPager(state = pagerState) {
        page->
        tabs[page].screen(array,array2,gimnasioVM,navController)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(tabs: List<Items_movimientos>, pagerState: PagerState) {
    var selectedTab = pagerState.currentPage
    var scope = rememberCoroutineScope()
    TabRow(selectedTabIndex = selectedTab, modifier = Modifier.background(Color.White)) {
        tabs.forEachIndexed { index, items ->
            Tab(
                selected = selectedTab == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = { Text(items.titulo) },
                icon ={
                    Icon(imageVector =
                        if (index == selectedTab) items.iconSelected else items.iconUnSelected,
                        contentDescription = items.titulo,
                        tint = Util().seleccionarColor(index == selectedTab)
                    )}
            )
        }
    }
}