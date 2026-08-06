rule MatchNodes
    match l : Left!Node
    with r : Right!Node {

    compare : l.name = r.name
}

rule MatchEdges
    match l : Left!Edge
    with r : Right!Edge {

    compare : l.source.matches(r.source)
        and l.target.matches(r.target)
}

rule MatchGraphs
    match l : Left!Graph
    with r : Right!Graph {

    compare : true
}
