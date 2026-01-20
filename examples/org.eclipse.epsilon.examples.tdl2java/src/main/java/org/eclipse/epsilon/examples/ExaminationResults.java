package org.eclipse.epsilon.examples;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.lang.String;
import java.lang.Integer;

public class ExaminationResults {

    protected List<Row> rows = new ArrayList<>();
    
    protected LinkedHashMap<String, List<Row>> studentIndex = new LinkedHashMap<>();
    protected LinkedHashMap<String, List<Row>> moduleIndex = new LinkedHashMap<>();

    public Row add(String student, String module, Integer mark) {

        Row row = new Row(student, module, mark);
        
        List<Row> studentIndexValues = studentIndex.get(student);
        if (!studentIndex.containsKey(student)) {
            studentIndexValues = new ArrayList<>();
            studentIndex.put(student, studentIndexValues);
        }
        studentIndexValues.add(row);

        List<Row> moduleIndexValues = moduleIndex.get(module);
        if (!moduleIndex.containsKey(module)) {
            moduleIndexValues = new ArrayList<>();
            moduleIndex.put(module, moduleIndexValues);
        }
        moduleIndexValues.add(row);

        rows.add(row);
        return row;
    }

    public List<Row> getRows() {
        return Collections.unmodifiableList(rows);
    }

    public List<Row> searchByStudent(String student) {
        return studentIndex.get(student);
    }

    public List<Row> searchByModule(String module) {
        return moduleIndex.get(module);
    }

    public class Row {

        protected String student;
        protected String module;
        protected Integer mark;

        public Row(String student, String module, Integer mark) {
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
}
