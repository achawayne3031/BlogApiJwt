package com.BlogApiJwt.service.impl;

import com.BlogApiJwt.dao.RoleDao;
import com.BlogApiJwt.dao.UserDao;
import com.BlogApiJwt.entity.Role;
import com.BlogApiJwt.entity.User;
import com.BlogApiJwt.exception.CustomException;
import com.BlogApiJwt.repository.UserRepository;
import com.BlogApiJwt.service.UserService;
import com.BlogApiJwt.validation.ResetPasswordValidation;
import com.BlogApiJwt.validation.UpdateUserValidation;
import com.BlogApiJwt.validation.UserLoginValidation;
import com.BlogApiJwt.validation.UserValidation;
import jakarta.mail.AuthenticationFailedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private UserDao userDao;

    private RoleDao roleDao;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private UserRepository userRepository;



    public UserServiceImpl(UserDao userDao,
                           RoleDao roleDao,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           AuthenticationManager authenticationManager,
                           UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public User findByFullName(String fullName) {
        return userDao.findUserByFullName(fullName);
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findUserByEmail(username);
    }

    @Override
    public User save(UserValidation validation) {
        User user = new User();

        user.setEmail(validation.getEmail());
        user.setEnabled(true);
        user.setFullName(validation.getFullName());
        user.setPassword(passwordEncoder.encode(validation.getPassword()));

        String rolePrefix = "role_";
        String role = rolePrefix.concat(validation.getRole()).toUpperCase();
        user.setRoles(Arrays.asList(roleDao.findByName(role)));

        userDao.save(user);

        return user;
    }

    @Override
    public User loginUser(UserLoginValidation userLoginValidation) {
        Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginValidation.getEmail(), userLoginValidation.getPassword()));
       ///// SecurityContextHolder.getContext().setAuthentication(authenticate);

        return userRepository.findByEmail(userLoginValidation.getEmail()).orElseThrow();

      ///// return userDao.findUserByEmail(userLoginValidation.getEmail());
    }

    @Override
    public void update(UpdateUserValidation updateUserValidation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        User userData = userDao.findById(currentUser.getId());

        if(userData == null){
            throw new CustomException("User not found");
        }

        userData.setFullName(updateUserValidation.getFullName());
        userData.setEmail(updateUserValidation.getEmail());
        userDao.update(userData);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordValidation resetPasswordValidation) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        User userData = userDao.findById(currentUser.getId());

        if(userData == null){
            throw new CustomException("User not found");
        }

        if(!passwordEncoder.matches(resetPasswordValidation.getOldPassword(), userData.getPassword())){
            throw new CustomException("Incorrect password");
        }

        userData.setPassword(passwordEncoder.encode(resetPasswordValidation.getNewPassword()));
        userDao.update(userData);
    }

    @Override
    public Page<User> paginatedUsers(int page, int size) {
        Pageable pager = PageRequest.of(page, size);

        return userRepository.findAll(pager);
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//       User user = userDao.findUserByEmail(username);
//
//       if(user == null){
//           throw new UsernameNotFoundException("Invalid email address");
//       }
//
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
//                mapRolesToAuthorities(user.getRoles()));
//    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}
