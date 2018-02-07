package com.bryanherbst.gss

import edu.wpi.first.networktables.NetworkTableInstance

/**
 * Reports game data to robots
 */
class GameDataReporter {
    companion object {
        private const val TABLE_NAME = "OffseasonFMSInfo"
        private const val KEY_GAME_DATA = "GameData"
    }

    private val networkTablesManager = NetworkTablesManager(TABLE_NAME)

    fun reportPlateConfiguration(configuration: PlateConfiguration) {
        networkTablesManager.setEntry(KEY_GAME_DATA, configuration.toString())
    }

}