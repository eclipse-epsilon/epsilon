# ETL example

This is an example project of an [ETL](https://eclipse.dev/epsilon/doc/etl/) model-to-model transformation using the RDF resource.

Two launch files are provided:

* The `(EPackage by URI)` requires registering the `01-base.emf` and `02-combined.emf` metamodels first in Eclipse.
  With [Eclipse Emfatic](https://eclipse.dev/emfatic/) installed, this can be done by right-clicking on the file from the "Package Explorer" or "Project Explorer" and selecting "Register EPackages".
* The `(use Emfatic files)` option only requires having installed Emfatic. It will directly load and register those metamodels for the transformation.
