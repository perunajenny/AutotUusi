package model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Auto;

public class Dao {
	
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Autot.sqlite";
	
	private Connection yhdista(){
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); //Eclipsessa
    	//path += "/webapps/"; //Tuotannossa. Laita tietokanta webapps-kansioon
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<Auto> listaaKaikki(){
		ArrayList<Auto> autot = new ArrayList<Auto>();
		sql = "SELECT * FROM autot";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					//con.close();					
					while(rs.next()){
						Auto auto = new Auto();
						auto.setRekno(rs.getString(1));
						auto.setMerkki(rs.getString(2));
						auto.setMalli(rs.getString(3));	
						auto.setVuosi(rs.getInt(4));	
						autot.add(auto);
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return autot;
	}

}
