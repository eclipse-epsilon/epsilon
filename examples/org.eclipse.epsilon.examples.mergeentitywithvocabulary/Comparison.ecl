rule MatchSystemWithVocabulary
	match s : Source!System
	with v : Vocabulary!Vocabulary {
	
	compare {
		return true;
	}
}

rule MatchEntityWithTerm
	match s : Source!Entity
	with t : Vocabulary!Term {

	compare {
		return s.name = t.name or 
		t.`alias`.exists(a|a.name = s.name);
	}
}