/**
 * 
 */
package org.cheetyan.weibospider.plugins;

import javax.transaction.Transactional;

import org.cheetyan.weibospider.basicdao.BaseDAOImpl;
import org.cheetyan.weibospider.model.sina.Status;
import org.cheetyan.weibospider.model.sina.User;
import org.cheetyan.weibospider.model.tx.StatusTX;
import org.cheetyan.weibospider.model.tx.UserTX;
import org.cheetyan.weibospider.taskmodel.Task;
import org.springframework.stereotype.Repository;

/**
 * @author cheetyan
 *
 */
@Repository(value = "persistPlugin")
@Transactional
public class PersistPlugin extends BaseDAOImpl<Status, Long> implements Plugin {
	public PersistPlugin() {
		super();
	}

	@Override
	public void close() {
	}

	@Override
	public void save(Task t, Status st) {
		super.saveOrUpdate(st);
	}

	@Override
	public void save(Task t, User user) {
		// super.save(user);
	}

	@Override
	public void save(Task t, StatusTX st) {
		super.getSession().saveOrUpdate(st);
	}

	@Override
	public void save(Task t, UserTX usertx) {
		super.getSession().saveOrUpdate(usertx);
	}
}
