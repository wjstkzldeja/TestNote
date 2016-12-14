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
        //�����ͺ��̽����� ������ �̿��� ����
        UserDetails userDetails = UserDAO.getUserDetails(userId);
        if (userDetails==null) throw new UsernameNotFoundException("������ ������ ã�� �� �����ϴ�.");
        return userDetails;
    }
}
