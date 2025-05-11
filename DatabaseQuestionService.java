/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package webhooksolver.service;

public class DatabaseQuestionService {
    

    public String solveQuestion(String regNo) {
        // Extract last two digits of regi number
        int lastTwoDigits = Integer.parseInt(regNo.substring(regNo.length() - 2));
        
        if (lastTwoDigits % 2 == 1) {
            return solveQuestion1();
        } else {
            return solveQuestion2();
        }
    }

    private String solveQuestion1() {
        // Sol for Q 1
        return "SELECT d.doctor_id, d.doctor_name, COUNT(p.patient_id) AS patient_count " +
               "FROM doctors d " +
               "LEFT JOIN patients p ON d.doctor_id = p.doctor_id " +
               "GROUP BY d.doctor_id, d.doctor_name " +
               "ORDER BY patient_count DESC, d.doctor_name ASC";
    }

    private String solveQuestion2() {
        // Sol for Q 2
        return "SELECT p.patient_id, p.patient_name, SUM(m.cost) AS total_medicine_cost " +
               "FROM patients p " +
               "JOIN patient_medicines pm ON p.patient_id = pm.patient_id " +
               "JOIN medicines m ON pm.medicine_id = m.medicine_id " +
               "GROUP BY p.patient_id, p.patient_name " +
               "HAVING SUM(m.cost) > 1000 " +
               "ORDER BY total_medicine_cost DESC";
    }
}

