# PowerUp Game Specific Data Server

This is a Java application that provides game-specific data to robots for the 2018 *FIRST* Robotics Competition game, PowerUp.

It is intended for pre-season events (typically "Week Zero" events), since *FIRST* will not have a mechanism in place
for those events to provide the game-specific data through the official APIs.

![Screenshot](web-resources/screenshot.png?raw=true)

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

Example C++ - Here we use the existing Drivetrain Subsystem to connect to the server and to store our function that retrieves the switch & scale data.

```C++
#include <networktables/NetworkTableInstance.h>

Drivetrain::Drivetrain() : frc::Subsystem("Drivetrain") {
...

	// Open up a NetworkTables connection to the powerup-gss server and grab the game data.
	// Note: This should probably be split into multiple parts or perhaps into it's own subsystem so we
	// re-use the same connection to NT vs building, grab and destroy every time.
	GSSinst = nt::NetworkTableInstance::Create();
	GSSinst.StartClient("10.0.100.5",1735);
	GSSinst.AddLogger({}, 0, 99);
}

std::string Drivetrain::getGameSpecificMessage() {
	// Return the switch & scale data pulled from the NetworkTable entry.
	return GSSinst.GetTable("OffseasonFMSInfo")->GetEntry("GameData").GetString("defaultValue");
}
```

To get the data, simply ask the Drivetrain::getGameSpecificMessage function from your autononmous command or AutonomousInit(). Note: This example just display the data. You will want to act on the data retrieved from getGameSpecificMessage().

```
void AutonomousCommand::Initialize() {
	// Grab the game data and push it to the console.
	std::cout << "GameData: " << Robot::drivetrain->getGameSpecificMessage() << std::endl;
}
```


Note that you need to know the IP address of the computer running the server (this application).
If that computer is running the FMS software, it is likely **10.0.100.5**.

For a one-page document containing instructions that you can hand out to teams at events you are hosting, 
check out [Event Information](Event%20Information.md).

## Running the server

To run from source use the command `./gradlew run`.

You can also download a zip containing an executable version from the GitHub releases page.

The server will attempt to use your computer's IP address for the NetworkTables server on the default port.
If possible, it will prefer the default FMS IP address of **10.0.100.5**.

Once running, the UI offers a single button: randomize. This button will randomize the scale and plate assignments amongst
the legal configurations.
