# Maven example

This is an example of how to run from Maven an EOL program that queries an RDF file.
The Maven build uses the [maven-antrun-plugin](https://maven.apache.org/plugins/maven-antrun-plugin/) to reuse the [Epsilon Ant tasks](https://eclipse.dev/epsilon/doc/workflow/).

## Requirements

This project requires Java 17+ and Maven 3 to be installed and available from the `PATH`.

## Execution

To try it out, run the following command:

```bash
mvn compile
```

The Ant build inside the Maven file first registers the Epsilon Ant tasks.
It uses a Groovy script to associate the RDF resource factory with the `.rdfres` file extension.

After this, we can use the Epsilon Ant tasks as normal.
In this specific case, the `program.eol` prints out the names of various employees in the RDF graph:

```
Employees in the Development Team team: Sequence {"Jane Sue", "John Doe"}
Employees in the Marketing Team team: Sequence {}
Names of all employees: Sequence {"Jane Sue", "John Doe"}
```