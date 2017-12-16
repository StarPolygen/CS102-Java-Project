package action;



import java.sql.SQLException;



import connect.sqlAction;



public class Teacher extends Person{

	public String address;

	public int maxnumOfMonth;

	public int maxtimeOfMonth;

	public int maxunllnum;

	public static String command = "";

	Teacher(String id,String password){

		super(id,password);

	}

	Teacher(String id)throws SQLException {
		
		//此处的参数不应只是id，或者另写一个重载的构造方法，用以将老师实例化，new一个老师对象
		
		command = "SELECT maxnumOfMonth,maxtimeOfMonth,maxunllnum FROM teacher WHERE t_id = '" + id + "';";

		sqlAction.executeSQL(command);

		maxnumOfMonth = sqlAction.rs.getInt("maxnumOfMonth");

		maxtimeOfMonth = sqlAction.rs.getInt("maxtimeOfMonth");

		maxnumOfMonth = sqlAction.rs.getInt("maxunllnum");

	}

	public void setMaxnumOfMonth(int n) throws SQLException {

		command = "UPDATE teacher SET maxnumOfMonth ="+ n +" WHERE t_id = '" + this.id+"';";

		sqlAction.executeSQL(command);

	}

	public void setMaxtimeOfMonth(int n) throws SQLException {

		command = "UPDATE teacher SET MaxtimeOfMonth ="+ n +" WHERE t_id = '" + this.id+"';";

		sqlAction.executeSQL(command);

	}

	public void setMaxunllnum(int n) throws SQLException {

		command = "UPDATE teacher SET maxunllnum ="+ n +" WHERE t_id = '" + this.id+"';";

		sqlAction.executeSQL(command);

	}

	public String getAddress() {

		return address;

	}

	public void setAddress(String address) throws SQLException {

		command = "UPDATE teacher SET address ='"+address+"' WHERE t_id = '" + this.id+"';";

		sqlAction.executeSQL(command);

	}

	public void setName(String name) throws SQLException {

		command = "UPDATE teacher SET t_name ='"+name+"' WHERE t_id = '" + this.id+"';";

		sqlAction.executeSQL(command);

	}

	public  void setPassword(String password) throws SQLException {

		command = "UPDATE teacher SET t_password ='"+ password +"' WHERE t_id = '" + this.id+"';";

		sqlAction.executeSQL(command);

	}

	public void setResult(String r_id) throws SQLException {

		command = "SELECT distance FROM reservation WHERE teacher = '"+this.id+"' AND r_id = "+r_id+";";

		sqlAction.executeSQL(command);

		if (sqlAction.rs.getInt("diatance") <= 0) {

			command = "UPDATE reservation SET wellfinished = false WHERE r_id = "+ r_id +";"; 

			sqlAction.executeSQL(command);

		}

	}public void addValid_Stu(int r_id,String s_id) throws SQLException {
		
		command = "SELECT valid_stu FROM reservation WHERE teacher = '"+this.id+"' AND r_id = "+r_id+";";
		
		sqlAction.executeSQL(command);
		
		String allstu=sqlAction.rs.getString("valid_stu")+" "+s_id+" ";
		
		command="UPDATE teacher SET STU='"+ allstu +"' WHERE t_id = '" + this.id+"';";
		
		sqlAction.executeSQL(command);
		
	}public String CheckNumOfRes() throws SQLException {
		
		int count=0;
		
		String n = null;
		
		command="SELECT valid_stu FROM reservation WHERE teacher = '"+this.id+"';";
		
		sqlAction.executeSQL(command);
		
		while(sqlAction.rs.next()) {
		
		if(!sqlAction.rs.getString("student").equals("***")) {
			
			count++;
			n=n+sqlAction.rs.getString("r_id")+"、";
			}
		}
		return "您本月共有 "+count+" 次预约记录，预约编号分别为： "+n;
		
		
	}public String RevResult() throws SQLException {
		
		command= "SELECT valid_stu FROM reservation WHERE teacher = '"+this.id+"';";
		
		sqlAction.executeSQL(command);
		
		String s="预约编号      教师                 预约学生ID   日期                 起始时间   终止时间    预约地点          预约状态\n";
		
		while(sqlAction.rs.next()) {
		
		if(!sqlAction.rs.getString("student").equals("***")) {
			
			String place=sqlAction.rs.getString(10)+sqlAction.rs.getString(11);
			
			String state;
			
			if(sqlAction.rs.getBoolean("wellfinished")&&sqlAction.rs.getInt("distance")<0) {
				state="已完成";
			}else if(sqlAction.rs.getBoolean("wellfinished")&&sqlAction.rs.getInt("distance")>=0){
				state="未完成";
			}else {
				state="失约";
			}
			
			s+=String.format("%-11d%-13s%-13s%-13s%-9s%-10s%-10s%s\n",sqlAction.rs.getInt(1),sqlAction.rs.getString(2),sqlAction.rs.getString(3),sqlAction.rs.getString(4),sqlAction.rs.getString(7),sqlAction.rs.getString(8),place,state);
			}
		}
		return s;
	}public void addReservation() {
		
		command="INSERT INTO reservation(r_id,teacher,student,r_date,r_year,r_month,begintime,finishtime,length,building,room,distance,)";
				
	}

}
