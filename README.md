# PowerUp Game Specific Data Server

This is a Java application that provides game-specific data to robots for the 2018 *FIRST* Robotics Competition game, PowerUp.

It is intended for pre-season events (typically "Week Zero" events), since *FIRST* will not have a mechanism in place
for those events to provide the game-specific data through the official APIs.

![Screenshot](web-resources/screenshot.png ?raw=true)

## Clients

This app publishes the scale and plate randomization to NetworkTables ([see the WPILib intro here](http://wpilib.screenstepslive.com/s/currentCS/m/75361/l/843361-what-is-networktables)).
The data is published to the `"OffseasonFMSInfo"` table in the `"GameData"` entry.
This entry follows the same format (e.g. `"LRL"`) as the official WPILib API `DriverStation.getInstance().getGameSpecificMessage();`.

An example Java implementation to get this data would look like this:

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

Note that you need to know the IP address of the computer running the server (this application).
If that computer is running the FMS software, it is likely **10.0.100.5**.

For a one-page document containing instructions that you can hand out to teams at events you are hosting, 
check out [Event Information](Event Information).

## Running the server

To run from source use the command `./gradlew run`.

You can also download a zip containing an executable version from the GitHub releases page.

The server will attempt to use your computer's IP address for the NetworkTables server on the default port.
If possible, it will prefer the default FMS IP address of **10.0.100.5**.

Once running, the UI offers a single button: randomize. This button will randomize the scale and plate assignments amongst
the legal configurations.
