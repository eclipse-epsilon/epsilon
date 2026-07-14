pre {
  var rByLabel = RightTree!Tree.all.mapBy(t|t.label);
}

rule LeftWithRight
  match l : LeftTree!Tree
  with r : RightTree!Tree
  from: rByLabel.getOrDefault(l.label, Set {})
{
  compare: true
  do {
    var ld = l.depth();
    var rd = r.depth();
    (l.label + ' (depth ' + ld + ' ) matched with ' + r.label + ' (depth ' + rd + ')').println();
  }
}

@cached
operation LeftTree!Tree depth() {
  if (self.eContainer().isDefined()) {
    return 1 + self.eContainer().depth();
  } else {
    return 0;
  }
}