package payrollDesign;
import java.util.Scanner;
import java.sql.*;

public class Payroll{
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the Employee ID:");
		String payroll_ID = in.nextLine();
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");
			Statement stmt=con.createStatement();
			String query = "select * FROM Employee WHERE ID=" + "'"+payroll_ID+"'";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {

				double m_sal = rs.getDouble("Salary_monthly");
				double h_sal = rs.getDouble("Salary_perhr");
				double chrge = rs.getDouble("Union_charge");
				int emp_type = rs.getInt("Employee_type");
				Payment emp = new EmployeeTypeB(payroll_ID,m_sal,h_sal,chrge);

				System.out.println(emp.get_todays_pay());
			}
			else System.out.println("Not an Employee!");
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
	}
}

interface Payment {
	double get_todays_pay();
	double monthly_charge();
}

class PaymentInfo {
	private double monthlysalary;
	private double hourlysalary;
	private double charge;
	PaymentInfo(double monthlysalary,double hourlysalary,double charge) {
		this.monthlysalary = monthlysalary;
		this.hourlysalary = hourlysalary;
		this.charge = charge;
	}
	protected double cut_monthly_charges() {
		return this.charge;
	}
	protected double getmonthlysalary() {
		return this.monthlysalary;
	}
	protected double gethourlysalary() {
		return this.hourlysalary;
	}
}

class EmployeeTypeA extends PaymentInfo implements Payment{
	private String ID;
	EmployeeTypeA(String ID,double monthlysalary,double hourlysalary,double charge) {
		super(monthlysalary,hourlysalary,charge);
		this.ID = ID;
	}
	public double get_todays_pay() {
		double todays_amount = 0;
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/FKpayroll","root","");

			java.util.Date date=new java.util.Date();
			java.sql.Date sqlDate=new java.sql.Date(date.getTime());

			Statement stmt=con.createStatement();
			String query = "select * FROM in_out_record WHERE ID=" + "'"+ID+
				"' AND Date='" + sqlDate + "'";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				double time_worked = rs.getDouble("Tot_time");
				todays_amount = gethourlysalary()*time_worked;
				if(time_worked > 8) todays_amount  *= 1.5;
			}
			con.close();
		}
		catch(Exception e){ 
			System.out.println(e);
		}
		return todays_amount;
	} 
	public double monthly_charge() {
		return super.cut_monthly_charges();
	}
}

class EmployeeTypeB extends PaymentInfo implements Payment{
	private String ID;
	EmployeeTypeB(String ID,double monthlysalary,double hourlysalary,double charge) {
		super(monthlysalary,hourlysalary,charge);
		this.ID = ID;
	}
	public double get_todays_pay() {
		return getmonthlysalary()/30;
	}
	public double monthly_charge() {
		return super.cut_monthly_charges();
	}
}


				// String temp1 = "select * FROM Employee WHERE ID=" + "'"+ID+"'";
				// ResultSet rs1 = stmt.executeQuery(temp1);
				// if(rs1.next()) {
				// 	double old_amt = rs.getDouble("Amount_to_be_paid");
				// 	double new_amt = old_amt + todays_amount;
				// 	String temp2 = "UPDATE Employee SET Amount_to_be_paid = "+
				// 	 new_amt + " WHERE ID=" + "'"+ID+"'";
				// 	stmt.executeUpdate(temp2);
				// }