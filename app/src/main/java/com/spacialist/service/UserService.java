package com.spacialist.service;

import com.spacialist.data.dto.UserDTO;

public interface UserService {

    public UserDTO getUserByUsernameAndPassword(String username, String password);
    public UserDTO getUserByAcctId(String acctId);
}
