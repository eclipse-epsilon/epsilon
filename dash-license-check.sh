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
REVIEW=0
for arg in "$@"; do
    shift
    if [ "$arg" = "-r" -o "$arg" = "--review" ]; then
        if test -z "$DASH_API_TOKEN"; then
            echo "When requesting a review, please set the DASH_API_TOKEN environment variable."
            exit 1
        fi
        DASH_OPTIONS_TYCHO="-Ddash.iplab.token=${DASH_API_TOKEN} -Ddash.projectId=${DASH_PROJECT_ID} -Ddash.repo=${DASH_REPO}"
        DASH_OPTIONS_PLAIN="$DASH_OPTIONS_TYCHO"
        REVIEW=1
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

# The above command may highlight issues around wrapped.* artifactIds: these
# come from Maven Central, and they may be just fine, but the Dash License tool
# doesn't automatically translate them to plain Maven dependencies.
#
# One approach is to filter these out if they are not 'restricted' in
# DEPENDENCIES-PLAIN.
#
# Note that plantuml-epl is not in the plain Maven build, but it has been
# approved in IPZilla:
#   https://gitlab.eclipse.org/eclipsefdn/emo-team/iplab/-/issues/10595

find_wrapped_restricted() {
    grep 'wrapped.*restricted' DEPENDENCIES | cut --delim=/ -f4 | sed 's#wrapped.##'
}

classify_wrapped_restricted() {
    ARTIFACT_ID="$1"
    if grep -q "${ARTIFACT_ID}/.*restricted" DEPENDENCIES-PLAIN; then
        echo "${ARTIFACT_ID} is restricted in both DEPENDENCIES and DEPENDENCIES-PLAIN"
        grep "${ARTIFACT_ID}/" DEPENDENCIES*
    elif grep -q "${ARTIFACT_ID}/" DEPENDENCIES-PLAIN; then
        echo "${ARTIFACT_ID} is restricted in DEPENDENCIES but is not restricted in DEPENDENCIES-PLAIN:"
        grep "${ARTIFACT_ID}/" DEPENDENCIES*
    else
        echo "${ARTIFACT_ID} is restricted in DEPENDENCIES but does not appear in DEPENDENCIES-PLAIN"
        grep "${ARTIFACT_ID}/" DEPENDENCIES*
    fi
}

wrapped_restricted_report() {
    find_wrapped_restricted | (
        while read wrapped_restricted; do
            classify_wrapped_restricted "$wrapped_restricted"
            # add a newline between items
            echo
        done)
}

# During license search, find wrapped artifacts that are not approved in the
# DEPENDENCIES-PLAIN file.

if [ "$REVIEW" = 0 ]; then
    wrapped_restricted_report | tee DEPENDENCIES-WRAPPED-RESTRICTED
fi
