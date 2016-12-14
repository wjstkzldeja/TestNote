package org.kdea.security;
import java.util.Collection;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
 
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider 
{
    @Autowired
    UserService userService;
 
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException 
    {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println(username.length());
        if(username.equals("") || password.equals("")){
        	throw new BadCredentialsException("���̵� �Ǵ� �н����带 �Է����ּ���");
        }
        
        User user = null;
        Collection<? extends GrantedAuthority> authorities = null;
 
        try {
            user = (User)userService.loadUserByUsername(username);
            System.out.println("name :"+user.getUsername());
            System.out.println("pw :"+user.getPassword());
            System.out.println("auth asdasd:"+user.getAuthorities());
            // �̿��ڰ� �α��� ������ �Է��� ��й�ȣ�� DB�κ��� ������ ��ȣȭ�� ��й�ȣ�� ���Ѵ�
            if (!passwordEncoder.matches(password, user.getPassword())) 
                    throw new BadCredentialsException("��й�ȣ ����ġ");
 
            authorities = user.getAuthorities();
        } catch(UsernameNotFoundException e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        } catch(BadCredentialsException e) {
            e.printStackTrace();
            throw new BadCredentialsException(e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
       
        
    }
 
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}
