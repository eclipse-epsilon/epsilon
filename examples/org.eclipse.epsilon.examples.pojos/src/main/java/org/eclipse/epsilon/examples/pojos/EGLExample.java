package org.eclipse.epsilon.examples.pojos;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class EGLExample {
    public static void main(String[] args) throws Exception {
        
        // Set up the project POJO (plain-old Java object)
        Project project = new Project();

        Task analysis = new Task();
        analysis.setName("Analysis");
        analysis.setDuration(3);
        project.getTasks().add(analysis);

        Task design = new Task();
        design.setName("Design");
        design.setDuration(6);
        project.getTasks().add(design);
        
        // Parse the EGL template to run on the POJO
        EglModule module = new EglModule();
        module.parse(
            "[%for (task in project.tasks){%]\n" +
            "- Task: [%=task.name%] ([%=task.duration%] months)\n" +
            "[%}%]"
        );
        
        // Make the project POJO available to the EGL template
        module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("project", project));

        // Execute the EGL template and print its output
        Object result = module.execute();

        System.out.println("Result");
        System.out.println(result);
    }
}
