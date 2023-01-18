package fr.istic.tpjpa.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import fr.istic.tpjpa.domain.Department;
import fr.istic.tpjpa.domain.Employee;

public class JpaTest {

	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("example");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		

		// TODO create entity

		for (int j = 0; j < 5000; j++) {
			
			EntityTransaction tx = manager.getTransaction();
			tx.begin();
			Department d = new Department("dep"+j);
			manager.persist(d);
			for (int i = 0; i < 2; i++) {
				Employee e = new Employee("emp_"+j + "_"+i);
				manager.persist(e);
				e.setDepartment(d);
				d.getEmployees().add(e);
			}
			tx.commit();
			
		}

		// TODO persist entity


		// TODO run request

		System.out.println(".. done");
	}

}
