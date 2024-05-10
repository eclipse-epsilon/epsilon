# Gradle example for Epsilon Debug Adapter support in VS Code

This is a minimal Gradle project to try out the Debug Adapter support in Epsilon from VS Code.

To experiment with this functionality from VS Code, first run a plain Maven build from the root of the repository, installing the new versions of the workflow tasks which include the Epsilon DAP server:

```sh
# Go to the root directory of the Epsilon sources
cd ../../..
# Build and install the updated workflow tasks
mvn -f pom-plain.xml install
```

Next, run VS Code with a version of the `vscode-epsilon` extension which supports remote debugging.
The current WIP branch for remote debugging is available from:

https://github.com/agarciadom/vscode-epsilon/tree/remote-debug

Try setting a breakpoint, and then debug the program by going to the `Run and Debug` section of VS Code, and launching the "Debug 01-hello" configuration.

VS Code will start the script on debug mode in the background, connect to the DAP server of the script, and stop at the designated breakpoint.
