rule Models 
	match l : Left!Model with r : Right!Model {
	compare : true
}

rule Class 
	match l : Left!Class with r : Right!Class {

	compare : l.name = r.name
}