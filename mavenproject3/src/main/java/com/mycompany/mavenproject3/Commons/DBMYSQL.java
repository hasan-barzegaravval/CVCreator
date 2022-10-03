/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.Commons;
/**
 * The class for Database connection and data exchange.
 * This class contains required database connection and data exchange methods. It contains:
 * <ul>
 * <li> database connection methods
 * <li> database Table and Schema creators
 * <li> database prepared statements for insert and update
 * <li> database data reader (Selector)
 * <li> insert unique and insert single for certain insert operations
 * <li> close,commit and getter to access the database connection for any further development as required.
 * </ul>
 * loader functions are written to work with separate tables. It gives modularity and flexibility in db operation. 
 * The database connection is through username password connection. In addition database connection is set 
 * to send and run multiple commands. The Connection and functions are tested against latest MYSQL database. 
 * The update strategy applied here is not optimal. If you are willing to use on a server or use a database on the server with 
 * multiple access and users, consider a more optimized update strategy. You may add change listeners to data and prepare the
 * update statement based on the changed occur. 
 * @author Hasan Barzegaravval
 * @version 1.0
 * @since   1.0
 * @see JDBC
 */
import com.mycompany.mavenproject3.Definitions.COMPANY;
import com.mycompany.mavenproject3.Definitions.EDUCATION;
import com.mycompany.mavenproject3.Definitions.HEADER;
import com.mycompany.mavenproject3.Definitions.INFO;
import com.mycompany.mavenproject3.Definitions.LANGUAGE;
import com.mycompany.mavenproject3.Definitions.PROFILE;
import com.mycompany.mavenproject3.Definitions.PROJECT;
import com.mycompany.mavenproject3.Definitions.PUBLICATION;
import com.mycompany.mavenproject3.Definitions.REFERENCE;
import com.mycompany.mavenproject3.Definitions.SKILL;
import com.mycompany.mavenproject3.Definitions.WORKEXPERIENCE;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DBMYSQL {
    private String host;
    private String port;
    private String username;
    private String pass;
    private String status="Not Connected";    
    private Connection conn;
    private String DBname;
    private HashMap<String,PreparedStatement> updates;
    private HashMap<String,PreparedStatement> inserts;
    /**
     * Constructor for Database operator which creates an empty instance. 
     */
    public DBMYSQL(){
        updates=new HashMap();
        inserts=new HashMap();  
    }
    /**
     * Database operator constructor. It creates new Database operator instance and
     * connect to the Database. The method accepts four parameters.
     * @param host This is host address for database
     * @param port The port on which database is listening
     * @param username The username to access the database
     * @param pass The password for the database connection
     * @throws SQLException This method throws SQL Exception while connection issues encountered. 
     */
    public DBMYSQL(String host,String port,String username,String pass) throws SQLException{
        this.host=host;
        this.port=port;
        this.username=username;
        this.pass=pass;      
        conn=DriverManager.getConnection(connectionstring());
        conn.setAutoCommit(false);
        this.status="Connected";
        PreparedStatements();        
    }
    /**
     * DB connector method. 
     * @param host This is host address for database
     * @param port The port on which database is listening
     * @param username The username to access the database
     * @param pass The password for the database connection
     * @throws SQLException This method throws SQL Exception while connection issues encountered. 
     */
    public void connect(String host,String port,String username,String pass) throws SQLException{
        this.host=host;
        this.port=port;
        this.username=username;
        this.pass=pass;
        conn=DriverManager.getConnection(connectionstring());
        this.status="Connected";
        PreparedStatements();
        conn.setAutoCommit(false);
    }
    /**
     * This method create a connection string. 
     * The connection string follows the jdbc mysql connection pattern.
     * The string is set as allowMultiQueries=true which allows sending multiple 
     * queries at once. Check your database if any issue arise from this setting.
     * @return a connection string  
     */
    private String connectionstring(){
        return "jdbc:mysql://"+host+":"+port+"?user="+username+"&password="+pass+"&allowMultiQueries=true";
    }
    /**
     * getter for connection status.
     * It might be useful to add a listener to DB status change for interrupts.
     * to get latest updated status as it might change during operation.
     * @return status of connection. Returns Not Connected if connection not established and Connected if
     * connection is established successfully. 
     */
    public String getstatus(){
        return this.status;
    }
    /**
     * Close a database connection. 
     * @throws SQLException on connection error or closing operation failure.
     */
    public void close() throws SQLException{
        if (this.conn!=null) this.conn.close();
        this.status="Not Connected";  
    }
    /**
     * This method returns a list of databases (Schema) available.
     * The return is in string list form.
     * @return List of strings which are databases(Schema) names
     * @throws SQLException on any SQL operation issues it throw exception.
     */
    public List<String> DBList() throws SQLException{
        List<String> dblist=new ArrayList();
        String SQC="SHOW DATABASES";
        ResultSet rs=conn.createStatement().executeQuery(SQC);
        //conn.commit();
        while(rs.next()) dblist.add(rs.getString("Database"));
        return dblist;
    }
    /**
     * This method returns list of tables available in an specific Database.
     * @param dbName Name of the database
     * @return List of tables in the database
     * @throws SQLException on any SQL operation issue which occurs during operation.
     */
    public List<String> TableList(String dbName) throws SQLException{
        List<String> tblist=new ArrayList();
        String SQC="SHOW TABLES FROM "+dbName;
        ResultSet rs=conn.createStatement().executeQuery(SQC);
        while(rs.next()) tblist.add(rs.getString("Tables_in_"+dbName));
        return tblist;
    }
    /**
     * method to retrieve list of projects (Stored CV names in a database).
     * @param dbName name of the database
     * @return List of projects (CVs stored) names. 
     * @throws SQLException on on any SQL operation issue which occurs during operation.
     */
    public List<String> ProjectList(String dbName) throws SQLException{
        List<String> Projects=new ArrayList();
        ResultSet rs=conn.createStatement().executeQuery("Select Name From "+dbName+".cvs");
        while(rs.next()) Projects.add(rs.getString("Name"));
        return Projects;
    }
    /**
     * This method create a new database for the project including all required tables and procedures.
     * the SQL commands for this is written in a separated file called SQL in project resource folder.
     * please see the SQL file in resource folder of the project. 
     * @param Name name of the new database to create.
     * @throws SQLException on any SQL operation issue which occurs during operation.
     */
    public void CreateRDB(String Name) throws SQLException{
        String GeneralStatement="CREATE DATABASE IF NOT EXISTS "+Name+";USE "+Name+";";        
        String jstring;
        try{
            String s=FINALS.RESOURCEFOLDER+FINALS.SEP+"SQLFiles"+FINALS.SEP+"SQLInit.sql";
            jstring=new String(Files.readAllBytes(Paths.get(s)),StandardCharsets.UTF_8);
            }catch(IOException ex){
                jstring="";
                AlertException.ALERT("Cteating new Database is failed.The SQL file could not be opened.","SQL file opening issue.","Error");
        }      
        conn.createStatement().execute(GeneralStatement+jstring); conn.commit();
        DBname=Name;        
    }
    /**
     * This method insert a single entry and return its inserted id.
     * @param statement a SQL insert statement.
     * @return The id of inserted row if operation is successful otherwise returns -1.
     * @throws SQLException on any SQL operation issue which occurs during operation.
     */
    public int insertSingle(String statement)throws SQLException{
        PreparedStatement st=conn.prepareStatement(statement,Statement.RETURN_GENERATED_KEYS);
        st.executeUpdate();conn.commit();
        try(ResultSet sr=st.getGeneratedKeys()){
            if (sr.next()){
                return (int) sr.getLong(1);
            }else{
                throw new AlertException("Fail to insert new row or retrieve the id of inserted row.","SQL insert Error","Error");
            }
        }catch(AlertException aex){
            return -1;
        }
    }
    /**
     * insert a single row to a given table with array of data as object[].
     * This method accepts a table name and list of inputs and calls a prepared statement
     * stored in inserts HashMap and set the parameters by input Object array 
     * @param Table name of the table (data) corresponding to the stored statement pattern in inserts.
     * @param li    Array of objects that will be assigned to the prepared statement required parameters. 
     * @return      The inserted row id if operation success otherwise returns -1.
     * @throws SQLException on any SQL operation issue which occurs during operation.
     */
    public int insertSingle(String Table,Object[] li)throws SQLException{
        PreparedStatement st=inserts.get(Table);        
        for (int i=0;i<li.length;i++) st.setObject(i+1, li[i]);        
        st.executeUpdate();
        conn.commit();
        try(ResultSet sr=st.getGeneratedKeys()){
            if (sr.next()){
                return (int) sr.getLong(1);
            }else{
                throw new AlertException("Fail to insert new row or retrieve the id of inserted row.","SQL insert Error","Error");                
            }
        }catch(AlertException aex){
            return -1;
        }
        
    }
    /**
     * This method insert a single entry into a table if the entry is unique otherwise
     * it returns the id of the similar entry on the table.
     * The method calls a stored procedure on the database which checks the uniqueness. 
     * @param Value  The value to be inserted.
     * @param Tbname The name of the table.
     * @param Column The name of the column of the table.
     * @return  id of inserted row or the id of the entry with similar value.
     * @throws SQLException on any SQL operation issue which occurs during operation.
     */
    public int insertSingleUnique(String Value,String Tbname,String Column)throws SQLException{
        
        CallableStatement st=conn.prepareCall("CALL "+DBname+".INSERTUNIQUE(?,?,?,?)");
        st.registerOutParameter(4, java.sql.Types.INTEGER);  
        st.setString(1,Value);
        st.setString(2,Tbname);
        st.setString(3,Column);        
        st.executeUpdate();
        conn.commit();
        return st.getInt(4);
    }
    /**
     * This method insert list of values into the specific table and column and check for each value
     * uniqueness. 
     * The method calls a stored procedure on the database which checks the uniqueness of each value and returns
     * a list of ids of inserted or similar entries.
     * @param Values    list of string values to be inserted on the table
     * @param Tbname    name of the table
     * @param Column    name of the column
     * @return      list of ids which are either inserted new rows or rows with similar ids.
     * @throws SQLException on any SQL operation issue which occurs during operation.
     */
    public List<Integer> insertList(List<String> Values,String Tbname,String Column)throws SQLException{
        CallableStatement st=conn.prepareCall("CALL "+DBname+".INSERTUNIQUE(?,?,?,?)");
        st.registerOutParameter(4, java.sql.Types.INTEGER);  
        st.setString(2,Tbname);
        st.setString(3,Column);
        List<Integer> IDS=new ArrayList();       
        Values.forEach((e)->{try {
            st.setString(1,e);
            st.executeUpdate();IDS.add(st.getInt(4));            
            } catch (SQLException ex) {
                Logger.getLogger(DBMYSQL.class.getName()).log(Level.SEVERE, null, ex);
                AlertException.ALERT("Error",ex);

            }
        });        
        conn.commit();
        return IDS;
    }
    /**
     * A method to execute a single statement update without prepared statements.
     * statement is a string of SQL command.
     * @param st SQL update query. 
     * @throws SQLException on any SQL operation issue which occurs during operation. 
     */
    public void SingleUpdate(String st) throws SQLException{
        conn.createStatement().execute(st);conn.commit();
    }
    /** 
     * This method prepares statements for update and insert data into tables and stores them 
     * in a HashMap with keys of the data classes names.
     * @throws SQLException on any SQL operation issue which occurs during operation. 
     */
    private void PreparedStatements() throws SQLException{        
        updates.put("COMPANY",conn.prepareStatement("UPDATE companies SET COmpany=?,Link=?,StartDate=?,EndDate=? WHERE id=?;"));
        updates.put("RESPONSIBILITES",conn.prepareStatement("UPDATE responsibilities SET Responsibilities=? WHERE id=?;"));
        updates.put("ACHIEVEMENTS",conn.prepareStatement("UPDATE achievements SET Achievements=? WHERE id=?;"));
        updates.put("EDUCATION", conn.prepareStatement("UPDATE educations SET Degree=?,Institue=?,Link=?,StartDate=?,EndDate=?,Thesis=?,CGPA=?,Comment=? WHERE id=?;"));      
        updates.put("HEADER",conn.prepareStatement("UPDATE headers SET Name=?,Discription=? WHERE id=?;"));
        updates.put("INFO",conn.prepareStatement("UPDATE infos SET Name=?,Link=?,Value=?,icon=? WHERE id=?;"));
        updates.put("LANGUAGE", conn.prepareStatement("UPDATE language SET Language=?,Fluency=?,Score=?,Test=?,Comment=? WHERE id=?;"));
        updates.put("PROFILE",conn.prepareStatement("UPDATE profiles SET Age=?,Photo=?,MaterialStatus=?,Profile=?,Comment=? WHERE id=?;"));         
        updates.put("PROJECT", conn.prepareStatement("UPDATE projects SET Title=?,Link=?,Comment=? WHERE id=?;"));
        updates.put("REFERENCE",conn.prepareStatement("UPDATE reference SET Name=?, Position=?, Company=?, Phone=?, Email=?, Comment=? WHERE id=?;"));
        updates.put("SKILL", conn.prepareStatement("UPDATE skills SET Skill=?,Rate=? WHERE id=?;"));
        updates.put("WORKEXPERIENCE",conn.prepareStatement("UPDATE positions SET Position=? WHERE id=?;"));        
        updates.put("PUBLICATION", conn.prepareStatement("UPDATE publications SET _Type=?,Title1=?,Title2=?,Authors=?,Journal=?,Volume=?,_IS=?,SP=?,EP=?,SN=?,Year=?,PB=? WHERE id=?;"));
        updates.put("CV",conn.prepareStatement("UPDATE cvs SET Name=?,Header=?,Info=?,Profile=?,Educations=?,WorkExperiences=?,Languages=?,Skills=?,Reference=?,Publications=? WHERE id=?;"));
        inserts.put("HEADER",conn.prepareStatement("INSERT INTO headers(Name,Discription) VALUES(?,?);",Statement.RETURN_GENERATED_KEYS));
        inserts.put("INFO",conn.prepareStatement("INSERT INTO infos(Name,Link,Value,icon) VALUES(?,?,?,?);",Statement.RETURN_GENERATED_KEYS));
        inserts.put("COMPANY", conn.prepareStatement("INSERT INTO companies(Company,Link,StartDate,EndDate) VALUES(?,?,?,?);",Statement.RETURN_GENERATED_KEYS));        
        inserts.put("EDUCATION", conn.prepareStatement("INSERT INTO educations(Degree,Institue,Link,StartDate,EndDate,Thesis,CGPA,Comment) VALUES(?,?,?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS));        
        inserts.put("LANGUAGE", conn.prepareStatement("INSERT INTO language(Language,Fluency,Score,Test,Comment) VALUES(?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS));
        inserts.put("PROFILE",conn.prepareStatement("INSERT INTO profiles(Age,Photo,MaterialStatus,Profile,Comment) VALUES(?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS));
        inserts.put("PROJECT", conn.prepareStatement("INSERT INTO projects(Title,Link,Comment) VALUES(?,?,?);",Statement.RETURN_GENERATED_KEYS));
        inserts.put("REFERENCE",conn.prepareStatement("INSERT INTO reference(Name,Position,Company,Phone,Email,Comment) VALUES(?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS));
        inserts.put("SKILL",conn.prepareStatement("INSERT INTO Skills(Skill,Rate) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS));        
        inserts.put("PUBLICATION",conn.prepareStatement("INSERT INTO publications(_Type,Title1,Title2,Authors,Journal,Volume,_IS,SP,EP,SN,Year,PB) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS));
        inserts.put("CV",conn.prepareStatement("INSERT INTO cvs(Name,Header,Info,Profile,Educations,WorkExperiences,Languages,Skills,Reference,Publications) VALUES(?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS));
    }
    /**
     * execute the commit command on the private connection object.
     * @throws SQLException on any SQL operation issue which occurs during operation. 
     */
    public void DBCCommit() throws SQLException{
        conn.commit();
    }
    /**
     * To get the name of the currently used database.
     * @return The name of the currently used database.
     */
    public String getDBname(){
        return DBname;
    }
    /**
     * set the name of the currently used database 
     * @param DBname name of the currently used database
     * @throws SQLException on any SQL operation issue which occurs during operation. 
     */
    public void setDBname(String DBname) throws SQLException{
        conn.createStatement().execute("Use "+DBname);
        this.DBname=DBname;
        
    }
    /**
     * This method load the headers data from the hear table.
     * The method load all data and store them with their id as key in a map.
     * @return a HashMap of HEADER objects and ids as keys.
     * @throws SQLException on any SQL operation issue which occurs during operation. 
     */
    public HashMap<Integer,HEADER> LoadHeaders() throws SQLException{
        HashMap<Integer,HEADER> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM headers");
        //conn.commit();
        while(rs.next()){            
            
            data.put(rs.getInt("id"),new HEADER(rs.getInt("id"),rs.getString("Name"),rs.getString("Discription")));
        }
        return data;
    }
    /**
     * This method load the info data from the infos table.
     * The method load all data and store them with their id as key in a map.
     * @return a HashMap of INFO objects and ids as keys.
     * @throws SQLException on any SQL operation issue which occurs during operation. 
     */
    public HashMap<Integer,INFO> LoadInfos() throws SQLException{
        HashMap<Integer,INFO> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM infos");
        //conn.commit();
        while(rs.next()){            
            
            data.put(rs.getInt("id"),new INFO(rs.getInt("id"),rs.getString("Name"),rs.getString("Link"),rs.getString("Value"),rs.getString("icon")));
        }
        return data;
    }    
    public HashMap<Integer,WORKEXPERIENCE> LoadWorkExperiences() throws SQLException{
        HashMap<Integer,WORKEXPERIENCE> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM positions");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),new WORKEXPERIENCE(rs.getInt("id"),rs.getString("Position")));
        }
        return data;
    }
    public HashMap<Integer,EDUCATION> LoadEducations() throws SQLException{
        HashMap<Integer,EDUCATION> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM educations");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),new EDUCATION(rs.getInt("id"),rs.getString("Degree")
            ,rs.getString("Institue")
            ,rs.getString("Link")
            ,rs.getString("StartDate")
            ,rs.getString("EndDate")
            ,rs.getString("Thesis")
            ,rs.getString("CGPA")
            ,rs.getString("Comment")));
        }
        return data;                      
    }
    public HashMap<Integer,COMPANY> loadCompanies() throws SQLException{
        HashMap<Integer,COMPANY> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM companies");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),new COMPANY(rs.getInt("id"),rs.getString("Company")
            ,rs.getString("Link")
            ,rs.getString("StartDate")
            ,rs.getString("EndDate")));
        }
        return data;
    }
    public HashMap<Integer,LANGUAGE> loadLanguages() throws SQLException{
        HashMap<Integer,LANGUAGE> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM language");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),new LANGUAGE(rs.getInt("id"),rs.getString("Language")
            ,rs.getString("Fluency")
            ,rs.getString("SCore")
            ,rs.getString("Test")
            ,rs.getString("Comment")));
        }
        return data;
    }
    public HashMap<Integer,PROFILE> loadPofiles() throws SQLException{
        HashMap<Integer,PROFILE> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM profiles");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),new PROFILE(rs.getInt("id"),rs.getString("Age")
            ,rs.getString("Photo")
            ,rs.getString("MaterialStatus")
            ,rs.getString("Profile")
            ,rs.getString("Comment"),""));
        }
        return data;
        
    }
    public HashMap<Integer,PROJECT> loadProjects() throws SQLException{
        HashMap<Integer,PROJECT> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM projects");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),new PROJECT(rs.getInt("id"),rs.getString("Title")
            ,rs.getString("Link")
            ,rs.getString("Comment")));
        }
        return data;
    }
    public HashMap<Integer,REFERENCE> loadReferences() throws SQLException{
        HashMap<Integer,REFERENCE> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM reference");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),new REFERENCE(rs.getInt("id"),rs.getString("Name")
            ,rs.getString("Position")
            ,rs.getString("Company")
            ,rs.getString("Phone")
            ,rs.getString("Email")
            ,rs.getString("Comment")));
        }
        return data;
    }
    public HashMap<Integer,SKILL> loadSkills() throws SQLException{
        HashMap<Integer,SKILL> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM skills");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),new SKILL(rs.getInt("id"),rs.getString("Skill")
            ,rs.getInt("Rate")));
        }
        return data;
    }
    public HashMap<Integer,String> loadResp() throws SQLException{
        HashMap<Integer,String> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM responsibilities");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),rs.getString("Responsibilities"));
        }
        return data;
    }
    public HashMap<Integer,String> loadAchi() throws SQLException{
        HashMap<Integer,String> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM achievements");
        //conn.commit();
        while(rs.next()){        
            data.put(rs.getInt("id"),rs.getString("Achievements"));
        }
    return data;
    }
    public HashMap<Integer,PUBLICATION> loadPublication() throws SQLException{
        HashMap<Integer,PUBLICATION> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM publications");
        while(rs.next()){
            data.put(rs.getInt("id"),new PUBLICATION(rs.getInt("id"),rs.getString("_Type"),
            rs.getString("Title1"),
            rs.getString("Title2"),
            rs.getString("Authors"),
            rs.getString("Journal"),
            rs.getString("Volume"),
            rs.getString("_IS"),
            rs.getString("SP"),
            rs.getString("EP"),
            rs.getString("SN"),
            rs.getString("Year"),
            rs.getString("PB")));
        }
        return data;
    }
    /**
     * This method load the cvs(projects) stored from the cvs table.
     * The method load all data and store them with their id as key in a map.
     * the retrieved Objects are later used to map to a cv as specified by cv name.
     * @return a HashMap of Object arrays objects and ids as keys.
     * @throws SQLException on any SQL operation issue which occurs during operation. 
     */
    public HashMap<Integer,Object[]> loadCV() throws SQLException{
        HashMap<Integer,Object[]> data=new HashMap();
        ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM cvs");
        //conn.commit();
        while(rs.next()){        
            Object[] A=new Object[10];
            A[0]=rs.getString("Name");
            A[1]=(Integer)rs.getInt("header");
            A[2]=rs.getString("Info");
            A[3]=(Integer)rs.getInt("profile");
            A[4]=rs.getString("Educations");
            A[5]=rs.getString("WorkExperiences");
            A[6]=rs.getString("Languages");
            A[7]=rs.getString("Skills");
            A[8]=rs.getString("Reference");
            A[9]=rs.getString("Publications");
            data.put(rs.getInt("id"),A);
        }
        return data;
    }
    /**
     * get the updates statements
     * @return map of updates prepared statements.
     */
    public HashMap<String,PreparedStatement> getupdates(){return updates;}
    /**
     * get the inserts statements
     * @return map of updates prepared statements.
     */
    public HashMap<String,PreparedStatement> getinserts(){return inserts;}
    
    
}

 