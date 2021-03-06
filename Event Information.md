# Week Zero Game Data Instructions
Welcome to Week Zero! This page contains critical information for your team’s programmers regarding the 2018 game data 
that they can receive from the field. **Please ensure that your team’s programmers read this in its entirety.**

Because we cannot use the official Field Management System (FMS) software for the 2018 season, we do not have access to 
the official APIs. We have, however, created a system that will allow you to retrieve the SCALE and SWITCH randomization
information in a similar fashion.

**Critical:** This mechanism will **only work at this event.** At the end of this event, you should go back to using the
official APIs from WPILib as described in the control system documentation at [wpilib.screenstepslive.com](wpilib.screenstepslive.com).

### Technical Details
We are running a NetworkTables server on our FMS computer (this computer starts the matches and controls your driver station
while you are connected to the field). We will be publishing the game-specific message to that NetworkTables instance.

 * The server is running at the IP address `10.0.100.5`.
 * The game-specific message will be posted to the `"OffseasonFMSInfo"` table in the `"GameData"` entry as a String.
 * The format of the message is exactly the same as the official API; for example you will receive "LRL" to indicate that
the left side of the switches and the right side of the scale belong to your alliance as you look out to the field from
your driver station.

### Code samples
#### Java
```Java
NetworkTableInstance offSeasonNetworkTable = NetworkTableInstance.create()
offSeasonNetworkTable.startClient("10.0.100.5")
String gameData = offSeasonNetworkTable
    .getTable("OffseasonFMSInfo")
    .getEntry("GameData")
    .getString("defaultValue")
    
    // gameData will be the game specific string you would usually get from the driver station,
    // e.g. "LRL" or "LLL"
```


**Remember:** Switch back to the official APIs after today's competition! **This code will not work at official events.**