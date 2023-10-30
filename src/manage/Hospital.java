package manage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import obj.Nurse;
import obj.Patient;

public class Hospital {
    private final HashMap<String, Nurse> nurseMap = new HashMap<>();
    private final HashMap<String, Patient> patientMap = new HashMap<>();
    private boolean change = false;

    public void createNurse() {
        String confirm;
        do {
            String id;
            do {
                id = util.Validation.getRegexString("Input ID(Nxxxx): ", "Wrong format. Ex: N0001,...", "^[N|n]\\d{4}$");
                if (nurseMap.containsKey(id)) {
                    System.out.println("Duplicated ID. Please input again!");
                }
            } while (nurseMap.containsKey(id));

            String name = util.Validation.getString("Input name: ", "All fields are not allowed null!");

            int age = util.Validation.getAnInteger("Input age: ", "Must be a number larger than 0 "
                                                 + "and all fields are not allowed null!", 0);

            String gender;
            do {
                gender = util.Validation.getString("Input gender(Male/Female): ", "All fields are not allowed null!");
                if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
                    System.out.println("Male or Female and all fields are not allowed null!");
                }
            } while (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"));

            String address = util.Validation.getString("Input address: ", "All fields are not allowed null!");

            String phone = util.Validation.getRegexString("Input phone: ", "Must be a number and all fields are not allowed null!", "^\\d{10}$");

            String department;
            do {
                department = util.Validation.getString("Input department: ", "All fields are not allowed null and "
                                                     + "the length of the department field must be from 3 to 50 characters");

                if (department.length() == 0 || department.isEmpty() || department.length() < 3 || department.length() > 50) {
                    System.out.println("All fields are not allowed null "
                                     + "and the length of the department field must be from 3 to 50 characters!");
                }
            } while (department.length() < 3 || department.length() > 50);

            String shift = util.Validation.getString("Input shift: ", "All fields are not allowed null!");

            double salary = util.Validation.getADouble("Input salary: ", "All fields are not allowed null "
                                                     + "and must be a number larger than 0", 0);

            Nurse nurse = new Nurse(id, name, age, gender, address, phone, department, shift, salary);
            nurseMap.put(id, nurse);
            System.out.println("Added successfully!");
            
            change = true;
            confirm = util.Validation.getRegexString("Do you want to continue? (Y/N): ", "Just Y(y) or N(n)", "[Y|y|n|N]$");
        } while (confirm.equalsIgnoreCase("Y"));
    }

    public void findNurse() {
        String name = util.Validation.getString("Input name to search: ", "All the fields are not allowed!");
        boolean check = false;
        for (Nurse n : nurseMap.values()) {
            if (n.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(n.toString());
                check = true;
            }
        }
        if (!check) {
            System.out.println("The nurse " + name + " does not exist!");
        }
    }

    public void updateNurse() {
        String id = util.Validation.getRegexString("Input ID(Nxxxx) to update: ", "Wrong format. Ex: N0001,...", "^[N/n]\\d{4}$");
        if (nurseMap.containsKey(id)) {
            System.out.println("Here is the old information of the id you just inputed: " + nurseMap.get(id).getName());
            System.out.println(nurseMap.get(id).toString());

            String newName = util.Validation.updateString("Input new name: ", nurseMap.get(id).getName());
            nurseMap.get(id).setName(newName);

            int newAge = util.Validation.updateAnInteger("Input new age: ", 0, nurseMap.get(id).getAge());
            nurseMap.get(id).setAge(newAge);

            String newGender = util.Validation.updateString("Input new gender: ", "^[Y|y|n|N]$");
            if (newGender.equalsIgnoreCase("Y")) {
                if (nurseMap.get(id).getGender().equalsIgnoreCase("Male")) {
                    nurseMap.get(id).setGender("Female");
                }
                nurseMap.get(id).setGender("Male");
            }

            String newAddress = util.Validation.updateString("Input new address: ", nurseMap.get(id).getAddress());
            nurseMap.get(id).setAddress(newAddress);

            String confirmPhone = util.Validation.getRegexString("Do you want to change phone (Y/N)?:  ", "Yes or No", "^[Y|y|n|N]$");
            if (confirmPhone.equalsIgnoreCase("Y")) {
                String newPhone = util.Validation.getRegexString("Input new phone: ", "Must be a number and all the fields are not allowed null", "^0\\d{9}$");
                nurseMap.get(id).setPhone(newPhone);
            }

            String newDepartment = util.Validation.updateString("Input new department", nurseMap.get(id).getDepartment());
            nurseMap.get(id).setDepartment(newDepartment);

            String newShift = util.Validation.updateString("Input new shift: ", nurseMap.get(id).getShift());
            nurseMap.get(id).setShift(newShift);

            double newSalary = util.Validation.updateADouble("Input new salary: ", 0, nurseMap.get(id).getSalary());
            nurseMap.get(id).setSalary(newSalary);

            System.out.println("Updated successfully!");
            change = true;
        } else {
            System.out.println("The nurse has id = " + id + " does not exist!");
        }
    }

