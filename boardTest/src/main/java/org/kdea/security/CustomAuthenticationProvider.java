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
        	throw new BadCredentialsException("아이디 또는 패스워드를 입력해주세요");
        }
        
        User user = null;
        Collection<? extends GrantedAuthority> authorities = null;
 
        try {
            user = (User)userService.loadUserByUsername(username);
            System.out.println("name :"+user.getUsername());
            System.out.println("pw :"+user.getPassword());
            System.out.println("auth asdasd:"+user.getAuthorities());
            // 이용자가 로그인 폼에서 입력한 비밀번호와 DB로부터 가져온 암호화된 비밀번호를 비교한다
            if (!passwordEncoder.matches(password, user.getPassword())) 
                    throw new BadCredentialsException("비밀번호 불일치");
 
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
