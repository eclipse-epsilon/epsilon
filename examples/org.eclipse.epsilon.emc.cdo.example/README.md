# Example query

This a toy example that connects to a CDO repository on the same JVM, creates a Tree object, and prints the current list of instances in the model.

## Preparations

To run this example, you will need a local CDO repository called "repo" with a "model" model resource.
To create it, follow these steps:

1. In the "CDO Repositories" view, create a new local repository named "repo". Leave TCP and security disabled.
1. Ensure in "CDO Repositories" that the icon for the new "Local Repository" is blue (you are connected to CDO).
1. Right-click on the repository and select "Checkout". A new "Online Transactional Checkout" folder will be added to the "Project Explorer"  view.
1. Right-click on the "Online Transactional Checkout" and select "New - Model Resource". Name the new model resource "model".

You will also need to register the sample `Tree.ecore` metamodel in your global registry.
Right-click on the `Tree.ecore` file in this folder, and select "Register EPackages".

## Running the query

You can now run the sample query by right-clicking `populateTree.launch` in the Project Explorer, and selecting `Run as - populateTree`.
You should obtain this output:

```
Now there are these many instances: 1
```

The EMC CDO driver will first check the CDO package registry for the `Tree` type, and since it won't be there yet, it will fall back to your global package registry (`EPackage.Registry.INSTANCE`).
On the first execution of the query, CDO will automatically register the `Tree` metamodel into the CDO package registry when the transaction was committed upon saving the model.

If you re-run the query, you will see the number of instances increasing by 1 each time.
These later runs will use the version of `Tree` that has already been registered into CDO.
