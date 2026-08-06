rule Tree2Tree 
	match l : Core!Tree
	with r : Fragment!Tree {
	
	compare : l.label = r.label

}