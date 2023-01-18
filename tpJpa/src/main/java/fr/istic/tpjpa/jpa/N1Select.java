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

		// TODO persist entity


		// TODO run request

		System.out.println(".. done");
	}

}