    public void deleteNurse() {
        String id = util.Validation.getRegexString("Input ID(Nxxxx) to deleting: ", "Wrong format. Ex: N0001,...!", "^[N|n]\\d{4}$");
        if (nurseMap.containsKey(id)) {
            String confirm = util.Validation.getRegexString("Are you sure want to delete(y/n): ", "Just y or n!", "^[Y|y|n|N]$");
            if (confirm.equalsIgnoreCase("Y")) {
                boolean check = false;
                for (Patient p : patientMap.values()) {
                    if (p.getNurseAssigned().toLowerCase().contains(id.toLowerCase())) {
                        check = true;
                    }
                }
                if (check == true) {
                    System.out.println("The nurse cannot be deleted because she has a task!");
                } else {
                    nurseMap.remove(id);
                    System.out.println("The nurse's information has been deleted successfully!");
                    change = true;
                }
            } else {
                System.out.println("You canceled fail!");
            }
        } else {
            System.out.println("The nurse does not exist!");
        }
    }

    public void addPatient() {
        String id, confirm, listIDNurse = null;
        do {
            do {
                id = util.Validation.getRegexString("Input ID(Pxxxx): ", "Wrong format. Ex: P0001,...", "^[P|p]\\d{4}$");
                if (patientMap.containsKey(id)) {
                    System.out.println("Duplicated ID. Please input again!");
                }
            } while (patientMap.containsKey(id));

            String name = util.Validation.getString("Input name: ", "All fields are not allowed null!");

            int age = util.Validation.getAnInteger("Input age: ", "Must be a number larger than 0 and all fields are not allowed null!", 0);

            String gender;
            do {
                gender = util.Validation.getString("Input gender(Male/Female): ", "All fields are not allowed null!");
                if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
                    System.out.println("Male or Famale and all fields are not allowed null!");
                }
            } while (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"));

            String address = util.Validation.getString("Input address: ", "All fields are not allowed null!");

            String phone = util.Validation.getRegexString("Input phone: ", "Must be a number and all fields are not allowed null!", "^\\d{10}$");

            String diagnosis = util.Validation.getString("Input diagnosis: ", "All fields are not allowed null!");

            String admissionDate = util.Validation.getDate("Input admissionDate(dd/MM/yyyy): ", "All fields are not allowed null!");

            String dischargeDate = util.Validation.getDate("Input dischargeDate(dd/MM/yyyy): ", "All fields are not allowed null!");

            System.out.println("The following is a list of nurses: ");
            for (Nurse x : nurseMap.values()) {
                System.out.println(x.toString());
            }

            if (nurseMap.size() < 2) {
                System.out.println("The hospital has less than 2 nurses.");
            } else {
                boolean checkIDNurse_1;
                String idNurse_1, idNurse_2;
                do {
                    checkIDNurse_1 = false;
                    idNurse_1 = util.Validation.getRegexString("Input the id of the first doctor(Nxxxx): ", "Wrong format. Ex: N0001,...", "^[N|n]\\d{4}$");
                    checkIDNurse_1 = checkIDNurse(idNurse_1);
                    if (checkIDNurse_1 == false) {
                        System.out.println("Input again!");
                    }
                } while (checkIDNurse_1 == false);

                boolean checkIDNurse_2;
                do {
                    checkIDNurse_2 = false;
                    idNurse_2 = util.Validation.getRegexString("Input the id of the second doctor(Nxxxx): ", "Wrong format. Ex: N0001,...", "^[N|n]\\d{4}$");
                    checkIDNurse_2 = checkIDNurse(idNurse_2);
                    if (checkIDNurse_2 == false) {
                        System.out.println("Input again!");
                    }

                    if (idNurse_1.equalsIgnoreCase(idNurse_2)) {
                        System.out.println("Duplicated ID. Input again!");
                    }
                } while (checkIDNurse_2 == false || idNurse_1.equalsIgnoreCase(idNurse_2));
                listIDNurse = idNurse_1 + "," + idNurse_2;
                patientMap.put(id, new Patient(id, name, age, gender, address, phone, diagnosis, admissionDate, dischargeDate, listIDNurse));
                System.out.println("Add patient successfully!");
                change = true;
            }
            confirm = util.Validation.getRegexString("Do you want to add continues (Y/N): ", "Just Y(y) or N(n)", "^[Y|y|n|N]$");
        } while (confirm.equalsIgnoreCase("Y"));
    }

