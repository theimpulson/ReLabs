package io.aayush.relabs.network.data.user

import androidx.compose.ui.graphics.Color
import io.aayush.relabs.ui.theme.ColorAdmin
import io.aayush.relabs.ui.theme.ColorFSM
import io.aayush.relabs.ui.theme.ColorRC
import io.aayush.relabs.ui.theme.ColorRD
import io.aayush.relabs.ui.theme.ColorSM

sealed class UserTitle(val color: Color) {
    data object Admin : UserTitle(color = ColorAdmin)
    data object SM : UserTitle(color = ColorSM)
    data object FSM : UserTitle(color = ColorFSM)
    data object RC : UserTitle(color = ColorRC)
    data object RD : UserTitle(color = ColorRD)
    data object Member : UserTitle(color = Color.White)
}
