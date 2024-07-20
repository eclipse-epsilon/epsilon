# Processing Sirius ODesign Models with Epsilon

This example shows how to read a Sirius ODesign graphical syntax specification model and query it with EOL.

## How to Run

- Install Epsilon 2.5 and Sirius 7.4.0 (Specifier Environment) in your Eclipse.
- Import this project.
- Change the `odesign.setModelFile("/absolute/path/of/your.odesign")` line of `App.java` to point to your Sirius `.odesign` file.
- Run `App.java`.

## Updating the ODesign Model

- If you want to save any changes made by your EOL model call `odesign.store()` after `module.execute()`.