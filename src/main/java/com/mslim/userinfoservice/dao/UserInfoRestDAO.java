package com.mslim.userinfoservice.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "users", collectionResourceRel = "users") // TODO figure out why it's not working
public interface UserInfoRestDAO {

}
