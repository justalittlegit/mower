# Mower
Solution's source code for the mower exercise.
## Prerequisites
Java 8 >, Git, Maven, Eclipse IDE or other.
## Getting started
Using powershell, clone repository from Github:
```
git clone https://github.com/justalittlegit/mower.git
```
Import project into IDE as a Maven project.
## Testing
The mowers file processing method is implemented in a static method, which can be accessed as the following:
```
List<Mower> mowers = MowerService.process(filePath);
```
Which returns a list of the mowers with their last position.

The project is provided with JUnit4 tests, and a folder /test-files containing different cases.
The success case in the exemple (success-case.txt) can be demonstrated in the main java method.
