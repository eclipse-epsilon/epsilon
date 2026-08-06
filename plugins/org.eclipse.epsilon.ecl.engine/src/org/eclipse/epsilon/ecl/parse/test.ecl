rule A match l : L!A with r : R!A {}
rule B match l : L!B with r : R!B {}

rule EAttribute2EAttribute
	match l : Left!EAttribute
	with r : Right!EAttribute 
	extends A, B {
	
	compare :
		l.name = r.name and 
		l.defaultValueLiteral = r.defaultValueLiteral and 
		l.eContainingClass.matches(r.eContainingClass)  
	
	do {
		doIt();
	}
}

rule EAnnotation2EAnnotation
	match l : Left!EAnnotation
	with r : Right!EAnnotation {
	
	compare :
		l.source = r.source and 
		l.eModelElement.matches(r.eModelElement)  
		
}
rule EClass2EClass
	match l : Left!EClass
	with r : Right!EClass {
	
	compare :
		l.name = r.name and 
		l.instanceClassName = r.instanceClassName and 
		l.instanceTypeName = r.instanceTypeName and 
		l.ePackage.matches(r.ePackage)  
		
}
rule EClassifier2EClassifier
	match l : Left!EClassifier
	with r : Right!EClassifier {
	
	compare :
		l.name = r.name and 
		l.instanceClassName = r.instanceClassName and 
		l.instanceTypeName = r.instanceTypeName and 
		l.ePackage.matches(r.ePackage)  
		
}
rule EDataType2EDataType
	match l : Left!EDataType
	with r : Right!EDataType {
	
	compare :
		l.name = r.name and 
		l.instanceClassName = r.instanceClassName and 
		l.instanceTypeName = r.instanceTypeName and 
		l.ePackage.matches(r.ePackage)  
		
}
rule EEnum2EEnum
	match l : Left!EEnum
	with r : Right!EEnum {
	
	compare :
		l.name = r.name and 
		l.instanceClassName = r.instanceClassName and 
		l.instanceTypeName = r.instanceTypeName and 
		l.ePackage.matches(r.ePackage)  
		
}
rule EEnumLiteral2EEnumLiteral
	match l : Left!EEnumLiteral
	with r : Right!EEnumLiteral {
	
	compare :
		l.name = r.name and 
		l.literal = r.literal and 
		l.eEnum.matches(r.eEnum)  
		
}
rule ENamedElement2ENamedElement
	match l : Left!ENamedElement
	with r : Right!ENamedElement {
	
	compare :
		l.name = r.name 
		
}
rule EOperation2EOperation
	match l : Left!EOperation
	with r : Right!EOperation {
	
	compare :
		l.name = r.name and 
		l.eContainingClass.matches(r.eContainingClass)  
		
}
rule EPackage2EPackage
	match l : Left!EPackage
	with r : Right!EPackage {
	
	compare :
		l.name = r.name and 
		l.nsURI = r.nsURI and 
		l.nsPrefix = r.nsPrefix and 
		l.eSuperPackage.matches(r.eSuperPackage)  
		
}
rule EParameter2EParameter
	match l : Left!EParameter
	with r : Right!EParameter {
	
	compare :
		l.name = r.name and 
		l.eOperation.matches(r.eOperation)  
		
}
rule EReference2EReference
	match l : Left!EReference
	with r : Right!EReference {
	
	compare :
		l.name = r.name and 
		l.defaultValueLiteral = r.defaultValueLiteral and 
		l.eContainingClass.matches(r.eContainingClass)  
		
}
rule EStructuralFeature2EStructuralFeature
	match l : Left!EStructuralFeature
	with r : Right!EStructuralFeature {
	
	compare :
		l.name = r.name and 
		l.defaultValueLiteral = r.defaultValueLiteral and 
		l.eContainingClass.matches(r.eContainingClass)  
		
}
rule ETypedElement2ETypedElement
	match l : Left!ETypedElement
	with r : Right!ETypedElement {
	
	compare :
		l.name = r.name 
		
}
rule EStringToStringMapEntry2EStringToStringMapEntry
	match l : Left!EStringToStringMapEntry
	with r : Right!EStringToStringMapEntry {
	
	compare :
		l.key = r.key and 
		l.value = r.value 
		
}
rule ETypeParameter2ETypeParameter
	match l : Left!ETypeParameter
	with r : Right!ETypeParameter {
	
	compare :
		l.name = r.name 
		
}

