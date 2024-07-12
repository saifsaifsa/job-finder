package com.esprit.jobfinder.dto;

import com.esprit.jobfinder.models.User;
import com.esprit.jobfinder.models.enums.ERole;
import com.esprit.jobfinder.payload.request.CreateUserReq;
import com.esprit.jobfinder.utiles.DateUtils;

public class UserMapper {
     public static User createUserReqToUser(CreateUserReq createUserReq) {
        if ( createUserReq == null ) {
            return null;
        }

        User user = new User();

        user.setPhone( createUserReq.getPhone() );
        user.setUsername( createUserReq.getUsername() );
        user.setFirstName( createUserReq.getFirstName() );
        user.setLastName( createUserReq.getLastName() );
        user.setActive( createUserReq.getActive() );
        user.setEmail( createUserReq.getEmail() );
        user.setPassword( createUserReq.getPassword() );
        user.setBirthDay(DateUtils.parseDate(createUserReq.getBirthDay()));
        user.setRole( ERole.valueOf(createUserReq.getRole()) );
        return user;
    }
    public static CreateUserReq userToCreateUserReq(User user) {
        if ( user == null ) {
            return null;
        }

        CreateUserReq createUserReq = new CreateUserReq();

        createUserReq.setUsername( user.getUsername() );
        createUserReq.setFirstName( user.getFirstName() );
        createUserReq.setLastName( user.getLastName() );
        createUserReq.setActive( user.getActive() );
        createUserReq.setEmail( user.getEmail() );
        createUserReq.setPassword( user.getPassword() );
        createUserReq.setPhone( user.getPhone() );

        createUserReq.setBirthDay( user.getBirthDay().toString() );
        createUserReq.setRole( user.getRole().name() );

        return createUserReq;
    }
}

