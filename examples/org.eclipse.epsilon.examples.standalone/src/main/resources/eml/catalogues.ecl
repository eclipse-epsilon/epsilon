rule Catalogue2Catalogue
	match c1 : Catalogue1!t_catalogue
	with c2 : Catalogue2!t_catalogue {
	
	compare : true
		
}

rule Product2Product
	match p1 : Catalogue1!t_product
	with p2 : Catalogue2!t_product {
	
	compare : p1.a_id = p2.a_id
	
}