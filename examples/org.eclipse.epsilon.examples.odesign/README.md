# Processing Sirius ODesign Models with Epsilon

This example shows how to read a Sirius ODesign graphical syntax specification model and query it with EOL.

https://github.com/eclipse/epsilon/blob/770435007e72764041d6d8d12cca006a3aedb747/examples/org.eclipse.epsilon.examples.odesign/src/org/eclipse/epsilon/examples/odesign/App.java#L1-L36

## How to Run

- Install Epsilon 2.5 and Sirius 7.4.0 (Specifier Environment) in your Eclipse.
- Import this project.
- Change the `odesign.setModelFile("/absolute/path/of/your.odesign")` line of `App.java` to point to your Sirius `.odesign` file.
- Run `App.java`.

## Updating the ODesign Model

- If you want to save any changes made by your EOL model call `odesign.store()` after `module.execute()`.
