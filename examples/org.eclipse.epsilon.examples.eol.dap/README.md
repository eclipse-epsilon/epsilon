# Example for Debug Adapter support

This example project shows how to debug an Epsilon script from two different IDEs through the [Debug Adapter Protocol (DAP)](https://microsoft.github.io/debug-adapter-protocol/):

* From Eclipse, using the [LSP4E](https://github.com/eclipse/lsp4e) tools: details are provided below.
* From VS Code, using its built-in support for DAP: details are provided in the [README of the Gradle project](epsilon/README.md).

## Starting an Epsilon script from a local file, with remote debugging

Currently, the Debug Adapter support is attach-based.
We expect users to set up the Epsilon script with the appropriate models and configuration, and then hand it over to the Epsilon DAP server for remote debugging.
There are two approaches for doing this: via Java code, and through the Ant workflow tasks.

### Via Java code

The [DebugFileBasedEOL](src/org/eclipse/epsilon/examples/eol/dap/DebugFileBasedEOL.java) class shows how to do this.
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

## Starting an Epsilon script from a classpath resource, with remote debugging

The [DebugClasspathBasedEOL](src/org/eclipse/epsilon/examples/eol/dap/DebugClasspathBasedEOL.java) class shows how to do this.

In order to allow breakpoints on local files to apply to resources loaded from the classpath without requiring configuration, the debug adapter uses a longest-matching-component-suffix matching strategy to decide which module corresponds to a given path.

For instance, suppose we had a breakpoint on:

```
file:/path/to/sources/main/a.eol
```

If our running program was made up of:

```
jar:/path/to/binaries/main/a.eol
jar:/path/to/binaries/common/a.eol
```

Then the first option would be chosen, as it has the longest suffix of matching trailing components (two: `a.eol`, and then `main`).

The matching strategy will refuse to verify a breakpoint if there are two or more candidates with the same number of matching trailing components, to avoid ambiguity.

This example can be launched from the `Run Debug Adapter on 03-helloFromClasspath from Java` launch configuration.

## Connecting to the Epsilon Debug Adapter Protocol server 

Once the DAP server is running and waiting for connections, you can use a DAP client (e.g. LSP4E) to debug the Epsilon script that it is controlling.

Assuming you are running the above examples, which parse a file directly, follow these steps to try out the DAP support from Eclipse:

1. Install LSP4E.
1. Set some breakpoints in the EOL scripts.
1. Right click on `Debug Epsilon on port 4040.launch` in the Project Explorer, and select `Debug As > Debug Epsilon on port 4040 without URI mappings`.

You should be able to view variable values and control execution as usual.