#!/bin/bash

# Script for listing dependencies and filing reviews
# Usage: ./dash-license-check.sh [-r|-review]
#
# If you use -review, you need to set the DASH_API_TOKEN environment variable
# while running this script, e.g.:
#
#   DASH_API_TOKEN=abcdefghijkl ./dash-license-check.sh -review
#
# In Bash shells, it's recommended to add one space before the first letter of
# the command, so it will not be recorded in your command history.

# Configuration

DASH_PROJECT_ID=modeling.epsilon
DASH_REPO=https://github.com/eclipse/epsilon

# Options

DASH_OPTIONS_TYCHO="-Ddash.summary=DEPENDENCIES"
DASH_OPTIONS_PLAIN="-Ddash.summary=DEPENDENCIES-PLAIN"
for arg in "$@"; do
    shift
    if [ "$arg" = "-r" -o "$arg" = "--review" ]; then
        if test -z "$DASH_API_TOKEN"; then
            echo "When requesting a review, please set the DASH_API_TOKEN environment variable."
            exit 1
        fi
        DASH_OPTIONS_TYCHO="-Ddash.iplab.token=${DASH_API_TOKEN} -Ddash.projectId=${DASH_PROJECT_ID} -Ddash.repo=${DASH_REPO}"
        DASH_OPTIONS_PLAIN="$DASH_OPTIONS_TYCHO"
    fi
done

# Plain Maven build

mvn -f pom-plain.xml org.eclipse.dash:license-tool-plugin:license-check \
    -DexcludeGroupIds=org.eclipse \
    $DASH_OPTIONS_PLAIN

# Tycho build (-Dtycho.target.eager=true is for future Tycho 4 compatibility)
#
# Need to use excludeArtifactIds for all EMF dependencies, due to how Tycho
# defines groupId / artifactId for dependencies:
#
# https://github.com/eclipse/dash-licenses/issues/130#issuecomment-1013247062
#
# To produce that list of excluded artifact IDs, we can use a command like
# this one:
#
# grep restricted DEPENDENCIES | cut --delim=/ -f4 | grep org.eclipse | paste -sd ,

mvn org.eclipse.dash:license-tool-plugin:license-check \
    -Dtycho.target.eager=true $DASH_OPTIONS_TYCHO \
    -DexcludeArtifactIds=org.eclipse.emf.cdo.common,org.eclipse.emf.cdo.net4j,org.eclipse.emf.cdo.server.net4j,org.eclipse.emf.cdo.server,org.eclipse.emf.cdo,org.eclipse.emf.codegen.ecore,org.eclipse.emf.codegen,org.eclipse.emf.common.ui,org.eclipse.emf.common,org.eclipse.emf.compare.edit,org.eclipse.emf.compare.ide.ui,org.eclipse.emf.compare.ide,org.eclipse.emf.compare.rcp.ui,org.eclipse.emf.compare.rcp,org.eclipse.emf.compare,org.eclipse.emf.databinding,org.eclipse.emf.ecore.edit,org.eclipse.emf.ecore.editor,org.eclipse.emf.edit.ui,org.eclipse.emf.edit,org.eclipse.emf.emfatic.core,org.eclipse.emf.emfatic.ui,org.eclipse.emf.mapping.ecore2xml,org.eclipse.emf.transaction,org.eclipse.emf.validation,org.eclipse.emf.workspace,org.eclipse.emf,org.eclipse.gymnast.runtime.core,org.eclipse.gymnast.runtime.ui,org.eclipse.net4j.jvm,org.eclipse.net4j.util,org.eclipse.net4j,org.eclipse.xsd
