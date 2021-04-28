package com.github.xvar.neon.reduktor.ui.screen.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.xvar.neon.reduktor.R
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import com.github.xvar.neon.reduktor.ui.theme.NeonVsReduktorTheme

@Composable
fun CounterScreen(
    state: State<CounterViewState>,
    onCounterClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            val viewState = state.value
            if (viewState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp)
                )
            } else {
                Text(
                    text = "${viewState.counter}",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = viewState.someScreenDescription,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        FloatingActionButton(
            onClick = { onCounterClick() },
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.inc_counter)
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
private fun CounterScreenPreview() {
    NeonVsReduktorTheme {
        CounterScreen(
            state = mutableStateOf(CounterViewState(0)),
            onCounterClick = {}
        )
    }
}