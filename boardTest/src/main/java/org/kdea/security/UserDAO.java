package org.kdea.security;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
 
import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.*;
 
@Component
public class UserDAO 
{
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
     
    @Autowired
    private DataSource dataSource;
     
    private Connection getConn()
    {
       try{
         conn = dataSource.getConnection();
         return conn;
       }catch(Exception e){
           e.printStackTrace();
           if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException sqle){
                    sqle.printStackTrace();
                }
           }
       }
       return null;
    }
     
    private void closeAll() {
        try{
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
            if(conn!=null) conn.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }
 
     
    public UserDetails getUserDetails(String userId){
        conn  = getConn();
        String sql = "SELECT id,pwd,authority FROM userinfo WHERE id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                
                String id = rs.getString("ID");
                String pwd = rs.getString("PWD");
                String role = rs.getString("AUTHORITY");
                 
                List<GrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority(role));
                User user = new User(id, pwd, roles);
                //이거는 VO를 할필요 없이 UserDetails 사용한거임
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            closeAll();
        }
         
        return null;
    }
}
