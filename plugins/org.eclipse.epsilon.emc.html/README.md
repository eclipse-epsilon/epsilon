# Epsilon-HTML Integration
Eclipse plugins that extend Epsilon's Model Connectivity (EMC) layer with support for managing HTML documents using languages of the [Epsilon platform](http://www.eclipse.org/epsilon) to perform activities such as code generation, HTML validation and transformation.

## How to Run
* Install the latest interim version of Epsilon or clone its Eclipse Epsilon Git repo and import all projects under /plugins to your Eclipse workspace
* Clone this Git repo
* Import the `org.eclipse.epsilon.emc.html` and `org.eclipse.epsilon.emc.html.dt` projects into your workspace
* Run the main method of the `HtmlModel` class in `org.eclipse.epsilon.emc.html` **or**
* Start a nested Eclipse instance by selecting the `org.eclipse.epsilon.emc.html project` and clicking `Run As -> Eclipse Application`
* Import the `org.eclipse.epsilon.emc.html.examples.dblp` and `org.eclipse.epsilon.emc.html.examples.bootstrap` projects into your nested Eclipse workspace
* Right click on `fetch-bibtex.launch` and select `Run As -> fetch-bibtex` or `bootstrap.launch` and select `Run As -> bootstrap`

## How to Script HTML Documents
* The HTML driver reuses [the syntax of the Epsilon XML driver](https://www.eclipse.org/epsilon/doc/articles/plain-xml/) to query HTML documents.
