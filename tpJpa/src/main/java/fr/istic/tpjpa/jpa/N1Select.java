package fr.istic.tpjpa.jpa;


import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import fr.istic.tpjpa.domain.Department;
import fr.istic.tpjpa.domain.Employee;

public class N1Select {

	private EntityManager manager;

	public N1Select(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("withoutcreate");
		EntityManager manager = factory.createEntityManager();
		N1Select test = new N1Select(manager);

		
		TypedQuery<Department> q = test.manager.createQuery("select d from Department d",Department.class);
		long start = System.currentTimeMillis();
		List<Department> res = q.getResultList();
		
		
		for (Department d : res){
			for (Employee e : d.getEmployees()){
				e.getName();
			}
		}

		long end = System.currentTimeMillis();
		long duree = end - start;
		System.err.println("temps d'exec = " +  duree);

		System.out.println(".. done");

		/**
		 * Why this is slower?
		 * Answer:
		 * When doing the query, the 'Department' entities are retrieved from the database in a lazy manner, which means
		 * that the data is loaded only when it's accessed. So when we call d.getEmployees(), it results in a separate
		 * query being executed to fetch the employees of that department,which is known as a "lazy load".
		 *
		 * The Lazy Loading allows to load related entities from the datbase on-demand, and it can be useful in scenarios where
		 * you only need to access a small portion of the related entities, as it can reduce the amount of data that is loaded
		 * from the database and reduce memory usage.
		 *
		 * The problem in here is that it leads to many additional round trips to the database, which increase the response time
		 * of the application.
		 */
	}

}
