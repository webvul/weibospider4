/**
 * 
 */
package org.cheetyan.weibospider.plugins;

import org.cheetyan.weibospider.model.sina.Status;
import org.cheetyan.weibospider.model.sina.User;
import org.cheetyan.weibospider.model.tx.StatusTX;
import org.cheetyan.weibospider.model.tx.UserTX;
import org.cheetyan.weibospider.taskmodel.Task;

/**
 * @author Administrator
 * note: session is singleton and must be synclized
 */
public interface Plugin {
	public void close();

	public void save(Task t, Status st);

	public void save(Task t, User user);

	public void save(Task t, StatusTX st);

	public void save(Task t, UserTX usertx);
}
