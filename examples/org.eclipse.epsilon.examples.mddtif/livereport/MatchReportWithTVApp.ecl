rule MatchReportWithText
	match t : OldTVApp!Text
	with r : Report!Report {
	
	compare : r.name = t.name
	
}
