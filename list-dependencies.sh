#!/bin/bash

# Plain Maven build
mvn -f pom-plain.xml org.eclipse.dash:license-tool-plugin:license-check -Ddash.summary=DEPENDENCIES-PLAIN

# Tycho build
mvn org.eclipse.dash:license-tool-plugin:license-check -Ddash.summary=DEPENDENCIES
