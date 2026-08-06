package org.eclipse.epsilon.examples.graphstream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccessRecorder;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessExecutionListener;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccesses;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class PropertyAccessGraph {
	
	public static void main(String args[]) throws Exception {
		System.setProperty("org.graphstream.ui", "swing");
		new PropertyAccessGraph().run();
		
	}
	
	public void run() throws Exception {
		
		// Make Emfatic and Flexmi known to EMF
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());
		
		// Set up the graph
		Graph graph = new MultiGraph("Property Access Graph");
		// Set its stylesheet and antialias properties
		graph.setAttribute("ui.stylesheet", "url('" + PropertyAccessGraph.class.getResource("style.css").toURI().toString() + "')");
		graph.setAttribute("ui.antialias", "true");
		graph.display();
		
		// Set up the EMF model
		EmfModel model = new EmfModel();
		model.setName("M");
		model.setMetamodelUri(EcorePackage.eNS_URI);
		model.setModelFileUri(URI.createURI(PropertyAccessGraph.class.getResource("oo.emf").toURI().toString()));
		model.load();
				
		// Create a node for each element in the model
		// and keep trace links in the "nodes" map
		Map<Object, Node> nodes = new HashMap<>();
		for (Object o : model.allContents()) {
			EObject e = (EObject) o;
			Node node = graph.addNode(UUID.randomUUID().toString());
			node.setAttribute("ui.class", e.eClass().getName());
			sleep(20);
			nodes.put(o, node);
		}
		
		// Create edges for every non-derived reference
		// in the model - including an edge to each element's container
		for (Object o : model.allContents()) {
			EObject e = (EObject) o;
			for (EReference r : e.eClass().getEAllReferences()) {
				List<Object> values = new ArrayList<>();
				
				if (!(e.eIsSet(r) || r.isDerived())) continue;
				
				if (r.isMany()) {
					values.addAll((Collection) e.eGet(r));
				}
				else {
					if (e.eIsSet(r)) values.add(nodes.get(e.eGet(r)));
				}
				
				for (Object value : values) {
					if (nodes.containsKey(value) && nodes.containsKey(o)) {
						graph.addEdge(UUID.randomUUID().toString(), nodes.get(o), nodes.get(value));
						sleep(20);
					}
				}
			}
			
			// The edge to the container
			if (e.eContainer() instanceof EObject && !nodes.get(o).hasEdgeBetween(nodes.get(e.eContainer()))) {
				graph.addEdge(UUID.randomUUID().toString(), nodes.get(o), nodes.get(e.eContainer()));
				sleep(20);
			}
		}
		
		// Create an EOL module that prints the names of all elements in the model
		EolModule module = new EolModule();
		module.parse(PropertyAccessGraph.class.getResource("ecore.eol").toURI());
		
		// Attach a listener to get notified every time a property
		// of a model element is accessed (e.g. e.name)
		module.getContext().getExecutorFactory().addExecutionListener(new PropertyAccessExecutionListener(new IPropertyAccessRecorder() {
			
			@Override
			public void stopRecording() {}
			
			@Override
			public void startRecording() {}
			
			@Override
			public void record(Object modelElement, String propertyName) {
				
				// When a property of the model element is accessed add a shadow to it
				// of proportional size to the # of times it has been accessed
				Node node = nodes.get(modelElement);
				if (node != null) {
					Integer accesses = node.getAttribute("accesses", Integer.class);
					if (accesses == null) { accesses = 0; }
					accesses++;
					node.setAttribute("accesses", accesses);
					int shadowSize = accesses * 5;
					
					// Add the shadow
					node.setAttribute("ui.style", "	shadow-mode: gradient-radial; \n"
							+ "	shadow-width: " + shadowSize + "px; \n"
							+ "	shadow-color: white; \n"
							+ "	shadow-offset: 0px;");
					
					// Wait for a bit to give time to the user to observe what is happening*/
					sleep(50);
				}
			}
			
			@Override
			public PropertyAccesses getPropertyAccesses() { return null; }
		}));
		
		// Make the model available to the EOL program
		module.getContext().getModelRepository().addModel(model);
		
		// Execute the program
		module.execute();
		
	}
	
	public void sleep(int millis) {
		long now = System.currentTimeMillis();
		while (System.currentTimeMillis() - now < millis) System.out.print("");
	}
}
