package com.stormpath.shiro.samples.springboot.common.dao;

import com.stormpath.shiro.samples.springboot.common.model.UserBO;

import java.util.Optional;

/**
 * Created by cbarad on 2016-12-02.
 * A handler / helper class to let Manager be worry-free from DAO semantics.
 */
//@Repository
public class IdentityManagerRepository {

    //@Autowired
    private UserDAO userDAO;

    public IdentityManagerRepository() {

    }

    public UserBO getUserByUsername(String username) {

        Optional<UserEntity> userEntity = userDAO.findByUsername(username);
        return toUserBO(userEntity.orElse(null));
    }

    public static UserBO toUserBO(UserEntity userEntity) {
        if (userEntity != null) {
            UserBO userBO = new UserBO();
            userBO.setId(userEntity.getId());
            userBO.setUsername(userEntity.getUserName());
            userBO.setEmail(userEntity.getEmail());
            userBO.setFirstName(userEntity.getFirstName());
            userBO.setLastName(userEntity.getLastName());
            userBO.setPassword(userEntity.getPassword());

            return userBO;
        }
        return null;
    }
}
