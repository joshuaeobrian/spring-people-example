package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by josh on 4/13/17.
 */


@Component
public class PersonRepository {

	@Autowired
	JdbcTemplate template;

	public List<Person> getPersonList() {

		return template.query("SELECT personid, title, firstname, middlename, lastname, suffix FROM person;",
				(rs,i)-> new Person(rs.getInt("personid"),
						rs.getString("title"),
						rs.getString("firstname"),
						rs.getString("middlename"),
						rs.getString("lastname"),
						rs.getString("suffix")));
	}
	public List<Person> getPeopleWithFirstNameInOrder(String name){
		return template.query("SELECT personid, title, firstname, middlename, lastname, suffix FROM person " +
						" WHERE lower(firstname) like lower(?) OR lower(lastname) like lower(?) order by firstname asc LIMIT 200",
				new Object[] {'%'+name+'%','%'+name+'%'},
				(rs,i)->  new Person(rs.getInt("personid"),
						rs.getString("title"),
						rs.getString("firstname"),
						rs.getString("middlename"),
						rs.getString("lastname"),
						rs.getString("suffix")));
	}

	public List<Person> getPersonBySearch(String name) {

		return template.query("SELECT personid, title, firstname, middlename, lastname, suffix FROM person " +
							" WHERE lower(firstname) like lower(?) OR lower(lastname) like lower(?) order by personid LIMIT 200",
				new Object[] {'%'+name+'%','%'+name+'%'},
				(rs,i)->  new Person(rs.getInt("personid"),
						rs.getString("title"),
						rs.getString("firstname"),
						rs.getString("middlename"),
						rs.getString("lastname"),
						rs.getString("suffix")));
	}

	public Person getPersonById(Integer id) {
		return template.queryForObject("SELECT personid, title, firstname, middlename, lastname, suffix FROM person " +
						" WHERE person.personid=?",
				new Object[] {id},
				(rs,i)->  new Person(rs.getInt("personid"),
						rs.getString("title"),
						rs.getString("firstname"),
						rs.getString("middlename"),
						rs.getString("lastname"),
						rs.getString("suffix")));
	}

	public void updatePerson(Person person){
		template.update("update person set title=?, firstname=?, middlename=?, lastname=?, suffix=?" +
				"where personid = ? ", new Object[]{
				person.getTitle(),
				person.getFirstName(),
				person.getMiddleName(),
				person.getLastName(),
				person.getSuffix(),
				person.getId()
		});

	}
	public void createPerson(Person person){
		template.update("insert into person( title, firstname, middlename, lastname, suffix)" +
				"VALUES (?,?,?,?,?)",
				new Object[]{
						person.getTitle(),
						person.getFirstName(),
						person.getMiddleName(),
						person.getLastName(),
						person.getSuffix(),
				});


	}

	public List<Person> getPeopleWithLastNameInOrder(String name) {
		return template.query("SELECT personid, title, firstname, middlename, lastname, suffix FROM person " +
						" WHERE lower(firstname) like lower(?) OR lower(lastname) like lower(?) order by lastname asc LIMIT 200",
				new Object[] {'%'+name+'%','%'+name+'%'},
				(rs,i)->  new Person(rs.getInt("personid"),
						rs.getString("title"),
						rs.getString("firstname"),
						rs.getString("middlename"),
						rs.getString("lastname"),
						rs.getString("suffix")));
	}
}
