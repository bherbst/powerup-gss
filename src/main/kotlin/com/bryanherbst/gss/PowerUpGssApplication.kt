
package com.bryanherbst.gss

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class PowerUpGssApplication : Application() {

    override fun start(primaryStage: Stage) {
        val root: Parent = FXMLLoader.load(javaClass.getResource("main.fxml"))
        primaryStage.title = "PowerUp Game Specific Data Server"
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(PowerUpGssApplication::class.java, *args)
        }
    }
}
