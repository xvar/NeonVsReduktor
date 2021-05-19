package com.github.xvar.neon.reduktor.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.xvar.neon.reduktor.R
import com.github.xvar.neon.reduktor.ui.theme.NeonVsReduktorTheme
import com.github.xvar.neon.reduktor.ui.util.instanceDebug

private val buttonPadding = 32.dp

@Composable
fun HomeUI(
    vm: HomeVm = viewModel()
) {
    vm.instanceDebug("home")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
        ) {

            ChooseButton(
                resId = R.string.name_neon,
                action = { vm.consume(HomeVm.Click.NeonButton("neon title")) }
            )

            ChooseButton(
                resId = R.string.name_reduktor,
                action = { vm.consume(HomeVm.Click.ReduktorButton("reduktor title")) }
            )

            ChooseButton(
                modifier = Modifier.padding(bottom = buttonPadding),
                resId = R.string.name_rx_redux,
                action = { vm.consume(HomeVm.Click.RxRedux()) }
            )

        }
    }
}

@Composable
private fun ChooseButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    resId : Int,
    action: () -> Unit = {}
) {
    Button(
        onClick = { action() },
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = buttonPadding, start = buttonPadding, end = buttonPadding)
    ) {
        Text(text = stringResource(id = resId))
    }
}

@Composable
@Preview
private fun HomePreview() {
    NeonVsReduktorTheme {
        HomeUI()
    }
}
