package com.example.avjindersinghsekhon.minimaltodo.uitest

import java.util.regex.Pattern

import android.content.Context
import android.content.Intent

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.uiautomator.*

import com.example.avjindersinghsekhon.minimaltodo.uitest.utils.ScreenDiffer

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class SimpleUiTest {

    private val device = UiDevice.getInstance(getInstrumentation())
    private val context = getApplicationContext<Context>()

    @Rule
    @JvmField
    val temporaryFolder = TemporaryFolder()

    @Before
    fun launchApp() {
        /* Relaunch the application */
        device.pressHome()
        val intent = context.packageManager
                .getLaunchIntentForPackage(APPLICATION_PACKAGE)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(APPLICATION_PACKAGE).depth(0)), LONG_WAIT)

        /* Remove all toDos */
        findObjectsByRes(TODO_ITEM).forEach {
            it.swipe(Direction.RIGHT, 1F)
            device.wait(Until.gone(byResSelector(DELETED_TODO_BAR)), LONG_WAIT)
        }
    }

    @Test
    fun shouldOpenNewWindow() {
        findObjectByRes(ADD_TODO_BUTTON).clickAndWait(Until.newWindow(), LONG_WAIT)
    }

    @Test
    fun shouldSeeTheSameScreenOnReturn() {
        val screenDiffer = ScreenDiffer(temporaryFolder.root)
        screenDiffer.takeExpected(device)
        findObjectByRes(ADD_TODO_BUTTON).clickAndWait(Until.newWindow(), LONG_WAIT)
        device.pressBack()
        device.pressBack()
        device.waitForIdle()
        screenDiffer.takeActual(device)
        screenDiffer.compare()
    }

    @Test
    fun shouldAddNewTodo() {
        findObjectByRes(ADD_TODO_BUTTON).clickAndWait(Until.newWindow(), LONG_WAIT)
        findObjectByRes(EDIT_TODO_TITLE).text = SAMPLE_TODO_TITLE
        findObjectByRes(MAKE_TODO_BUTTON).clickAndWait(Until.newWindow(), LONG_WAIT)
        assertTrue(findObjectByRes(TODO_ITEM).hasObject(By.text(SAMPLE_TODO_TITLE)))
    }

    private fun findObjectByRes(resId: String): UiObject2 {
        return device.findObject(byResSelector(resId))
    }

    private fun findObjectsByRes(resId: String): List<UiObject2> {
        return device.findObjects(byResSelector(resId))
    }

    private fun byResSelector(resId: String): BySelector {
        return By.res(Pattern.compile(".*$resId"))
    }

    companion object {
        private const val SAMPLE_TODO_TITLE = "Todotodotodo"
        private const val MAKE_TODO_BUTTON = "id/makeToDoFloatingActionButton"
        private const val EDIT_TODO_TITLE = "id/userToDoEditText"
        private const val DELETED_TODO_BAR = "id/snackbar_text"
        private const val TODO_ITEM = "id/listItemLinearLayout"
        private const val ADD_TODO_BUTTON = "id/addToDoItemFAB"
        private const val APPLICATION_PACKAGE = "com.avjindersinghsekhon.minimaltodo"
        private const val LONG_WAIT = 5000L
    }
}