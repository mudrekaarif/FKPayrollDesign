package payrollDesign;
import java.util.Scanner;

public class AddNewEmployee {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Employee emp = new Employee();
		System.out.println();
		System.out.println("Confirm details, are you sure you want to create this user (y/n):");
		String confirm = in.nextLine();
		if("y".equals(confirm)) {
			System.out.println("Your Flipkart ID is : ");
			System.out.println(emp.GetID());
		}
	}
}
class Employee {
	private String companyID;
	private String firstName;
	private String lastName;
	private String contactNumber;
	private int employeeType = 0;
	private int paymentType = 0;
	Employee() {
		EnterName();
		Details();
		EmployeeAndPaymentType();
		GenerateCID();
	}
	Scanner in = new Scanner(System.in);
	private void EnterName() {
		System.out.println("Enter your First Name :");
		this.firstName = in.nextLine();
		System.out.println("Enter your Last Name :");
		this.lastName = in.nextLine();
		System.out.println();
	}
	private void Details() {
		System.out.println("Enter your Contact Number:");
		this.contactNumber = in.nextLine();
		System.out.println();
	}
	private void EmployeeAndPaymentType() {
		System.out.println("Choose your Employee Type:");
		System.out.println("1. Paid by hour");
		System.out.println("2. Paid a flat salary");
		System.out.println("3. Fixed salary + commision");
		this.employeeType = in.nextInt();
		while(this.employeeType != 1 && this.employeeType != 2 && this.employeeType != 3) {
			System.out.println("Invalid Choice, Choose again.");
			this.employeeType = in.nextInt();
		}

		System.out.println();
		System.out.println("Choose your Method of Payment:");
		System.out.println("1. Paychecks mailed to the postal address");
		System.out.println("2. Paychecks held for pickup by the paymaster");
		System.out.println("3. Paychecks be directly deposited into the bank account");
		this.paymentType = in.nextInt();
		while(this.paymentType != 1 && this.paymentType != 2 && this.paymentType != 3) {
			System.out.println("Invalid Choice, Choose again.");
			this.employeeType = in.nextInt();
		}
		System.out.println();
	}
	private void GenerateCID() {
		this.companyID = this.firstName + "." + this.lastName + "." + this.contactNumber + "@flipkart.com";
	}
	public String GetID() {
		return this.companyID;
	}
	public String GetName() {
		return this.firstName + " " + this.lastName;
	}
}