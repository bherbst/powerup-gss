package com.bryanherbst.gss

import com.bryanherbst.gss.extension.random
import javafx.fxml.FXML
import javafx.scene.layout.VBox

class MainController {

    companion object {
        private const val STYLE_RED = "red-alliance-box"
        private const val STYLE_BLUE = "blue-alliance-box"
    }

    @FXML lateinit var redSwitchNear: VBox
    @FXML lateinit var redSwitchFar: VBox
    @FXML lateinit var blueSwitchNear: VBox
    @FXML lateinit var blueSwitchFar: VBox
    @FXML lateinit var scaleNear: VBox
    @FXML lateinit var scaleFar: VBox

    private val gameDataReporter = GameDataReporter()

    fun randomize() {
        val configuration = PlateConfiguration.values().random()
        when (configuration) {
            PlateConfiguration.LLL -> {
                setSwitchStyles(STYLE_RED, STYLE_BLUE)
                scaleNear.styleClass.setAll(STYLE_RED)
                scaleFar.styleClass.setAll(STYLE_BLUE)
            }

            PlateConfiguration.RRR -> {
                setSwitchStyles(STYLE_BLUE, STYLE_RED)
                scaleNear.styleClass.setAll(STYLE_BLUE)
                scaleFar.styleClass.setAll(STYLE_RED)
            }

            PlateConfiguration.LRL -> {
                setSwitchStyles(STYLE_RED, STYLE_BLUE)
                scaleNear.styleClass.setAll(STYLE_BLUE)
                scaleFar.styleClass.setAll(STYLE_RED)
            }

            PlateConfiguration.RLR -> {
                setSwitchStyles(STYLE_BLUE, STYLE_RED)
                scaleNear.styleClass.setAll(STYLE_RED)
                scaleFar.styleClass.setAll(STYLE_BLUE)
            }
        }

        gameDataReporter.reportPlateConfiguration(configuration)
    }

    private fun setSwitchStyles(nearSwitches: String, farSwitches: String) {
        redSwitchNear.styleClass.setAll(nearSwitches)
        blueSwitchNear.styleClass.setAll(nearSwitches)
        redSwitchFar.styleClass.setAll(farSwitches)
        blueSwitchFar.styleClass.setAll(farSwitches)
        scaleNear.styleClass.setAll(farSwitches)
        scaleFar.styleClass.setAll(farSwitches)
    }
}