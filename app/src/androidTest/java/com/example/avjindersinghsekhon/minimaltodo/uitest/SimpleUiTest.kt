package com.example.avjindersinghsekhon.minimaltodo.uitest

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Test
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import android.content.Intent
import androidx.test.uiautomator.*
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Before
import java.util.regex.Pattern
import com.example.avjindersinghsekhon.minimaltodo.uitest.utils.ScreenDiffer
import org.junit.Rule
import org.junit.rules.TemporaryFolder

class SimpleUiTest {

    private val device = UiDevice.getInstance(getInstrumentation())
    private val context = getApplicationContext<Context>()

    @Rule
    @JvmField
    val temporaryFolder = TemporaryFolder()

    @Before
    fun launchApp() {
        device.pressHome()
        val intent = context.packageManager
                .getLaunchIntentForPackage(APPLICATION_PACKAGE)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(APPLICATION_PACKAGE).depth(0)), 5000)
    }

    @Test
    fun addButtonShouldOpenNewWindow() {
        val addButton = device.findObject(By.res(Pattern.compile(".*$BUTTON_ID")))
        addButton.clickAndWait(Until.newWindow(), LONG_WAIT)
    }

    @Test
    fun shouldSeeTheSameScreenOnReturn() {
        val screenDiffer = ScreenDiffer(temporaryFolder.root)
        val addButton = device.findObject(By.res(Pattern.compile(".*$BUTTON_ID")))
        addButton.clickAndWait(Until.newWindow(), LONG_WAIT)
        screenDiffer.takeExpected(device)
        device.pressBack()
        device.pressBack()
        device.waitForIdle()
        screenDiffer.takeActual(device)
        screenDiffer.compare()
    }

    companion object {
        private const val BUTTON_ID = "id/addToDoItemFAB"
        private const val APPLICATION_PACKAGE = "com.avjindersinghsekhon.minimaltodo"
        private const val LONG_WAIT = 5000L
    }
}