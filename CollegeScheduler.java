//unit 2 hw
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

class Main {
    // ---PsuedoCode---
    // User enters the information.
    // Add the data to the contructor.
    // Calculate cost, then print it out.
    
    private static College valenciaCollege = new College();
    
    private static String mainMenu(){
        String option;
        System.out.println("Choose from the following options:");
        System.out.println("\t1- Add a new student");
        System.out.println("\t2- Add/Delete a course");
        System.out.println("\t3- Search for a student");
        System.out.println("\t4- Print fee invoice");
        System.out.println("\t5- Print fee invoice sorted by crn");
        System.out.println("\t0- Exit program");
        System.out.print("Enter your selection: ");
        option = new Scanner(System.in).nextLine();
        return option;
    }
    
    // Add/Delete a course.
    private static String subMenu(String name){
        String option;
        System.out.printf("A- Add a new course for %s\n", name);
        System.out.printf("D- Delete a course from %s's schedule\n", name);
        System.out.println("C- Cancel operation");
        System.out.print("Enter your selection: ");
        option = new Scanner(System.in).nextLine();
        return option;
    }
    
    public static void main(String[] args){
        boolean stop = false;
        while(!stop){
            String menu_option = mainMenu();
            switch(Integer.parseInt(menu_option)){
                case 1:
                    while(1==1){
                        System.out.print("Enter the student's id: ");
                        int id = new Scanner(System.in).nextInt();
                        if(valenciaCollege.searchById(id)){
                            System.out.printf("Sorry, %d is already assigned to another student\n", id);
                            continue;
                        }
                        System.out.print("Enter student's name: ");
                        String name = new Scanner(System.in).nextLine();
                        System.out.printf("Enter how many courses %s is taking?\n", name);
                        int num_courses = new Scanner(System.in).nextInt();
                        System.out.printf("Enter the %d course numbers\n", num_courses);
                        
                        ArrayList<Integer> listOfCrns = new ArrayList<Integer>();
                        for(int i = 0; i < num_courses; i++){
                            int course_number = new Scanner(System.in).nextInt();
                            listOfCrns.add(course_number);
                        }
                        
                        System.out.printf("Enter %s's current gpa:\n", name);
                        double gpa = new Scanner(System.in).nextDouble();
                        
                        Student student = new Student(name, id, gpa, listOfCrns);
                        double cost = 120.25;
                        double total_cost = 0;
                        for(int crn : listOfCrns){
                            int credit_hour = 0;
                            credit_hour = student.GetCreditHours(crn);
                            total_cost += credit_hour * cost;
                        }
                        if(gpa >= 3.5 && total_cost > 700){
                            System.out.printf("(%s is eligible for the 25%s discount)\n", name, "%");
                        }else{
                            System.out.printf("(%s is not eligible for the 25%s discount)\n", name, "%");
                        }
                        valenciaCollege.enrollStudent(student);
                        System.out.println("Student added successfully!\n");
                        break;
                    }
                    break;
                case 2:
                    System.out.print("Enter the student's id: ");
                    int id = new Scanner(System.in).nextInt();
                    valenciaCollege.printInvoice(id);
                    // Print out courses
                    String sub_option = subMenu(valenciaCollege.GetStudent(id).GetName());
                    switch(sub_option.toUpperCase()){
                        case "A":
                            while(1==1){
                                System.out.print("Enter course number to add: ");
                                int crn = new Scanner(System.in).nextInt();
                                if(valenciaCollege.addCourse(id, crn)){
                                    System.out.print("Want to display new invoice? Y/N: ");
                                    switch((new Scanner(System.in).nextLine()).toUpperCase()){
                                        case "Y":
                                            valenciaCollege.printInvoice(id);
                                            break;
                                        case "N":
                                            break;
                                    }
                                    break;
                                }else{
                                    System.out.println("Course already exists.");
                                }
                            }
                            break;
                        case "D":
                            while(1==1){
                                System.out.print("Enter course number to delete: ");
                                int crn = new Scanner(System.in).nextInt();
                                if(valenciaCollege.deleteCourse(id, crn)){
                                    System.out.print("Want to display new invoice? Y/N: ");
                                    switch((new Scanner(System.in).nextLine()).toUpperCase()){
                                        case "Y":
                                            valenciaCollege.printInvoice(id);
                                            break;
                                        case "N":
                                            break;
                                    }
                                    break;
                                }else{
                                    System.out.println("Could not find course, please try again.");
                                }
                            }
                            break;
                        case "C":
                            break;
                    }
                    break;
                case 3:
                    System.out.print("Enter the student's id: ");
                    
                    if(valenciaCollege.searchById(new Scanner(System.in).nextInt())){
                        System.out.println("Student found!");
                    }else{
                        System.out.println("Student does not exist.");
                    }
                    
                    break;
                case 4:
                    System.out.print("Enter the student's id: ");
                    valenciaCollege.printInvoice(new Scanner(System.in).nextInt());
                    break;
                case 5:
                    System.out.print("Enter the student's id: ");
                    valenciaCollege.printSortedInvoice(new Scanner(System.in).nextInt());
                    break;
                case 0:
                    stop = true;
                    break;
            }
        }
  }
}

class Student{
    private int student_id;
    private String student_name;
    private double gpa;
    private ArrayList<Integer> listOfCrns;
    
