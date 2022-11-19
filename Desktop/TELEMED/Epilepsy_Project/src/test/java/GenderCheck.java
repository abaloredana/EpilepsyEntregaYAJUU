
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author loredanaabalo
 */
public class GenderCheck {
     if (malecheck.isSelected() && femalecheck.isSelected()) {
            JOptionPane.showMessageDialog(null, "You cannot choose both genders. Select only one.");
            patient.gender = "uwu";
            malecheck.setSelected(false);
            femalecheck.setSelected(false);
            
            while (patient.gender == "uwu") {

                if (malecheck.isSelected() && !(femalecheck.isSelected())) {
                    patient.gender = "Male";
                    break;
                } else if (femalecheck.isSelected() && !(malecheck.isSelected())) {
                    patient.gender = "Female";
                    System.out.println("Gender" + patient.gender);
                    break;
                }
            }
        } else if (malecheck.isSelected() && !(femalecheck.isSelected())) {
            patient.gender = "Male";

        } else if (femalecheck.isSelected() && !(malecheck.isSelected())) {
            patient.gender = "Female";

        }
        System.out.println("gender: " + patient.gender);
    
}
