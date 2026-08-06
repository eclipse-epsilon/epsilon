pre {
	
}

@abstract
rule NamedElements
	match l : Left!NamedElement
	with r : Right!NamedElement {
	
	compare : l.name = r.name

}

@abstract
rule PackageableElements
	match l : Left!PackageableElement
	with r : Right!PackageableElement 
	extends NamedElements {
	
	compare : l.package.matches(r.package)
	
}

rule Packages 
	match l : Left!Package 
	with r : Right!Package
	extends PackageableElements {
	
}

rule Models
	match l : Left!Model
	with r : Right!Model {
	
	compare : true
	
}	

@greedy
rule Classifiers 
	match l : Left!Classifier 
	with r : Right!Classifier 
	extends PackageableElements {
	
}

rule Features
	match l : Left!Feature
	with r : Right!Feature 
	extends NamedElements {
	
	compare : l.owner.matches(r.owner)
	
}

rule Operations
	match l : Left!Operation
	with r : Right!Operation
	extends Features {
	
	compare { 
	  return l.parameters.size() = r.parameters.size() and 
	    l.parameters.forAll(lp|lp.matches(r.parameters.
		  at(l.parameters.indexOf(lp))));
	}
}

rule Parameters
	match l : Left!Parameter
	with r : Right!Parameter {
	
	compare : l.type.matches(r.type)
	
}
