If you're running Epsilon from source, this example should work out of the box.
If not, you'll need to do the following
 - In the Plug-ins view (Window->Show View->Plug-ins), find the org.eclipse.epsilon.workflow plugin
 - Right-click on it and select Import As -> Source Project
 - Go back to your Package Explorer, right click on the ant folder under the imported org.eclipse.epsilon.workflow project
   and select Build Path -> Use as Source Folder 