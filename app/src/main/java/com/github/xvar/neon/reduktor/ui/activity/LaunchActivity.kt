package com.github.xvar.neon.reduktor.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.github.xvar.neon.reduktor.domain.navigation.Router
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent
import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen
import com.github.xvar.neon.reduktor.ui.AppRouter
import com.github.xvar.neon.reduktor.ui.screen.home.HomeUI
import com.github.xvar.neon.reduktor.ui.screen.home.HomeVm
import com.github.xvar.neon.reduktor.ui.screen.neon.NeonUI
import com.github.xvar.neon.reduktor.domain.reduktor.ReduktorInitData
import com.github.xvar.neon.reduktor.ui.screen.neon.NeonVm
import com.github.xvar.neon.reduktor.ui.screen.neon.NeonVmNewInstanceProvider
import com.github.xvar.neon.reduktor.ui.screen.reduktor.ReduktorUI
import com.github.xvar.neon.reduktor.ui.screen.reduktor.ReduktorVm
import com.github.xvar.neon.reduktor.ui.screen.reduktor.ReduktorVmNewInstanceProvider
import com.github.xvar.neon.reduktor.ui.screen.rxredux.RxReduxUI
import com.github.xvar.neon.reduktor.ui.screen.rxredux.RxReduxVm
import com.github.xvar.neon.reduktor.ui.screen.rxredux.RxReduxVmNewInstanceProvider
import com.github.xvar.neon.reduktor.ui.theme.NeonVsReduktorTheme
import com.github.xvar.neon.reduktor.ui.util.instanceDebug
import com.github.xvar.neon.reduktor.ui.util.viewModelProviderFactoryOf
import io.reactivex.Flowable

class LaunchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeonVsReduktorTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    //Проблема №1 - Навигация с инъекцией зависимостей
    //Вероятно, решается через attach/detach
    val navController = rememberNavController()
    val appRouter: Router = remember { AppRouter(navController) }.instanceDebug("main")

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = AppScreen.Home.destination,
        ) {
            composable(AppScreen.Home.destination) {
                //Особенности composable-функций - recompose может быть вызван любое кол-во раз
                val vm = viewModel<HomeVm>().instanceDebug("main")
                //подписка на route-events
                DisposableRouterEffect(router = appRouter, events = vm.routeEvents)
                HomeUI(vm)
            }
            composable(AppScreen.Neon.destination) {
                //Забил на передачу параметров (не слишком отличается от reduktor)
                val vm = viewModel<NeonVm>(factory = viewModelProviderFactoryOf {
                    NeonVmNewInstanceProvider()
                        .get()
                }).instanceDebug("main")

                DisposableRouterEffect(router = appRouter, events = vm.routeEvents)
                NeonUI(vm)
            }
            composable(
                route = AppScreen.Reduktor.destination,
                arguments = listOf(
                    navArgument(AppScreen.Reduktor.Args.counter) { type = NavType.IntType },
                    navArgument(AppScreen.Reduktor.Args.title) {
                        defaultValue = AppScreen.Reduktor.Args.defaultTitle
                    }
                )
            ) {
                //получение параметров (вариантов вроде нет)
                val initCounter =
                    requireNotNull(it.arguments?.getInt(AppScreen.Reduktor.Args.counter))
                val initTitle = it.arguments?.getString(AppScreen.Reduktor.Args.title) ?: "no title"

                //можно посмотреть в сторону decompose и иерархии по SaveStateHandler
                val ctx = LocalContext.current
                val initData = ReduktorInitData(initCounter, initTitle)
                val vm = viewModel<ReduktorVm>(factory = viewModelProviderFactoryOf {
                    ReduktorVmNewInstanceProvider(ctx)
                        .get(initData)
                }
                )

                DisposableRouterEffect(router = appRouter, events = vm.routeEvents)
                ReduktorUI(vm)
            }
            composable(
                route = AppScreen.RxRedux.destination,
                arguments = listOf(
                    navArgument(AppScreen.RxRedux.Args.counter) { type = NavType.IntType }
                )
            ) {
                val initCounter =
                    requireNotNull(it.arguments?.getInt(AppScreen.RxRedux.Args.counter))
                val vm = viewModel<RxReduxVm>(factory = viewModelProviderFactoryOf {
                    RxReduxVmNewInstanceProvider().get(initCounter = initCounter)
                })
                DisposableRouterEffect(router = appRouter, events = vm.routeEvents)
                RxReduxUI(vm)
            }
        }
    }
}

@Composable
@NonRestartableComposable
private fun DisposableRouterEffect(
    router: Router,
    events: Flowable<RouteEvent>
) {
    DisposableEffect(router) {
        val disposable = events.subscribe {
            router.handle(it)
        }
        onDispose { disposable.dispose() }
    }
}