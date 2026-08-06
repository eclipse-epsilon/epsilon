rule Root2Root 
	match l : Left!t_tree
	with r : Right!t_tree {
	
	guard : l.parent.isUndefined() 
		and r.parent.isUndefined()
	
	compare : true
		
}

rule Tree2Tree
	match l : Left!t_tree
	with r : Right!t_tree {

	guard : l.a_label <> "t5" 
		and l.parent.isDefined()
		and r.parent.isDefined()
	
	compare: l.a_label == r.a_label 
		and l.parent.matches(r.parent)
	
}