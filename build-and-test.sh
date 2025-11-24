#!/bin/bash

# Exit on first failure
set -e

# Download the EMF Compare maven artifact
bash plugins/org.eclipse.epsilon.eunit.cmp.emf/download-emf-compare.sh

# Build and install the Jena uberjar
pushd releng/org.eclipse.epsilon.jena.uberjar 
mvn install
popd

# Build
mvn -T 1C clean install

# Plugged-In tests
mvn -f tests/org.eclipse.epsilon.test verify -P plugged

# Full test suite
mvn -f tests/org.eclipse.epsilon.test surefire:test -P unit

# Build standalone Maven artifacts without Tycho
mvn -T 1C -f pom-plain.xml compile

# Build update site
mvn -f releng verify -P updatesite

# Bump versions (replace X.Y.Z with appropriate numbers)
#mvn org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=X.Y.Z-SNAPSHOT
#mvn versions:set-property -Dproperty=epsilon.version -DnewVersion=X.Y.Z-SNAPSHOT
#mvn -f pom-plain.xml versions:set -DnewVersion=X.Y.Z-SNAPSHOT
# Remember to manually bump the MANIFEST.MF of o.e.e.target and o.e.e.test

# Query project version
#mvn help:evaluate -Dexpression=project.version -q -DforceStdout 
