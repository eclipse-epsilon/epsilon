# Using the Simulink driver from Java

This example demonstrates using Epsilon's Simulink EMC driver from a standalone Java application through the command line. In particular, we use an [EOL program](program.eol) to create a new Simulink model like the one below.

![](https://eclipse.dev/epsilon/doc/articles/simulink/simulink-model.png)

## How to run this example

- Download and install [Java 17](https://adoptium.net/)
- Download and install [Maven](https://maven.apache.org/)
- Check out the [official MATLAB documentation](https://uk.mathworks.com/help/matlab/matlab_external/setup-environment.html) for OS-specific instructions on setting up your environment
- Edit `.mvn/jvm.config` to specify MATLAB's library path (or delete `.mvn/jvm.config` altogether if you are on Windows and you wish the driver to use the default MATLAB installation)
- Run `mvn compile exec:java` from the command line
- Open the generated Simulink model (`model.slx`) to inspect/simulate it
