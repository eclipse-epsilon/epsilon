# Example for Debug Adapter support

This example project shows how to debug an Epsilon script from two different IDEs through the [Debug Adapter Protocol (DAP)](https://microsoft.github.io/debug-adapter-protocol/):

* From Eclipse, using the [LSP4E](https://github.com/eclipse/lsp4e) tools: details are provided below.
* From VS Code, using its built-in support for DAP: open the `epsilon` folder from VS Code, and read the details in its [README](epsilon/README.md).

Currently, the Debug Adapter support is attach-based.
We expect users to follow these steps:

1. Set up the Epsilon script with the appropriate models and configuration, and then hand it over to the Epsilon DAP server for remote debugging. The server will wait for connections before starting the Epsilon script.
1. Connect to the DAP server using your IDE. The process for LSP4E is provided at the end of this document.

## Epsilon DAP server for a local file

### Via Java code

The [DebugFileBasedEOL](src/org/eclipse/epsilon/examples/eol/dap/DebugFileBasedEOL.java) class shows how to do this.
This requires creating a `EpsilonDebugServer` instance with the configured module and the port to listen on, and invoking the `.run()` method which will block until the script has completed its execution.

The [DebugWithModel](src/org/eclipse/epsilon/examples/eol/dap/DebugWithModel.java) class shows how we can debug scripts that use models.
Again, this only requires passing the set up module to the server, and letting the server run.

The project includes several launch configurations for this:

* `Run Debug Adapter on 01-hello from Java.launch`
* `Run Debug Adapter on 02-imports-main from Java.launch`
* `Run Debug Adapter on 04-inspectObject from Java.launch`

These can be executed by right-clicking on them in the Project Explorer, and selecting their entries within the "Run as..." menu.

### Via Ant tasks

The [provided Ant file](build.xml) shows how to do this.
When an executable Ant task is configured with `debug="true"` and `debugPort="PORT"`, it will wait for a DAP client before it starts running.

There are several launch configurations for this as well:

* `Run Debug Adapter on 01-hello from Ant.launch`
* `Run Debug Adapter on 02-imports-main from Ant.launch`
* `Run Debug Adapter on 04-inspectObject from Ant.launch`

## Epsilon DAP server for a classpath resource

The [DebugClasspathBasedEOL](src/org/eclipse/epsilon/examples/eol/dap/DebugClasspathBasedEOL.java) class shows how to do this.

The class includes comments on how the experience differs depending on whether the classpath resource resides directly in a local folder as a regular file (e.g. within the `bin` folder of an Eclipse project), or not (e.g. when it is inside a JAR).
In the second case, we will need to tell the debug adapter how to map module URIs to filesystem paths, so it can:

* Associate incoming requests for breakpoints to modules (DAP client path -> module URI),
* Relate a breakpoint hit in a module to the file to show in the DAP client (module URI -> DAP client path).

This can be done by adding entries to the `adapter.getUriToPathMappings()` map, where the key is the URI, and the value is the filesystem path that it refers to.
The adapter can map both individual files, or entire subtrees.

This example can be launched from the `Run Debug Adapter on 03-helloFromClasspath from Java` launch configuration.

## Epsilon DAP server for an HTTP file

The [DebugHttpBasedEOL](src/org/eclipse/epsilon/examples/eol/dap/DebugHttpBasedEOL.java) class shows how to do this.

This is the most general case, where the module is being loaded from a non-file URI.
In this case, the adapter needs to be explicitly told how to map the `http://` URIs used by the modules to filesystem paths that it can report to the DAP client.

This example can be launched from the `Run Debug Adapter on 01-hello via HTTP from Java` launch configuration.

## Connecting to the Epsilon DAP server

Once the DAP server is running and waiting for connections, you can use a DAP client (e.g. LSP4E) to debug the Epsilon script that it is controlling.

Assuming you are running the above examples, which parse a file directly, follow these steps to try out the DAP support from Eclipse:

1. Install LSP4E.
1. Set some breakpoints in the EOL scripts.
1. Right click on `Debug Epsilon on port 4040.launch` in the Project Explorer, and select `Debug As > Debug Epsilon on port 4040 without URI mappings`.

You should be able to view variable values and control execution as usual.
