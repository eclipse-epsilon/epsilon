package org.eclipse.epsilon.examples;

public class ExaminationResultsApp {
    
    public static void main(String[] args) {
        
        ExaminationResults results = new ExaminationResults();
        results.add("dk135", "ENG1", 53);
        results.add("dk135", "ENG2", 58);
        results.add("ab123", "ENG1", 60);
        results.add("ab123", "ENG2", 62);
        
        results.searchByStudent("dk135").forEach(s -> { 
            System.out.println(s.getModule() + "/" + s.getMark());
        });
        
    }

}
