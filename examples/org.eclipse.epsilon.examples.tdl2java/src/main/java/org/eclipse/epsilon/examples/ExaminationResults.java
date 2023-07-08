package org.eclipse.epsilon.examples;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.lang.String;
import java.lang.Integer;

public class ExaminationResults {

    protected List<Result> results = new ArrayList<>();
    
    protected LinkedHashMap<String, List<Result>> studentIndex = new LinkedHashMap<>();
    protected LinkedHashMap<String, List<Result>> moduleIndex = new LinkedHashMap<>();

    public Result add(String student, String module, Integer mark) {

        Result result = new Result(student, module, mark);
        
        List<Result> studentIndexValues = studentIndex.get(student);
        if (!studentIndex.containsKey(student)) {
            studentIndexValues = new ArrayList<>();
            studentIndex.put(student, studentIndexValues);
        }
        studentIndexValues.add(result);

        List<Result> moduleIndexValues = moduleIndex.get(module);
        if (!moduleIndex.containsKey(module)) {
            moduleIndexValues = new ArrayList<>();
            moduleIndex.put(module, moduleIndexValues);
        }
        moduleIndexValues.add(result);

        results.add(result);
        return result;
    }

    public List<Result> searchByStudent(String student) {
        return studentIndex.get(student);
    }

    public List<Result> searchByModule(String module) {
        return moduleIndex.get(module);
    }

}