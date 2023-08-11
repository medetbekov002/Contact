package com.example.contacts.util

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.contacts.R
import com.example.contacts.enums.IntentActionType
import com.example.contacts.obj.ContactData
import com.example.contacts.ui.activities.MainActivity

object ShortcutHelper {
    sealed class AppShortcut(
        val action: String,
        @DrawableRes val iconRes: Int,
        @StringRes val label: Int
    ) {
        object CreateContact : AppShortcut("create", R.drawable.ic_add, R.string.create_contact)
    }
    private val shortcuts = listOf(AppShortcut.CreateContact)

    private fun createShortcut(
        context: Context,
        intent: Intent,
        label: String,
        id: String,
        icon: IconCompat,
        pin: Boolean = false
    ) {
        val shortcut = ShortcutInfoCompat.Builder(context, id)
            .setShortLabel(label)
            .setLongLabel(label)
            .setIcon(icon)
            .setIntent(intent)
            .build()

        if (!pin) {
            ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
        } else {
            ShortcutManagerCompat.requestPinShortcut(context, shortcut, null)
        }
    }

    fun createShortcuts(context: Context) {
        ShortcutManagerCompat.getDynamicShortcuts(context).takeIf { it.isEmpty() } ?: return

        shortcuts.forEach {
            createShortcut(
                context,
                Intent(context, MainActivity::class.java).apply {
                    this.action = Intent.ACTION_VIEW
                    putExtra("action", it.action)
                },
                context.getString(it.label),
                it.action,
                IconCompat.createWithResource(context, it.iconRes)
            )
        }
    }

    fun createContactShortcut(
        context: Context,
        contact: ContactData,
        intentActionType: IntentActionType
    ) {
        val intent = IntentHelper.getLaunchIntent(
            intentActionType,
            when (intentActionType) {
                IntentActionType.DIAL, IntentActionType.SMS -> contact.numbers.firstOrNull()?.value
                IntentActionType.EMAIL -> contact.emails.firstOrNull()?.value
                IntentActionType.CONTACT -> contact.contactId.toString()
                else -> contact.addresses.firstOrNull()?.value
            }.orEmpty()
        )

        val icon = when (intentActionType) {
            IntentActionType.DIAL -> R.drawable.ic_call
            IntentActionType.SMS -> R.drawable.ic_message
            IntentActionType.EMAIL -> R.drawable.ic_add
            IntentActionType.CONTACT -> R.drawable.ic_contact
            else -> return
        }

        createShortcut(
            context,
            intent,
            contact.displayName.orEmpty(),
            System.currentTimeMillis().toString(),
            IconCompat.createWithResource(context, icon),
            true
        )
    }
}
