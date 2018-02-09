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

C++ Example - Here we use the existing Drivetrain Subsystem to connect to the server and to store our function that retrieves the switch & scale data.

```
Drivetrain.h

#include <networktables/NetworkTableInstance.h>

/**
 *
 *
 * @author ExampleAuthor
 */
class Drivetrain: public frc::Subsystem {
private:
...
	nt::NetworkTableInstance GSSinst;
public:
...
	std::string getGameSpecificMessage();
...
};
```
```
Drivetrain.cpp

Drivetrain::Drivetrain() : frc::Subsystem("Drivetrain") {
...

	// Open up a NetworkTables connection to the powerup-gss server. This will reconnect on it's own if
	// the powerup-gss server is not available. The AddLogger will remove all error messages for this NT instance,
	// so if you are experiencing difficulties making this work, comment that line out.
	// Note: This should probably be split into it's own subsystem so the code layout and function is cleaner.
	GSSinst = nt::NetworkTableInstance::Create();
	GSSinst.StartClient("10.0.100.5",1735);
	GSSinst.AddLogger({}, 0, 99);
}

std::string Drivetrain::getGameSpecificMessage() {
	// Return the switch & scale data pulled from the NetworkTable entry.
	return GSSinst.GetTable("OffseasonFMSInfo")->GetEntry("GameData").GetString("defaultValue");
}
```

To get the data, simply ask the Drivetrain::getGameSpecificMessage function from your autononmous command or AutonomousInit().
Note: This example just display the data. You will want to act on the data retrieved from getGameSpecificMessage().

```
void AutonomousCommand::Initialize() {
	// Grab the game data and push it to the console.
	std::cout << "GameData: " << Robot::drivetrain->getGameSpecificMessage() << std::endl;
}
```

Python Example - Using this as the base for the Python code: https://github.com/robotpy/examples/blob/master/getting-started/robot.py

```
def robotInit(self):
        """
        This function is called upon program startup and
        should be used for any initialization code.
        """
	# Create a new NetworkTableInstance and connect to the powerup-gss server.
	self.inst = NetworkTables.create();
        self.inst.initialize(server='10.0.100.5')

def getGameSpecificMessage(self):
	# Lookup the current gameData and return it.
	return self.inst.getTable("OffseasonFMSInfo").getString('GameData', 'defaultValue')
	

def autonomousInit(self):
        """This function is run once each time the robot enters autonomous mode."""
        self.timer.reset()
        self.timer.start()
	# Get the current gameData.
	self.gameData = self.getGameSpecificMessage()
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
