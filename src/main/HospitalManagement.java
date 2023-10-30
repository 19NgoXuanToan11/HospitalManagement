package main;

import java.text.ParseException;

import manage.Hospital;

import menu.Menu;

public class HospitalManagement {
    public static void main(String[] args) throws ParseException {
        Hospital hsp = new Hospital();

        Menu menu = new Menu("Hospital Management Program");
        menu.addNewOption("         1. Nurse's management");
        menu.addNewOption("         2. Patient's management");
        menu.addNewOption("         3. Exit!");

        Menu menuNurse = new Menu("Nurse Management");
        menuNurse.addNewOption("       1. Create a nurse");
        menuNurse.addNewOption("       2. Find the nurse");
        menuNurse.addNewOption("       3. Update the nurse");
        menuNurse.addNewOption("       4. Delete the nurse");
        menuNurse.addNewOption("       5. Quit - Other");

        Menu menuPatient = new Menu("Patient Management");
        menuPatient.addNewOption("     1. Add a patient");
        menuPatient.addNewOption("     2. Display patient");
        menuPatient.addNewOption("     3. Sort the patients list");
        menuPatient.addNewOption("     4. Save data");
        menuPatient.addNewOption("     5. Load data");
        menuPatient.addNewOption("     6. Quit - Other");

        int choice;
        do {
            menu.printMenu();
            choice = menu.getChoice();
            switch (choice) {
                case 1:
                    int choiceNurse;
                    do {
                        menuNurse.printMenu();
                        choiceNurse = menuNurse.getChoice();
                        switch (choiceNurse) {
                            case 1:
                                hsp.createNurse();
                                break;
                            case 2:
                                hsp.findNurse();
                                break;
                            case 3:
                                hsp.updateNurse();
                                break;
                            case 4:
                                hsp.deleteNurse();
                                break;
                            case 5:
                                System.out.println("Return main menu!");
                                break;
                        }
                    } while (choiceNurse != 5);
                    break;
                    
                case 2:
                    int choicePatient;
                    do {
                        menuPatient.printMenu();
                        choicePatient = menuPatient.getChoice();

                        switch (choicePatient) {
                            case 1:
                                hsp.addPatient();
                                break;
                            case 2:
                                hsp.displayPatients();
                                break;
                            case 3:
                                hsp.sortPatient();
                                break;
                            case 4:
                                hsp.saveData();
                                break;
                            case 5:
                                hsp.loadData();
                                break;
                            case 6:
                                System.out.println("Return main menu!");
                                break;
                        }
                    } while (choicePatient != 6);
                    break;

                case 3:
                    System.out.println("Exist!");
                    break;
            }
        } while (choice != 3);
    }
}
