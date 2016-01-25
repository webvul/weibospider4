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
 * @author cheetyan
 *
 */
public class SimplePlugin implements Plugin {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(Task t, Status st) {
		System.out.println(t.toString()+"   "+st.toString());		
	}
	@Override
	public void save(Task t, User user) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void save(Task t, StatusTX st) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(Task t, UserTX usertx) {
		// TODO Auto-generated method stub
		
	}

}
