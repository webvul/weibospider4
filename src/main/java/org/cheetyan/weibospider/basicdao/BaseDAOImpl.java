/**
 * 
 */
package org.cheetyan.weibospider.basicdao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.Id;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author cheetyan
 *
 */
@Transactional
public class BaseDAOImpl<M, PK extends Serializable> implements IBaseDAO<M, PK> {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;
	//
	private final Class<M> entityClass;
	private final String HQL_LIST_ALL;
	private final String HQL_COUNT_ALL;
	private String pkName;

	@SuppressWarnings("unchecked")
	public BaseDAOImpl() {
		this.entityClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		Field[] fields = this.entityClass.getDeclaredFields();
		for (Field f : fields) {
			if (f.isAnnotationPresent(Id.class)) {
				this.pkName = f.getName();
			}
		}
		// Assert.notNull(pkName);
		// TODO @Entity name not null
		HQL_LIST_ALL = "from " + this.entityClass.getSimpleName() + " order by " + pkName + " desc";
		HQL_COUNT_ALL = " select count(*) from " + this.entityClass.getSimpleName();
	}

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(M model) {
		return (PK) getSession().save(model);
	}

	@Override
	public void saveOrUpdate(M model) {
		getSession().saveOrUpdate(model);
	}

	@Override
	public void update(M model) {
		getSession().update(model);
	}

	@Override
	public void merge(M model) {
		getSession().merge(model);
	}

	@Override
	public void delete(PK id) {
		getSession().delete(this.get(id));
	}

	@Override
	public void deleteObject(M model) {
		getSession().delete(model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public M get(PK id) {
		return (M) getSession().get(this.entityClass, id);
	}

	@Override
	public int countAll() {
		Query query = getSession().createQuery(HQL_COUNT_ALL);
		return ((BigInteger) query.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	protected <T> T aggregate(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		return (T) query.uniqueResult();
	}

	@Override
	public List<M> listAll() {
		return list(HQL_LIST_ALL);
	}

	@Override
	public List<M> listAll(int pn, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(PK id) {
		return get(id) != null;
	}

	@Override
	public void flush() {
		getSession().flush();
	}

	@Override
	public void clear() {
		getSession().clear();
	}

	@Override
	public <T> List<T> list(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Collection<M> entities) {
		if (entities == null || entities.size() == 0)
			return;
		int index = 1;
		for (M model : entities) {
			getSession().save(model);
			if (index % 50 == 0) {
				getSession().flush();
				getSession().clear();
			}
			index++;
		}
		getSession().flush();
		getSession().clear();
		return;
	}
}
