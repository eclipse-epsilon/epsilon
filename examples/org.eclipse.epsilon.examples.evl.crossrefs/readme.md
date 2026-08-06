# Working with Cross-Referenced Models in EVL

This is an advanced EVL example which demonstrates how the behaviour of EVL can be customised to only check some elements of a multi-file EMF model. In particular, we have a model that consists of three cross-referenced files (`a.flexmi`, `b.flexmi` and `c.flexmi`) and we wish to execute a set of EVL constraints only on elements belonging to `a.flexmi`.

## How to run this example

- Download and install [Java 11](https://adoptium.net/)
- Download and install [Maven](https://maven.apache.org/)
- Run `mvn compile exec:java` from the command line
