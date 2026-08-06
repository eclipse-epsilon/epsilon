Overview
========
This project contains several example EGL programs.  Each program can be executed by 
running its .launch file. For programs 2-5, the resulting HTML is generated into a 
new folder at "./website"


1. Basic (templates/basic)
--------------------------
This program demonstrates the core features of EGL:
  - static sections emit text that is specified in the template
  - dynamic sections emit text that is derived from the input model
  
2. Coordination (templates/coordination)
----------------------------------------
This program demonstrates how EGL can be used to generate several files:
   - an EGL template for each type of file that we want to generate
   - an EGX program that traverses the input model and coordinates the generation of files
 
 3. Formatted (templates/formatted)
 ----------------------------------
 This program demonstrates that an EGL launch configuration can be configured to automatically
 format (indent) all of the text that is generated.  

 Note that the source code for this program does not differ from the coordination program. The 
 launch configuration, however, contains an extra setting which is visible from the "Generated 
 Text" tab of the Run... menu.
 
 4. Protected Regions (templates/protected_regions)
 --------------------------------------------------
 This program demonstrates the merging engine of EGL, which is used to preserve any hand-written 
 changes to generated text. To see this in action:
 
 a) Run the program.
 b) Open the website/Get up.html file.
 c) Change the protected region from:
 
 		<!-- protected region style on begin -->
		<style type="text/css">
		</style>
		<!-- protected region style end -->
		
	to:
	 	<!-- protected region style on begin -->
		<style type="text/css">
		h1 { color: red }
		</style>
		<!-- protected region style end -->

d) Make a change to the models/wakeup.model file.
e) Rerun the program and observe that your hand-written edits are preserved.

5. Trace Model (templates/trace_model)
--------------------------------------
This program demonstrates EGL's support for generating trace links, which indicate the way 
in which source model elements have contributed to the generated text. To see this in action:

a) Run the program.
b) Open the trace_model/wakeup.textlink file.
c) Double-click on one of the elements in the left-hand pane (the source model).
d) Observe that the editor navigates to a region of text that was generated from the model 
   element that you selected.
   
Note that the .textlink file is an EMF model: it is possible to write other Epsilon programs 
that manipulate .textlink files. 
				 