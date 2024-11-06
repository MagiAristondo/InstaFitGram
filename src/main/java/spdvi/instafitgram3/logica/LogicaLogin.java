/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spdvi.instafitgram3.logica;

import spdvi.instafitgram3.DataAcces.DataAccess;
import spdvi.instafitgram3.dto.User;
import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author giari
 */
public class LogicaLogin {
    public static boolean ComprovarLogin(String username, char[] password)
    {        
        
        DataAccess da = new DataAccess();
        User user = da.getUserByUsername(username);
        
        if(BCrypt.verifyer().verify(password, user.getPasswordHash()).verified)
        {
            System.out.println("Holaaa");
            return true;
        }
        
        return false;
    }
}