    public boolean checkIDNurse(String id) {
        boolean check = false;
        for (Nurse x : nurseMap.values()) {
            if (x.getId().equalsIgnoreCase(id)) {
                check = true;
            }
        }
        if (check == true) {
            int count = 0;
            for (Patient x : patientMap.values()) {
                if (x.getNurseAssigned().toLowerCase().contains(id.toLowerCase())) {
                    count++;
                }
            }
            if (count >= 2) {
                System.out.println("1 doctor can only take care of up to 2 patients!");
                check = false;
            }
        }
        return check;
    }

    public void displayPatients() throws ParseException {
        if (patientMap.isEmpty()) {
            System.out.println("List patient is empty. Nothing to print!");
        } else {
            String dateStart = util.Validation.getDate("Enter date start(dd/MM/yyyy): ", "Wrong. Input again!");
            String dateEnd;
            do {
                dateEnd = util.Validation.getDate("Enter date end(dd/MM/yyyy): ", "Wrong. Input put again!");
                if (getTime(dateEnd) <= getTime(dateStart)) {
                    System.out.println("The end date must be greater than the start date!");
                }
            } while (getTime(dateEnd) <= getTime(dateStart));
            
            System.out.println("LIST OF PATIENTS");
            System.out.println("Start date: " + dateStart);
            System.out.println("End date: " + dateEnd);
            
            int count = 1;
            
            System.out.println("+-----+----------+---------------+---------------+---------------+---------------+");
            System.out.println("| No. |Patient ID| Admission Date|   Full Name   |     Phone     |   Diagnosis   |");
            System.out.println("+-----+----------+---------------+---------------+---------------+---------------+");
            for (Patient x : patientMap.values()) {
                if (getTime(x.getAdmissionDate()) >= getTime(dateStart) && getTime(x.getAdmissionDate()) <= getTime(dateEnd)) {
                    System.out.printf("|%-5d|%-10s|%-15s|%-15s|%-15s|%-15s|\n", 
                                        count++, x.getId(), x.getAdmissionDate(), x.getName(), x.getPhone(), x.getDiagnosis());
                }
            }
            System.out.println("+-----+----------+---------------+---------------+---------------+---------------+");
        }
    }

