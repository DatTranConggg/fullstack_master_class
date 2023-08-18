package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.entity.EmployeeLevel;
import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.model.EmployeeExportModel;
import com.hitachi.coe.fullstack.model.ExportRequest;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.service.impl.ExportServiceImpl;
import com.hitachi.coe.fullstack.transformation.EmployeeTransformer;
import com.hitachi.coe.fullstack.util.DateFormatUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExportServiceTest {

	@InjectMocks
	private ExportServiceImpl exportServiceImpl;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	EmployeeTransformer employeeTransformer;

	Employee employee1;

	Employee employee2;

	List<Employee> list;

	@Test
	void exportExcelImpl() {
		ExportRequest exportRequest = ExportRequest.builder()
				.keyWord(null)
				.typeFile("xlsx")
				.practiceName(null)
				.coeCoreTeamName(null)
				.branchName(null)
				.fromDateStr("2023-04-05")
				.toDateStr("2023-04-05")
				.pageNo(0)
				.itemPerPage(10)
				.sortBy("name")
				.isAscending(true)
				.build();

		EmployeeExportModel employeeExportModel1 = EmployeeExportModel.builder().email("lam.viluong@hitachivantara.com").name("Lam Vi Luong").build();
		EmployeeExportModel employeeExportModel2 = EmployeeExportModel.builder().email("phuoc.nguyen1@hitachivantara.com").name("Phuoc Nguyen").build();
		Pageable employeePage = PageRequest.of(exportRequest.getPageNo(), exportRequest.getItemPerPage(), Sort.by(exportRequest.getSortBy()).ascending());
		Page<Employee> page = new PageImpl<>(list);

		when(employeeRepository.filterEmployees(exportRequest.getKeyWord(), exportRequest.getPracticeName(),
				exportRequest.getCoeCoreTeamName(), exportRequest.getBranchName(),1,
				DateFormatUtils.convertTimestampFromString(exportRequest.getFromDateStr()),
				DateFormatUtils.convertTimestampFromString(exportRequest.getToDateStr()), employeePage)).thenReturn(page);
		when(employeeTransformer.convertEmployeeToExportModel(any(Employee.class))).thenReturn(employeeExportModel1);
		ResponseEntity<Resource> result = exportServiceImpl.exportExcel(exportRequest);

		Assertions.assertNotNull(result);
	}
	@BeforeEach
	private void initDataTest() {
		BusinessUnit businessUnit = new BusinessUnit();
		businessUnit.setName("DS");
		businessUnit.setId(4);
		Branch branch = new Branch();
		branch.setId(1);
		branch.setName("VIet Nam");
		EmployeeLevel employeeLevel = new EmployeeLevel();
		Level level = new Level();
		level.setName("Super Junior");
		employeeLevel.setLevel(level);
		List<EmployeeLevel> employeeLevels = List.of(employeeLevel);
		employee1 = new Employee();
		employee1.setId(21);
		employee1.setName("Lam Vi Luong");
		employee1.setEmail("lam.viluong@hitachivantara.com");
		employee1.setBusinessUnit(businessUnit);
		employee1.setBranch(branch);
		employee1.setEmployeeLevels(employeeLevels);
		employee2 = new Employee();
		employee2.setId(22);
		employee2.setEmail("phuoc.nguyen1@hitachivantara.com");
		employee2.setName("Phuoc Nguyen");
		employee2.setBusinessUnit(businessUnit);
		employee2.setBranch(branch);
		employee2.setEmployeeLevels(employeeLevels);
		list = List.of(employee1,employee2);
	}

}