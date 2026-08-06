# Employee example

This is an example of how we can separate additional information in
a different Turtle file, and model that information as a separate
EPackage which extends the base one.

## Preparation

To try this example, first follow these steps:

1. Import the project into Eclipse, with the EMF resource for RDF and Emfatic installed.
1. From the Package/Project Explorer, right-click on `orgchart.ecore` and select "Register EPackages".
1. Do the same, but for `orgchart-extra.ecore`.

## Model navigation

You may now open `myorg.rdfres` to only see the base information, or `myorg-extra.rdfres` to see the additional information laid on top of the base model.

## Model visualisation

To try the Picto visualisation, import the `../org.eclipse.epsilon.examples.rdf.emf.picto` example first.

You may then open `myorg.picto` or `myorg-extra.picto` to see the Picto visualisations of both scenarios.