    public long getTime(String date) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat("dd/mm/yyyy");
        return formater.parse(date).getTime(); 
    }

    public void sortPatient() {
        if (patientMap.isEmpty()) {
            System.out.println("List patient is empty. Nothing to print!");
        } else {
            ArrayList<Patient> listSort = new ArrayList();
            for (Patient x : patientMap.values()) {
                listSort.add(x);
            }

            int choiceField = util.Validation.getAnInteger("Input your choice(1.ID 2.Name): ", "Just 1 or 2", 1, 2);
            int choiceType = util.Validation.getAnInteger("Input your choice(1.ASC or 2.DESC): ", "Just 1 or 2", 1, 2);
            System.out.println("LIST OF PATIENTS");
            switch (choiceField) {
                case 1:
                    System.out.println("Sorted by patient's id");
                    if (choiceType == 1) {
                        System.out.println("Sort order: ASC");
                        Collections.sort(listSort, (o1, o2) -> { return o1.getId().compareToIgnoreCase(o2.getId()); });
                    } else {
                        System.out.println("Sort order: DESC");
                        Collections.sort(listSort, (o1, o2) -> { return o2.getId().compareToIgnoreCase(o1.getId()); });
                    }
                    break;

                case 2:
                    System.out.println("Sorted by patient's name");
                    if (choiceType == 1) {
                        System.out.println("Sort order: ASC");
                        Collections.sort(listSort, (o1, o2) -> { return o1.getName().compareToIgnoreCase(o2.getName()); });
                    } else {
                        System.out.println("Sort order: DESC");
                        Collections.sort(listSort, (o1, o2) -> { return o2.getName().compareToIgnoreCase(o1.getName()); });
                    }
                    break;
            }

            int count = 1;

            System.out.println("+-----+----------+---------------+---------------+---------------+---------------+");
            System.out.println("| No. |Patient ID| Admission Date|   Full Name   |     Phone     |   Diagnosis   |");
            System.out.println("+-----+----------+---------------+---------------+---------------+---------------+");
            for (Patient x : listSort) {
                System.out.printf("|%-5d|%-10s|%-15s|%-15s|%-15s|%-15s|\n", count++, x.getId(), x.getAdmissionDate(), x.getName(), x.getPhone(), x.getDiagnosis());
            }
            System.out.println("+-----+----------+---------------+---------------+---------------+---------------+");
        }
    }
    
    public void saveData() {
        if (change == true) {
            boolean checkNurse = true, checkPatient = true;
            try { 
                FileWriter fw = new FileWriter("nurses.dat");
                BufferedWriter bw = new BufferedWriter(fw);
                for (Nurse n : nurseMap.values()) {
                    bw.write(n.toString());
                    bw.newLine();
                }
                bw.close();
                fw.close();
            } catch (IOException e) {
                System.out.println("Save data from nurses.dat fail!");
                checkNurse = false;
            }

            try {
                FileWriter fw = new FileWriter("patients.dat");
                BufferedWriter bw = new BufferedWriter(fw);
                for (Patient p : patientMap.values()) {
                    bw.write(p.toString());
                    bw.newLine();
                }
                bw.close();
                fw.close();
            } catch (IOException e) {
                System.out.println("Save data from patients.dat fail!");
                checkPatient = false;
            }

            if (checkNurse == true && checkPatient == true) {
                System.out.println("Save data to file Successfull.");
            } else {
                System.out.println("Save data to file Fail.");
            }
        } else {
            System.out.println("The data does not change.");
        }
    }

    public void loadData() {
        boolean checkNurse = true, checkPatient = true;
        nurseMap.clear();
        patientMap.clear();

        try {
            FileReader fr = new FileReader("nurses.dat");
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String txt[] = line.split(",");
                String id = txt[0].trim();
                String name = txt[1].trim();
                int age = Integer.parseInt(txt[2].trim());
                String gender = txt[3].trim();
                String address = txt[4].trim();
                String phone = txt[5].trim();
                String department = txt[6].trim();
                String shift = txt[7].trim();
                double salary = Double.parseDouble(txt[8].trim());
                nurseMap.put(id, new Nurse(id, name, age, gender, address, phone, department, shift, salary));
            }
            br.close();
            fr.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Read data from nurses.dat fail!");
            checkNurse = false;
        }

        try {
            FileReader fr = new FileReader("patients.dat");
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String txt[] = line.split(",");
                String id = txt[0].trim();
                String name = txt[1].trim();
                int age = Integer.parseInt(txt[2].trim());
                String gender = txt[3].trim();
                String address = txt[4].trim();
                String phone = txt[5].trim();
                String diagnosis = txt[6].trim();
                String admissionDate = txt[7].trim();
                String dischargeDate = txt[8].trim();
                String nurseAssigned = txt[9].trim();
                patientMap.put(id, new Patient(id, name, age, gender, address, phone, diagnosis, admissionDate, dischargeDate, nurseAssigned));
            }
            br.close();
            fr.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Read data from patients.dat fail!");
            checkPatient = false;
        }

        if (checkNurse == true && checkPatient == true) {
            System.out.println("Read data from file Successfull.");
        } else {
            System.out.println("Read data from file Fail");
        }
    }
}
