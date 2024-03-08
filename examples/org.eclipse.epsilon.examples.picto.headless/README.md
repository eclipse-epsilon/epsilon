# Headless Computation of Picto Views

This example shows how to trigger computation of Picto views in a headless way (without opening the Picto view).

## How to run this example

- In your first Eclipse workspace, import this project
- Run a nested Eclipse workspace
- In the nested workspace, import the [org.eclipse.epsilon.examples.picto.socialnetwork](../org.eclipse.epsilon.examples.picto.socialnetwork) project
- Register its `socialnetwork.emf` metamodel
- Right click on `socialnetwork-standalone.picto` in the Project Explorer view and select the `Export Views` menu item
- You should see the names and computed content of the views in the console view

## Notes

- Please note that this example only works with [standalone Picto files](https://eclipse.dev/epsilon/doc/picto/#using-picto-in-standalone-mode-with-many-models)