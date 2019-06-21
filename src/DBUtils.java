import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBUtils {
	private String className; //驱动名
    private String url; //连接数据库的URL地址
    private String username; //数据库的用户名
    private String password; //数据库的密码
    private Connection conn; //数据库连接对象
    private PreparedStatement statement; //数据库预编译处理对象
    private ResultSet resultSet = null;
    
    //数据库链接
    public DBUtils() {
    	className="com.mysql.jdbc.Driver";
        url="jdbc:mysql://localhost:3306/blog?useSSL=false&characterEncoding=utf8";
        username="fusiyu";
        password="111111";
        conn = null;
        statement = null;
        try{
            Class.forName(className);
            conn = DriverManager.getConnection(url, username, password);
        }catch(Exception e){
            System.out.println("加载数据库驱动程序失败！");
            e.printStackTrace();
        }
    }
    
    //查询操作
    public ResultSet doSelect(String sql) {
    	try {
    		statement = conn.prepareStatement(sql);
        	resultSet = statement.executeQuery();
    	} catch(Exception e){
    		e.printStackTrace();
    	}	
    	return resultSet;
    }
    
    //关闭链接
    public void closeConnection() {
    	try {
			if(resultSet != null)
				resultSet.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		try {
			if(statement != null)
				statement.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
    }
    
    //将查询结果集转化为JSON
    public String resultSetToJson(ResultSet rs) throws SQLException,JSONException  
    {  
       // json数组  
       JSONArray array = new JSONArray();  
        
       // 获取列数  
       ResultSetMetaData metaData = rs.getMetaData();  
       int columnCount = metaData.getColumnCount();  
        
       // 遍历ResultSet中的每条数据  
        while (rs.next()) {  
            JSONObject jsonObj = new JSONObject();  
             
            // 遍历每一列  
            for (int i = 1; i <= columnCount; i++) {  
                String columnName =metaData.getColumnLabel(i);  
                String value = rs.getString(columnName);  
                jsonObj.put(columnName, value);  
            }   
            array.put(jsonObj);   
        }  
        
       return array.toString();  
    }
    
    //增删改操作
    public Boolean excute(String sql) {
    	try {
    		statement = conn.prepareStatement(sql);
    		int result = statement.executeUpdate();
    		if(result>0) {
    			return true;
    		}else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return false;
    }

}
