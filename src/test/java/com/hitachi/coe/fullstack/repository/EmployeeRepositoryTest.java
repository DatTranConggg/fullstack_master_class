package com.hitachi.coe.fullstack.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.hitachi.coe.fullstack.entity.Employee;


@DataJpaTest
@ActiveProfiles("data")
class EmployeeRepositoryTest {
    Employee employee;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp(){
        employee = new Employee();
        employee.setEmail("a.nguyen@hitachivantara.com");
        employee.setLdap("71269780");
        employee.setHccId("125351");
        employee.setName("Nguyen A 1");
        employee.setLegalEntityHireDate(new Timestamp(System.currentTimeMillis()));
        employee.setCreatedBy("admin");
        employee.setCreated(new Date());
        employeeRepository.save(employee);

    }
    @Test
    void testfindByEmailOrLdapOrHccId_whenValidEmailAndLdapAndHccId_thenReturnListEmployee(){

        List<Employee> employeeList = employeeRepository.findByEmailOrLdapOrHccId(employee.getEmail(), employee.getLdap(), employee.getHccId());
        assertNotNull(employeeList);
        assertEquals(employee.getLdap(), employeeList.get(0).getLdap());
        assertEquals(employee.getHccId(), employeeList.get(0).getHccId());
        assertEquals(employee.getEmail(), employeeList.get(0).getEmail());

    }
    @Test
    void testfindByEmailOrLdapOrHccId_whenValidLdap_thenReturnListEmployee(){

        List<Employee> employeeList = employeeRepository.findByEmailOrLdapOrHccId(null, employee.getLdap(), null);
        assertNotNull(employeeList);
        assertEquals(employee.getLdap(), employeeList.get(0).getLdap());
        assertEquals(employee.getHccId(), employeeList.get(0).getHccId());
        assertEquals(employee.getEmail(), employeeList.get(0).getEmail());

    }
    @Test
    void testfindByEmailOrLdapOrHccId_whenInvalidEmailAndLdapAndHccId_thenEmptyList(){

        List<Employee> employeeList = employeeRepository.findByEmailOrLdapOrHccId("123", "123", "123");
        assertNotNull(employeeList);
        assertEquals(0,employeeList.size());

    }
    @Test
    void testfindByHccId_whenValidHccId_thenReturnEmployee(){

        Employee employeeReturn = employeeRepository.findByHccId(employee.getHccId());
        assertNotNull(employee);
        assertEquals(employee.getLdap(), employeeReturn.getLdap());
        assertEquals(employee.getHccId(), employeeReturn.getHccId());
        assertEquals(employee.getEmail(), employeeReturn.getEmail());

    }
    @Test
    void testfindByHccId_whenInvalidHccId_thenNull(){

        Employee employeeReturn = employeeRepository.findByHccId("321");
        assertNull(employeeReturn);

    }
    @Test
    void testfindByHccIdAndLdap_whenValidLdapAndHccId_thenReturnEmployee(){

        Employee employeeReturn = employeeRepository.findByHccIdAndLdap(employee.getHccId(), employee.getLdap());
        assertNotNull(employee);
        assertEquals(employee.getLdap(), employeeReturn.getLdap());
        assertEquals(employee.getHccId(), employeeReturn.getHccId());
        assertEquals(employee.getEmail(), employeeReturn.getEmail());

    }
    @Test
    void testfindByHccIdAndLdap_whenInvalidLdap_thenNull(){

        Employee employeeReturn = employeeRepository.findByHccIdAndLdap(employee.getHccId(),"321");
        assertNull(employeeReturn);

    }
}
