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

You can then use the VS Code Gradle support to run the `runHello` task, which will leave the `01-hello.eol` script waiting for a Debug Adapter connection on port 4040.

Try setting a breakpoint, and then debug the program by going to the `Run and Debug` section of VS Code, and launching the "Attach to Epsilon script" configuration.

The program will then start, and should stop at the designated breakpoint.
