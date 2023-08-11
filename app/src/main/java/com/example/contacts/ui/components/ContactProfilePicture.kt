package com.example.contacts.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.contacts.R
import com.example.contacts.obj.ContactData
import com.example.contacts.ui.components.base.ClickableIcon
import com.example.contacts.ui.components.base.FullScreenDialog
import com.example.contacts.ui.components.base.ZoomableImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactProfilePicture(
    contact: ContactData,
    onDismissRequest: () -> Unit
) {
    FullScreenDialog(onClose = onDismissRequest) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        ClickableIcon(
                            icon = Icons.Default.ArrowBack,
                            contentDescription = R.string.okay
                        ) {
                            onDismissRequest.invoke()
                        }
                    },
                    title = {
                        Text(contact.displayName.orEmpty())
                    }
                )
            }
        ) { pV ->
            ZoomableImage(
                modifier = Modifier.padding(pV),
                bitmap = contact.photo!!
            )
        }
    }
}
