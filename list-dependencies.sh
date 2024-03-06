#!/bin/bash

# Plain Maven build

mvn -f pom-plain.xml org.eclipse.dash:license-tool-plugin:license-check -Ddash.summary=DEPENDENCIES-PLAIN -DexcludeGroupIds=org.eclipse

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

mvn org.eclipse.dash:license-tool-plugin:license-check -Ddash.summary=DEPENDENCIES -Dtycho.target.eager=true \
    -DexcludeArtifactIds=org.eclipse.emf.cdo.common,org.eclipse.emf.cdo.net4j,org.eclipse.emf.cdo.server.net4j,org.eclipse.emf.cdo.server,org.eclipse.emf.cdo,org.eclipse.emf.codegen.ecore,org.eclipse.emf.codegen,org.eclipse.emf.common.ui,org.eclipse.emf.common,org.eclipse.emf.compare.edit,org.eclipse.emf.compare.ide.ui,org.eclipse.emf.compare.ide,org.eclipse.emf.compare.rcp.ui,org.eclipse.emf.compare.rcp,org.eclipse.emf.compare,org.eclipse.emf.databinding,org.eclipse.emf.ecore.edit,org.eclipse.emf.ecore.editor,org.eclipse.emf.edit.ui,org.eclipse.emf.edit,org.eclipse.emf.emfatic.core,org.eclipse.emf.emfatic.ui,org.eclipse.emf.mapping.ecore2xml,org.eclipse.emf.transaction,org.eclipse.emf.validation,org.eclipse.emf.workspace,org.eclipse.emf,org.eclipse.gymnast.runtime.core,org.eclipse.gymnast.runtime.ui,org.eclipse.net4j.jvm,org.eclipse.net4j.util,org.eclipse.net4j,org.eclipse.xsd
