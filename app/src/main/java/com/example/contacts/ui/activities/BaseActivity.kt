package com.example.contacts.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.contacts.ui.models.ContactsModel
import com.example.contacts.ui.models.ThemeModel

abstract class BaseActivity: ComponentActivity() {
    lateinit var themeModel: ThemeModel
    lateinit var contactsModel: ContactsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelProvider = ViewModelProvider(this)
        themeModel = viewModelProvider.get()
        contactsModel = viewModelProvider.get()

        contactsModel.init(this)
    }
}
