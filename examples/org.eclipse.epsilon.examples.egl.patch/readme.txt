This project demonstrates the ability of EGL to patch existing files using rules annotated as @patch

1) Delete the contents of the src folder
2) Generate model code from comps.genmodel
3) Run patch.egx through patch.launch
4) ComponentInstanceImpl now has two more methods - getName() and getAction()

For simplicity, all inherited attributes in @instance classes are assumed to be single-valued