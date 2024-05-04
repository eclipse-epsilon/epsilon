# Example for Debug Adapter support

This example project shows how to debug an Epsilon script from two different IDEs through the [Debug Adapter Protocol (DAP)](https://microsoft.github.io/debug-adapter-protocol/):

* From Eclipse, using the [LSP4E](https://github.com/eclipse/lsp4e) tools: details are provided below.
* From VS Code, using its built-in support for DAP: details are provided in the [README of the Gradle project](epsilon/README.md).

## Starting an Epsilon script with remote debugging

Currently, the Debug Adapter support is attach-based.
We expect users to set up the Epsilon script with the appropriate models and configuration, and then hand it over to the Epsilon DAP server for remote debugging.
There are two approaches for doing this: via Java code, and through the Ant workflow tasks.

### Via Java code

The [DebugEOL](src/org/eclipse/epsilon/examples/eol/dap/DebugEOL.java) class shows how to do this.
This requires creating a `EpsilonDebugServer` instance with the configured module and the port to listen on, and invoking the `.run()` method which will block until the script has completed its execution.

The example provided includes two launch configurations for this:

* `Run Debug Adapter on 01-hello from Java.launch`
* `Run Debug Adapter on 02-imports-main from Java.launch`

These can be executed by right-clicking on them in the Project Explorer, and selecting their entries within the "Run as..." menu.

### Via Ant tasks

The [provided Ant file](build.xml) shows how to do this.
When an executable Ant task is configured with `debug="true"` and `debugPort="PORT"`, it will wait for a DAP client before it starts running.

There are two launch configurations for this as well:

* `Run Debug Adapter on 01-hello from Ant.launch`
* `Run Debug Adapter on 02-imports-main from Ant.launch`

## Connecting to the Epsilon Debug Adapter Protocol server 

Once the DAP server is running and waiting for connections, you can use a DAP client (e.g. LSP4E) to debug the Epsilon script that it is controlling.

To try out the DAP support from Eclipse, install LSP4E, set some breakpoints in the EOL scripts, and then run the `Debug Epsilon on port 4040.launch` launch configuration in this project.
You should be able to view variable values and control execution as usual.