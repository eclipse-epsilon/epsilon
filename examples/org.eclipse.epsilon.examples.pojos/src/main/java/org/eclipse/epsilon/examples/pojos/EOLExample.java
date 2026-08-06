package org.eclipse.epsilon.examples.pojos;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class EOLExample {
    
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
        
        // Parse the EOL program to run on the POJO
        EolModule module = new EolModule();
        module.parse("return project.tasks.size();");
        
        // Make the POJO available to the EOL program
        module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("project", project));

        // Execute the EOL program and print its output
        Object result = module.execute();
        
        System.out.println("Result: " + result);
    }
}