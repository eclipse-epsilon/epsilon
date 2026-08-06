package org.eclipse.epsilon.examples.pojos;

import java.util.ArrayList;
import java.util.List;

public class Project {
    
    protected String name;
    protected List<Task> tasks = new ArrayList<>();

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
