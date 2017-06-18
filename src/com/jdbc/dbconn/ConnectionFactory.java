/**
 * 
 */
package com.jdbc.dbconn;

import java.sql.Connection;

/**
 * @author Nikhil
 *
 */
public interface ConnectionFactory {
	
	 Connection getConnection();

}
