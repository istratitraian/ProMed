/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.services;

import ro.duoline.promed.domains.User;

/**
 *
 * @author I.T.W764
 */
public interface UserService extends CRUDService<User> {

    public User findByUsername(String username);
    public User findByEmail(String email);
}
