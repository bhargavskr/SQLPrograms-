package example;
import java.math.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.Datum;
import oracle.jdbc.driver.OracleConnection;

import oracle.sql.REF;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class JdbcBaseBall extends Example {

    String[] st; 
    class Baseball{
		String name;
		String city;
		int year;
		String position;
		String league;
		
	
		public Baseball(String name, String city, BigDecimal year, String position, String league) {
			super();
			int y=year.intValue();
			this.name = name;
			this.city = city;
			this.year = y;
			this.position = position;
			this.league = league;
		
		}
		
		public Object[] getObjArray() {
			return new Object[]{name,city,year,position,league};
		}
	          

	public String toString() {
	    return String.format("%s from %s in %d and  is a %s in %s", name, city, year,position,league);
	}
	    
      
	//	public CustomDatum create(Datum arg0, int arg1) throws SQLException {
	//    Object[] attributes = ((STRUCT)arg0).getAttributes();
	//    return new Baseball((String)attributes[0],(String)attributes[1],(Integer)attributes[2],(String)attributes[3],(String)attributes[4]);
			     
	//	}

	public Datum toDatum(OracleConnection arg0) throws SQLException {
	    StructDescriptor descriptor = new StructDescriptor("BHRAVURI.BASEBALL_OBJ",arg0);
	    return new STRUCT(descriptor, arg0, getObjArray());
	}




	}
	
	
	    

	public void method_1() throws SQLException, ClassNotFoundException {
		super.run();
		Statement stmt = conn.createStatement();
		stmt.execute(" drop table baseball_tab");
		stmt.execute(" drop type baseball_obj");
		boolean res=stmt.execute("create or replace type baseball_obj as object(name varchar2(100),city  varchar2(100),year number(4),position varchar2(10),league varchar2(2))");
		if(!res)
			  System.out.println(" Object Created");
		conn.close();
		
	}

    public void method_2() throws SQLException, ClassNotFoundException {
	super.run();
	Statement stmt = conn.createStatement();
	boolean res=stmt.execute("create table baseball_tab of baseball_obj");
		if(!res)
	    System.out.println(" Object Table Created");
	boolean res1= stmt.execute("alter table baseball_tab add constraint name_year_pk primary key (name,year)");
	if(!res1)
            System.out.println(" Primary Key Created");
	
	boolean res2= stmt.execute("alter table baseball_tab add constraint league_ck_nn check(league is not null)");
	if(!res2)
            System.out.println(" Not Null Constraint on league Created");

	boolean res3= stmt.execute("alter table baseball_tab add constraint name_ck_nn  check(city is not null)");
	if(!res3)
            System.out.println(" Not Null Constraint on Name Created");

	    
	conn.close();

    }
  
    
    public void method_3() throws SQLException, ClassNotFoundException {
	super.run();
	Statement stmt = conn.createStatement();
	boolean res=stmt.execute("insert into baseball_tab (name,city,year,position,league) select name,team,year,position,league from ramorehead.mlb");
	if(!res)
	    System.out.println(" Object Table Populated");
	conn.close();

    }

    

    public Baseball method_4(String league,String year) throws SQLException, ClassNotFoundException {
        super.run();
        Statement stmt = conn.createStatement();
        ResultSet rs=stmt.executeQuery("select ref(w) from baseball_tab w where league='"+league+"' and year="+year);
	rs.next();
     
		REF ref = (REF)rs.getObject(1);
             		STRUCT results = (STRUCT) ref.getValue();
	 	Object[] obj = results.getAttributes();
		Baseball b=new Baseball((String)obj[0],(String)obj[1],(BigDecimal)obj[2],(String)obj[3],(String)obj[4]);
	       
	conn.close();
	return b;
		
    }

    public void method_5()throws SQLException,ClassNotFoundException 
    {
	Baseball c;
	c=this.method_4("NL","1952"); 
	System.out.println(c.toString());
    }
    public void method_6(int n) throws SQLException, ClassNotFoundException {
        super.run();
        Statement stmt = conn.createStatement();
        ResultSet rs=stmt.executeQuery("select city from (select city,count(*) from baseball_tab group by city order by 2 desc) where rownum<="+n);
       
       
	 st=new String[n];
	    int i=0;   
	   while(rs.next())
	    {    
       
		st[i]=rs.getString(1);
		i++;
	    }	
        conn.close();
       
 
    }

	public void method_7()throws SQLException,ClassNotFoundException
	    {
		
		this.method_6(10);
		System.out.println(" Top 10 cities with Highest number of MVP's ");

	        for(int i=0;i<this.st.length;i++)
			{
			    System.out.println(this.st[i]);
			    this.st[i]=null;
			}

 
	    }

    



	public static void main(String[] args) throws SQLException, ClassNotFoundException {
	        
	    JdbcBaseBall a=new JdbcBaseBall();
	     a.method_1();
	     a.method_2();
	     a.method_3();
	     a.method_5();
	     a.method_7(); 
	}

}
