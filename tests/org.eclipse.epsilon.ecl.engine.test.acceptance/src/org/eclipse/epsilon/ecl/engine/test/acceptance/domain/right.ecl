rule T2T 
	match l : Left!Tree
	with r : Right!Tree from: Right!Tree.all.select(t|t.name = l.name) {
	
	compare : l.name = r.name and 
		((l.parent == null and r.parent == null) or l.parent.matches(r.parent))
}

post {
	for (t in matchTrace.reduced) {
		(t.left + " <-> " + t.right).println();
	}
}