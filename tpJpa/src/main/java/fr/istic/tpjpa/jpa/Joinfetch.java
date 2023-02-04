package fr.istic.tpjpa.jpa;


import java.util.List;


import fr.istic.tpjpa.domain.Department;
import fr.istic.tpjpa.domain.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;


public class Joinfetch {

	private EntityManager manager;

	public Joinfetch(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("withoutcreate");
		EntityManager manager = factory.createEntityManager();
		Joinfetch test = new Joinfetch(manager);

		
		TypedQuery<Department> q = test.manager.createQuery("select distinct d from Department d join fetch d.employees e",Department.class);
		long start = System.currentTimeMillis();
		List<Department> res = q.getResultList();
		
		for (Department d : res){
			for (Employee e : d.getEmployees()){
				System.out.println(e.getName());
			}
		}

		long end = System.currentTimeMillis();
		long duree = end - start;
		System.err.println("temps d'exec = " +  duree);

		System.out.println(".. done");

		/**
		 * Why this is faster?
		 * Answer:
		 * When doing the query, the 'Department' entities and their 'Employees' entities are retreived from the database
		 * in an eager manner, which means that all the data is loaded at once in a single query.This is thanks to the
		 * use of the 'join fetch', as we tell the JPA engine to load the associated entities in the same query.
		 * So when we call d.getEmployees(), it doesn't result in a separate query because the data is already loaded.
		 *
		 * It's faster than the N1Select class because we make only one query instead of two, and all the data is loaded
		 * in a single round trip to the database.
		 *
		 *
		 */
	}

}
