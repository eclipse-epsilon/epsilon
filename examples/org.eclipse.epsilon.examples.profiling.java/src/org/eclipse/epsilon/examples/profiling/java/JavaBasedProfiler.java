package org.eclipse.epsilon.examples.profiling.java;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.profiling.Profiler;
import org.eclipse.epsilon.profiling.ProfilerTargetSummary;
import org.eclipse.epsilon.profiling.ProfilingExecutionListener;

public class JavaBasedProfiler {

    /*
	 * This is just a tag to identify this module: it does not have to be the filename of the script.
	 * For instance, it could have simply been "example" or "my_script".
	 *
	 * If we run the script multiple times and want the results of all its executions to be aggregated
	 * we need to use the same tag.
	 */
	private static final String EOL_NAME = "example.eol"; 

	public static void main(String[] args) throws Exception {
		EolModule module = new EolModule();
		module.parse(JavaBasedProfiler.class.getResource(EOL_NAME));
		module.getContext().setProfilingEnabled(true);

		Profiler.INSTANCE.start(EOL_NAME, "", module);
		try {
			// Enable fine-grained profiling (at the level of an AST node)
			module.getContext().getExecutorFactory().addExecutionListener(new ProfilingExecutionListener());

			module.execute();
		} finally {
			Profiler.INSTANCE.stop(EOL_NAME);
		}

		// Generates a CSV file with execution counts, aggregate times, and own ("individual") times
		try (FileWriter fw = new FileWriter("profiler.csv"); PrintWriter pw = new PrintWriter(fw)) {
			pw.println("target;count;aggregate_ms;individual_ms");
			for (ProfilerTargetSummary summary : Profiler.INSTANCE.getTargetSummaries()) {
				pw.printf("%s;%d;%d;%d\n",
					summary.getName(), summary.getExecutionCount(),
					summary.getExecutionTime().getAggregate(),
					summary.getExecutionTime().getIndividual());
			}
		}
	}

}
