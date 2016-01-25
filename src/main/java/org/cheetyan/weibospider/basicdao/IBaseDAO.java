package org.cheetyan.weibospider.basicdao;

import java.util.Collection;
import java.util.List;

public interface IBaseDAO<M, PK> {
	public PK save(M model);

	public void saveOrUpdate(M model);

	public void update(M model);

	public void merge(M model);

	public void delete(PK id);

	public void deleteObject(M model);

	public M get(PK id);

	public int countAll();

	public List<M> listAll();

	public List<M> listAll(int pn, int pageSize);

	boolean exists(PK id);

	public void flush();

	public void clear();

	public <T> List<T> list(String hql);

	public void save(Collection<M> entities);
}
