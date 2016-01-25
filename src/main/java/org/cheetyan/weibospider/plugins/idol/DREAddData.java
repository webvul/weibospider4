/**
 * 
 */
package org.cheetyan.weibospider.plugins.idol;

import java.io.IOException;

/**
 * @author cheetyan
 *
 */
public class DREAddData {
	private static final String baseUrl = "DREADDDATA?KillDuplicates=REFERENCE&DREDbName=";

	public static String post(String idx, String database) {
		String url = baseUrl + database;
		try {
			return HttpConnectionUtil.postEntity(url, idx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error execute httpconnectionUtil";
	}
}
