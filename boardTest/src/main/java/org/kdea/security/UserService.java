package org.kdea.security;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
@Service
public class UserService implements UserDetailsService 
{
    @Autowired
    private UserDAO UserDAO;
 
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //데이터베이스에서 가져온 이용자 정보
        UserDetails userDetails = UserDAO.getUserDetails(userId);
        if (userDetails==null) throw new UsernameNotFoundException("접속자 정보를 찾을 수 없습니다.");
        return userDetails;
    }
}