    Student(String student_name, int student_id, double gpa, ArrayList<Integer> listOfCrns){
        this.student_name = student_name;
        this.student_id = student_id;
        this.gpa = gpa;
        this.listOfCrns = listOfCrns;
    }
    
    // Getting the ID
    public int GetId(){
        return student_id;
    }
    
    public ArrayList<Integer> GetCrns(){
        return listOfCrns;
    }
    
    // Setting the ID
    public void SetId(int student_id){
        this.student_id = student_id;
    }
    
    public String GetName(){
        return student_name;
    }
    
    public void SetName(String student_name){
        this.student_name = student_name;
    }
    
    public double GetGPA(){
        return gpa;
    }
    
    public void SetGPA(double gpa){
        this.gpa = gpa;
    }
    
    public String GetPrefix(int crn){
        String course = "";
        switch(crn){
            case 4587:
                course = "MAT 236";
                break;
            case 4599:
                course = "COP 220";
                break;
            case 8997:
                course = "GOL 124";
                break;
            case 9696:
                course = "COP 100";
                break;
            case 4580:
                course = "MAT 136";
                break;
            case 2599:
                course = "COP 260";
                break;
            case 1997:
                course = "CAP 424";
                break;
            case 3696:
                course = "KOL 110";
                break;
        }
        return course;
    }
    
    public int GetCreditHours(int crn){
        int credit_hour = 0;
        switch(crn){
                case 4587:
                    credit_hour = 4;
                    break;
                case 4599:
                    credit_hour = 3;
                    break;
                case 8997:
                    credit_hour = 1;
                    break;
                case 9696:
                    credit_hour = 3;
                    break;
                case 4580:
                    credit_hour = 1;
                    break;
                case 2599:
                    credit_hour = 3;
                    break;
                case 1997:
                    credit_hour = 1;
                    break;
                case 3696:
                    credit_hour = 2;
                    break;
            }
        return credit_hour;
    }
    
    private double calculateTotalPayout(){
        double cost = 120.25;
        double total_cost = 0;
        for(int crn : listOfCrns){
            int credit_hour = 0;
            credit_hour = GetCreditHours(crn);
            total_cost += credit_hour * cost;
        }
        
        return total_cost;
    }
    
    public void printFeeInvoice(boolean sorted){
        System.out.printf("%-15s%-15s%s\n", "CRN", "CR_PREFIX", "CREDIT HOURS");
        
        Double z = 35.00;
        if(sorted){
            ArrayList<Integer> temp_arr = new ArrayList<Integer>(listOfCrns);
            Collections.sort(temp_arr);
            for(int crn : temp_arr){
                int credit_hours = GetCreditHours(crn);
                System.out.printf("%-15s%-15s%-15s$%.2f\n", crn, GetPrefix(crn), credit_hours, credit_hours * 120.25);
            }
        }else{
            for(int crn : listOfCrns){
                int credit_hours = GetCreditHours(crn);
                System.out.printf("%-15s%-15s%-15s$%.2f\n", crn, GetPrefix(crn), credit_hours, credit_hours * 120.25);
            }
        }
        
        System.out.printf("\t\t%-15s\t$%.2f\n", "Health & id fees", z);
        System.out.println("----------------------------------------------------");
        if(calculateTotalPayout() > 700){
            System.out.printf("\t\t\t %-15s$%.2f\n", "", calculateTotalPayout() + z);
            System.out.printf("\t\t\t%-15s-$%.2f\n", "", (calculateTotalPayout() + z) * 0.25);
            System.out.printf("\t\t\t%s\n", "-----------------------");
            System.out.printf("\t\t\t %-15s$%.2f\n", "Total Payments", (calculateTotalPayout() + z) * 0.75);
        }else{
            System.out.printf("\t\t\t %-15s$%.2f\n", "Total Payments", calculateTotalPayout() + z);
        }
    }
}


class College{
    private ArrayList<Student> list = new ArrayList<Student>();
    private int student_id;
    
    public Student GetStudent(int student_id){
        for(Student stu : list){
            if(stu.GetId() == student_id){
                return stu;
            }
        }
        return null;
    }
    
    public boolean searchById(int studentId){
        for(Student stu : list){
            if(stu.GetId() == studentId){
                return true;
            }
        }
        return false;
    }
    
    // This is a different studentId
    public boolean addCourse(int student_id, int crn){
        boolean crn_exists = false;
        for(Student stu : list){
            if(stu.GetId() == student_id){
                for(int i = 0; i < stu.GetCrns().size(); i++){
                    if(stu.GetCrns().get(i) == crn){
                        crn_exists = true;
                    }
                }
        
                if(!crn_exists){
                    stu.GetCrns().add(crn);
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
    
    public boolean deleteCourse(int student_id, int crn){
        for(Student stu : list){
            if(stu.GetId() == student_id){
                for(int i = 0; i < stu.GetCrns().size(); i++){
                    if(stu.GetCrns().get(i) == crn){
                        System.out.printf("[%d/%s] is deleted successfully!\n", crn, stu.GetPrefix(crn));
                        stu.GetCrns().remove(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void printInvoice(int student_id){
        for(Student stu : list){
            if(stu.GetId() == student_id){
                stu.printFeeInvoice(false);
            }
        }
    }
    
    public void printSortedInvoice(int student_id){
        for(Student stu : list){
            if(stu.GetId() == student_id){
                stu.printFeeInvoice(true);
            }
        }
    }
    
    public void enrollStudent(Student student){
        // Create an instanced of class Student.
        list.add(student);
    }
}