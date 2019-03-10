package com.example.avjindersinghsekhon.minimaltodo.uitest.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.test.uiautomator.UiDevice
import junit.framework.Assert.fail
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ScreenDiffer(private val folder: File) {

    private lateinit var expectedFile: File
    private lateinit var actualFile: File

    fun takeExpected(device: UiDevice) {
        expectedFile = File(folder, "expectedFile.png")
        if (!device.takeScreenshot(expectedFile)) {
            fail("Could not take expected screenshot")
        }
    }

    fun takeActual(device: UiDevice) {
        actualFile = File(folder, "actualFile.png")
        if (!device.takeScreenshot(actualFile)) {
            fail("Could not take actual screenshot")
        }
    }

    private fun getDiff(): Bitmap? {
        val expected = BitmapFactory.decodeFile(expectedFile.path)
        val actual = BitmapFactory.decodeFile(actualFile.path)
        var result: Bitmap? = null
        for (y in 0 until expected.height) {
            for (x in 0 until expected.width) {
                if (expected.getPixel(x, y) != actual.getPixel(x, y)) {
                    if (result == null) {
                        result = expected.copy(expected.config, true)
                    }
                    result!!.setPixel(x, y, Color.RED)
                }
            }
        }
        return result
    }

    fun compare() {
        val diff = getDiff() ?: return
        try {
            BufferedOutputStream(FileOutputStream(File(folder, "result.png"))).use {
                diff.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        } catch (e: IOException) {
            fail("Could not write diff to file")
        }
        fail("Screenshots are different")
    }
}
