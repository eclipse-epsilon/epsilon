# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Eclipse Epsilon is a family of scripting languages and tools for model-based software engineering. It provides multiple DSLs for transforming, validating, comparing, generating, and migrating models (primarily EMF-based).

- **Repository**: https://github.com/eclipse/epsilon
- **Version**: 2.9.0-SNAPSHOT (Java 17+)

## Architecture

### Language Engines (plugins/)

Each language is its own OSGi bundle in `plugins/`. EOL is the foundation; all other languages extend it:

| Language | Bundle prefix | Purpose |
|----------|--------------|---------|
| EOL | `eol.engine` | Core expression/statement language; base for all others |
| ETL | `etl.engine` | Model-to-model transformation |
| EGL | `egl.engine` | Model-to-text / code generation |
| EVL | `evl.engine` | Model validation |
| ECL | `ecl.engine` | Model comparison |
| EML | `eml.engine` | Model merging |
| EPL | `epl.engine` | Pattern matching |
| EWL | `ewl.engine` | Interactive model wizards |
| ERL | `erl.engine` | Model refactoring |
| Flock | `flock.engine` | Model migration/evolution |
| EUnit | `eunit.*` | Unit testing for Epsilon programs |
| Flexmi | `flexmi.*` | Flexible textual model instantiation |
| Picto | `picto.*` | Model visualization |
| HUTN | `hutn.*` | Human-Usable Textual Notation |

All bundle names follow the pattern `org.eclipse.epsilon.[language|component].[subcomponent]`.

### Model Connectors (EMC)

Bundles prefixed `org.eclipse.epsilon.emc.*` adapt different model types:
- `emc.emf` – EMF/XMI models (most common)
- `emc.plainxml`, `emc.json`, `emc.yaml`, `emc.csv`, `emc.spreadsheets.excel` – data formats
- `emc.uml`, `emc.simulink`, `emc.jdt`, `emc.graphml`, `emc.bibtex`, `emc.html`, `emc.rdf.emf`

### Build System

Two separate Maven configurations:

- **`pom.xml`** – Tycho build; produces OSGi bundles and p2 update sites (used for Eclipse integration)
- **`pom-plain.xml`** – Plain Maven build; produces standalone JARs usable outside Eclipse

Target platform (Eclipse, EMF, Xtext, Sirius, Apache libraries, etc.) is defined in:
`releng/org.eclipse.epsilon.target/org.eclipse.epsilon.target.tpd`

### Plugin Layout

Each plugin contains:
- `src/org/eclipse/epsilon/[lang]/` – Java sources
- `META-INF/MANIFEST.MF` – OSGi bundle metadata
- `pom.xml` – Tycho packaging (`eclipse-plugin` or `eclipse-test-plugin`)
- Grammar files in `src/` (parsed by ANTLR3) for language plugins

Key packages across all language engines:
- `org.eclipse.epsilon.common.*` – Shared utilities (parsing, AST, context)
- `org.eclipse.epsilon.eol.dom.*` – AST node classes for expressions/statements
- `org.eclipse.epsilon.eol.execute.*` – Execution engine
- `org.eclipse.epsilon.[lang].parse.*` – ANTLR-generated parser/lexer
- `org.eclipse.epsilon.[lang].dt.*` – Eclipse IDE support (editors, outline)

## Building

### Prerequisites

- Java 17+, Maven 3.9.5+
- On Linux, GTK libraries for SWT: `libwebkit2gtk-4.0-dev` (or equivalent)

### Full Build

```bash
# One-time: download EMF Compare (needed by some tests)
bash plugins/org.eclipse.epsilon.eunit.cmp.emf/download-emf-compare.sh

# Install plain Maven parent and Jena uber-JAR
mvn -f pom-plain.xml -N install
mvn -f releng/org.eclipse.epsilon.jena.uberjar install

# Full Tycho build
mvn -T 1C clean install

# Or use the convenience script (does all of the above)
bash build-and-test.sh
```

### Useful Build Variants

```bash
# Build without tests
mvn install -DskipTests

# Build a single plugin
mvn -f plugins/org.eclipse.epsilon.eol.engine install

# Plain Maven artifacts only (much faster, no OSGi overhead)
mvn -T 1C -f pom-plain.xml clean compile

# Build the p2 update site
mvn -f releng verify -P updatesite
# Output: releng/org.eclipse.epsilon.updatesite/target/repository
```

## Testing

Tests live under `tests/`. The main aggregator is `tests/org.eclipse.epsilon.test`.

```bash
# Plugged-in tests (require OSGi/Eclipse runtime; run with Xvfb on CI)
mvn -f tests/org.eclipse.epsilon.test verify -P plugged

# Unit tests (standard JUnit, no OSGi)
mvn -f tests/org.eclipse.epsilon.test surefire:test -P unit

# Single test module
mvn -f tests/org.eclipse.epsilon.eol.engine.test.unit test

# Single test class or method
mvn -f tests/org.eclipse.epsilon.eol.engine.test.unit test -Dtest=MyTestClass
mvn -f tests/org.eclipse.epsilon.eol.engine.test.unit test -Dtest=MyTestClass#testMethod
```

## Using Language Engines at Runtime

All language modules follow the same parse/execute pattern:

```java
// EOL
EolModule module = new EolModule();
module.parse(new File("script.eol"));
module.getContext().getModelRepository().addModel(myModel);
module.execute();

// ETL (same pattern, different module class)
EtlModule module = new EtlModule();

// EGL (returns generated text)
EglModule module = new EglModule();
String output = (String) module.execute();
```

Models are loaded via EMC drivers and registered with the `ModelRepository`:

```java
EmfModel model = new EmfModel();
model.setName("Tree");
model.setMetamodelUri("TreeMM");          // or setMetamodelFile(...)
model.setModelFile("/path/to/model.xmi");
model.load();
module.getContext().getModelRepository().addModel(model);
```

## CI/CD

- **GitHub Actions** (`.github/workflows/build-and-test.yml`): builds on Ubuntu 22.04, macOS 14, and Windows 2022 with JDK 17 and 21; runs both plugged-in and unit test suites
- **Jenkinsfile**: internal Eclipse Foundation infrastructure builds

Version management:
```bash
# Bump version across all pom.xml and MANIFEST.MF files
mvn org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=X.Y.Z
# Note: target platform and test MANIFEST.MF files need manual updates
```
