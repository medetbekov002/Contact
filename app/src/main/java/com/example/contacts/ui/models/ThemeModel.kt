package com.example.contacts.ui.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.contacts.enums.ThemeMode
import com.example.contacts.util.Preferences

class ThemeModel : ViewModel() {
    var themeMode by mutableStateOf(
        ThemeMode.fromInt(
            Preferences.getInt(Preferences.themeKey, ThemeMode.SYSTEM.value)
        )
    )
    var collapsableBottomBar by mutableStateOf(
        Preferences.getBoolean(Preferences.collapseBottomBarKey, false)
    )
    var colorfulIcons by mutableStateOf(
        Preferences.getBoolean(Preferences.colorfulContactIconsKey, false)
    )
}
