package obj;

import java.io.Serializable;

public class Nurse extends Person implements Serializable {
    private String department;
    private String shift;
    private double salary;

    public Nurse() {
        
    }

    public Nurse(String id, String name, int age, String gender, String address, String phone, String department, String shift, double salary) {
        super(id, name, age, gender, address, phone);
        this.department = department;
        this.shift = shift;
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.getId() + ", " + super.getName() + ", " + super.getAge() + ", " + super.getGender() + ", " + super.getAddress() + ", "
                + super.getPhone() + ", " + department + ", " + shift + ", " + salary;
    }

    public void showInfor() {
        System.out.printf("|%-5s|%-20s|%-5d| %-10s|   %-15s|  %-15s|     %-15s|  %-10s|%-10.1f|\n",
                              id, name, age, gender, address, phone, department, shift, salary);
    }
}
