/*
 * Copyright (C) 2024 Kevin Buzeau
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.buzbuz.smartautoclicker.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.buzbuz.smartautoclicker.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object AppLanguageSelector {

    private val languageTags =
        listOf("", "en", "ar", "es", "fr", "it", "ja", "pt-BR", "ru", "uk", "vi", "zh-CN", "zh-TW")

    fun currentLanguageName(context: Context): String =
        languageNames(context)[selectedLanguageIndex()]

    fun show(context: Context, onLanguageSelected: (String) -> Unit = {}) {
        val languageNames = languageNames(context)
        val selectedIndex = selectedLanguageIndex()

        MaterialAlertDialogBuilder(context)
            .setTitle(R.string.field_app_language)
            .setSingleChoiceItems(languageNames, selectedIndex) { dialog, which ->
                onLanguageSelected(languageNames[which])
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(languageTags[which])
                )
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun languageNames(context: Context) = arrayOf(
        context.getString(R.string.language_system_default),
        "English",
        "العربية",
        "Español",
        "Français",
        "Italiano",
        "日本語",
        "Português (Brasil)",
        "Русский",
        "Українська",
        "Tiếng Việt",
        "简体中文",
        "繁體中文",
    )

    private fun selectedLanguageIndex(): Int {
        val currentLanguage = AppCompatDelegate.getApplicationLocales()
            .toLanguageTags()
            .substringBefore(',')
        return languageTags.indexOfFirst { it.equals(currentLanguage, ignoreCase = true) }
            .takeIf { it >= 0 } ?: 0
    }
}
