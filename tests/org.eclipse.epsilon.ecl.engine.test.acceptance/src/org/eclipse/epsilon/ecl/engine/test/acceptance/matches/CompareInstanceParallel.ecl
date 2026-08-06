pre {
	var Thread = Native("java.lang.Thread");
	var mainThread = Thread.currentThread();
}

rule TLN 
	match l : Left!TLN with r : Right!TLN extends TAnyLN {
	compare : l.text = r.text
}

@lazy
@abstract
rule TAnyLN 
	match l : Left!TAnyLN with r : Right!TAnyLN {
	compare : l.text1 = r.text1
	do {
		assert(
			l.dOI.asBag().parallelMatches(r.dOI.asBag()) and r.dOI.asBag().parallelMatches(l.dOI.asBag()),
			"[TAnyLN] Expected match on unordered collection."
		);
		assert(
			not (l.dOI.asSequence().parallelMatches(r.dOI.asSequence()) or r.dOI.asSequence().parallelMatches(l.dOI.asSequence())),
			"[TAnyLN] Didn't expect match on ordered collection."
		);
		assert(
			l.dOI.parallelMatches(l.dOI) and (r.dOI.parallelMatches(r.dOI)),
			"[TAnyLN] Expected match on idempotent collection."
		);
	}
}

@lazy
rule TDOI_lr
	match l : Left!TDOI with r : Right!TDOI {
	guard : Thread.currentThread() <> mainThread
	compare : l.access = r.access
	do {
		assertTrue(l.accessControl.isDefined());//print(" # TDOI-l:");
		assertTrue(r.accessControl.isDefined());//println(" <-> TDOI-r:");
	}
}

@lazy
rule TDOI_rl
	match r : Right!TDOI with l : Left!TDOI {
	guard : Thread.currentThread() <> mainThread
	compare : r.access = l.access
	do {
		assertTrue(r.accessControl.isDefined());//print(" # TDOI-r:");
		assertTrue(l.accessControl.isDefined());//println(" <-> TDOI-l:");
	}
}

post {
	assert(Thread.currentThread() == mainThread);
	for (m in System.context.matchTrace.getMatches()) {
		assertTrue(("Match:" + m.matching + " - Info:" + m.Info + " - " + m.left + " <-> " + m.right).isDefined());
	}
}