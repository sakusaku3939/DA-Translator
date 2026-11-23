package com.example.deeplviewer

import com.example.deeplviewer.webview.MyWebViewClient
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationDecisionTest {
    @Test
    fun translatorAndWritePagesAreAllowed() {
        listOf(
            "https://www.deepl.com/translator/l/en/da",
            "https://www.deepl.com/ja/translator/l/en/ja",
            "https://www.deepl.com/pt-PT/translator/l/en/pt",
            "https://www.deepl.com/en/write"
        ).forEach { url ->
            assertTrue(url, MyWebViewClient.isAllowedNavigation(url, isRedirect = false))
        }
    }

    @Test
    fun deepLRedirectsAreFollowedEvenWhenTheLayoutChanges() {
        listOf(
            "https://www.deepl.com/en/in/translator/l/en/in",
            "https://www.deepl.com/en/translator",
            "https://www.deepl.com/"
        ).forEach { url ->
            assertTrue(url, MyWebViewClient.isAllowedNavigation(url, isRedirect = true))
        }
    }

    @Test
    fun linksAndRedirectsOutsideTheTranslatorAreBlocked() {
        assertFalse(MyWebViewClient.isAllowedNavigation(
            "https://www.deepl.com/en/in/translator/l/en/in", isRedirect = false))
        assertFalse(MyWebViewClient.isAllowedNavigation("https://www.deepl.com/en/pro", isRedirect = false))
        listOf(
            "https://www.deepl.com.example.com/en/translator",
            "https://example.com/?https://www.deepl.com/",
            "http://www.deepl.com/en/translator",
            "https://deepl.com/en/translator"
        ).forEach { url ->
            assertFalse(url, MyWebViewClient.isAllowedNavigation(url, isRedirect = true))
        }
    }
}
