/**
 * author: joseph gibson
 * class: csci1302
 * file: PAssign09.java
 * date created: 11.23.24
 * date last modified: 11.23.24
 */

import java.io.*;

public class PAssign09 {
    public static void main(String[] args) {
        try (
                DataInputStream input = new DataInputStream(new FileInputStream("src/employeeData.dat"));
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/employeeDataProcessed.dat"))
        ) {
            while (true) {
                try {
                    // Read data from the binary file
                    String employeeID = input.readUTF();
                    double salary = input.readDouble();
                    double serviceYears = input.readDouble();

                    // Create Employee instance and process raise
                    Employee employee = new Employee(employeeID, salary, serviceYears);
                    double raisePercentage = employee.calculateRaise();
                    employee.processRaise(raisePercentage);

                    // Write processed Employee object to the output file
                    oos.writeObject(employee);

                    // Print processed Employee information to console
                    System.out.printf("%s", employee.toString());

                    // Error handling
                } catch (EOFException ex) {
                    System.out.println("End of File Reached");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error, File Not Found");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

    class Employee implements Serializable {
        // Implementation of data members
        private String name;
        private double salary;
        private double serviceYears;

        // Default constructor
        public Employee() {
        }

        // Convenience constructor
        public Employee(String name, double salary, double serviceYears) {
            this.name = name;
            this.salary = salary;
            this.serviceYears = serviceYears;
        }

        // Accessors and Mutators for respective data members
        public String getName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        public double getServiceYears() {
            return serviceYears;
        }

        // Method to calculate raise percentage
        public double calculateRaise() {
            if (salary >= 0.00 && salary <= 30000.00) {
                if (serviceYears <= 2.00) {
                    return 3.00;
                } else {
                    return 2.50;
                }
            } else if (salary > 30000.00 && salary <= 60000.00) {
                if (serviceYears <= 5.00) {
                    return 2.25;
                } else {
                    return 2.00;
                }
            } else if (salary > 60000.00 && salary <= 80000.00) {
                if (serviceYears <= 5.00) {
                    return 1.75;
                } else {
                    return 1.50;
                }
            } else {
                if (serviceYears <= 5.00) {
                    return 1.25;
                } else {
                    return 1.00;
                }
            }
        }

        // Method to process raise
        public void processRaise(double raisePercentage) {
            if (raisePercentage > 0) {
                salary *= (1 + raisePercentage / 100);
            }
        }

        // Custom class toString method
        @Override
        public String toString() {
            return String.format("Name: %s%nSalary: $%,.2f%nYears of Service: %.1f%n", name, salary, serviceYears);
        }
    }
