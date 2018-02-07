package com.bryanherbst.gss

import edu.wpi.first.networktables.NetworkTableInstance
import java.net.Inet4Address
import java.net.NetworkInterface

/**
 * Wraps interaction with NetworkTables.
 *
 * Note that this class will immediately start a NetworkTables server upon instantiation.
 */
class NetworkTablesManager(val tableName: String) {

    private val networkTables = NetworkTableInstance.create()
    private val ipAddress = getIp()

    init {
        networkTables.setNetworkIdentity("powerup-gss")
        networkTables.startServer(ipAddress)

        println("NetworkTables server started at $ipAddress")
    }

    fun setEntry(key: String, value: Any) {
        networkTables.getTable(tableName).getEntry(key).setValue(value)
    }

    private fun getIp(): String {
        val interfaces = NetworkInterface.getNetworkInterfaces().toList()

        val fmsExpected = interfaces.filter {
            it.inetAddresses.toList().any { it.hostAddress == "10.0.100.5" }
        }
        if (fmsExpected.isNotEmpty()) {
            println("Found network interface with expected FMS address")
            return fmsExpected[0].inetAddresses.toList()[0].hostAddress
        }

        val nonLoopback = interfaces.filter {
            it.inetAddresses.toList()
                    .filter { it is Inet4Address }
                    .any { !it.isLoopbackAddress }
        }
        if (nonLoopback.isNotEmpty()) {
            println("Using first non-loopback address")
            return nonLoopback[0].inetAddresses.toList()[0].hostAddress
        }

        println("using first available address")
        return interfaces[0].inetAddresses.toList()[0].hostAddress
    }
}