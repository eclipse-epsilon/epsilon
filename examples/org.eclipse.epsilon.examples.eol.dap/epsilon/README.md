# Gradle example for Epsilon Debug Adapter support in VS Code

This is a minimal Gradle project to try out the Debug Adapter support in Epsilon from VS Code.

To experiment with this functionality from VS Code, first run a plain Maven build from the root of the repository, installing the new versions of the workflow tasks which include the Epsilon DAP server:

```sh
# Go to the root directory of the Epsilon sources
cd ../../..
# Build and install the updated workflow tasks
mvn -f pom-plain.xml install
```

Next, run VS Code with the [`vscode-epsilon` extension](https://marketplace.visualstudio.com/items?itemName=SamHarris.eclipse-epsilon-languages) installed, which supports remote debugging since its v2.2.0 release.

Try setting a breakpoint, and then debug the program by going to the `Run and Debug` section of VS Code, and launching the "Debug 01-hello" or "Debug 04-inspectObject" configurations.

VS Code will start the script on debug mode in the background, connect to the DAP server of the script, and stop at the designated breakpoint.
