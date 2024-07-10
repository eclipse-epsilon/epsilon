package org.eclipse.epsilon.eol.dap.variables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class MapSliceReference extends IdentifiableReference<Map<Object, Object>> {

	private final String name;
	private final int from;
	private final int to;

	/**
	 * Creates a reference to a slice of a collection. The slice will include all
	 * the elements in the semi-closed range {@code [from, to)}.
	 *
	 * @param name Name of the collection (the name of the slice will be
	 *             automatically computed).
	 * @param t    Target collection.
	 * @param from Starting 0-based index, included.
	 * @param to   Final 0-based index, excluded.
	 */
	public MapSliceReference(IEolContext context, String name, Map<Object, Object> target, int from, int to) {
		super(context, target);
		this.from = from;
		this.to = to;
		this.name = name;
	}

	@Override
	public String getName() {
		return String.format("%s[%d..%d]", name, from, to - 1);
	}

	@Override
	public String getValue() {
		return String.format("<slice with %d elements>", to - from);
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		final List<IVariableReference> vars = new ArrayList<>(to - from);
		final Iterator<Object> itEntries = target.keySet().iterator();

		// Skip to the start of the list
		int i = 0;
		while (i < from && itEntries.hasNext()) {
			itEntries.next();
			i++;
		}

		// Turn the map entries into references
		while (i < to && itEntries.hasNext()) {
			Object key = itEntries.next();
			vars.add(state.putOrGetReference(
				new MapEntryReference(context, String.format("%s[%d]", name, i), target, key)));
			i++;
		}

		return vars;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(from, name, to);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapSliceReference other = (MapSliceReference) obj;
		return from == other.from && Objects.equals(name, other.name) && to == other.to;
	}

}
