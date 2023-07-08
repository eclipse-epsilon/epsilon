package org.eclipse.epsilon.examples;
import java.lang.String;
import java.lang.Integer;

public class Result {

    protected String student;
    protected String module;
    protected Integer mark;

    public Result(String student, String module, Integer mark) {
        this.student = student;
        this.module = module;
        this.mark = mark;
    
    }

    public String getStudent() {
        return this.student;
    }

    public String getModule() {
        return this.module;
    }

    public Integer getMark() {
        return this.mark;
    }

}