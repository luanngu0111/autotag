package upskills.database.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author LTLiem
 *
 * @param <T> : <<interface>> Data Access Object
 */
public interface Dao<T extends Object> {
	
	 public void create(T t);
	
	 public void delete(T t);
	 
	 public void deleteById(Serializable id);
	 
	 public void deleteAll();
	
	 public void update(T t);
	
	 public T get(Serializable id);
	 
	 public T load(Serializable id);
	 
	 public List<T> getAll();	
	
	 public long count();
	 
	 public boolean exists(Serializable id);
}
