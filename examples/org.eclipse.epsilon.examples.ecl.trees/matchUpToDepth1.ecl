rule LeftWithRight
  match l : LeftTree!Tree
  in: LeftTree.resource.contents.first.upToDepth(1)
  with r : RightTree!Tree
  in: RightTree.resource.contents.first.upToDepth(1)
{
  compare: l.label == r.label
  do {
    var ld = l.depth();
    var rd = r.depth();
    (l.label + ' (depth ' + ld + ' ) matched with ' + r.label + ' (depth ' + rd + ')').println();
  }
}

operation LeftTree!Tree upToDepth(depth: Integer): Collection {
  var result : Set;
  var pending = Sequence { self };
  while (not pending.empty) {
    var e = pending.removeAt(0);
    if (e.depth() <= depth) {
      result.add(e);
      pending.addAll(e.children);
    } 
  }
  return result;
}

@cached
operation LeftTree!Tree depth() {
  if (self.eContainer().isDefined()) {
    return 1 + self.eContainer().depth();
  } else {
    return 0;
  }
}