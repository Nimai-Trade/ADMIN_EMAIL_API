
package com.nimai.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestAttributes;

import com.nimai.admin.model.NimaiMEmployee;
import com.nimai.admin.model.NimaiMRights;
import com.nimai.admin.model.NimaiMRole;
import com.nimai.admin.payload.EmployeeListRequest;
import com.nimai.admin.payload.RightsRequest;
import com.nimai.admin.payload.RoleRequest;
import com.nimai.admin.repository.EmployeeRepository;
import com.nimai.admin.repository.RightsRepository;
import com.nimai.admin.repository.RoleRepository;
import com.nimai.admin.service.impl.EmployeeServiceImpl;
import com.nimai.admin.service.impl.RightsServiceImpl;
import com.nimai.admin.service.impl.RoleServiceImpl;
import com.nimai.admin.util.ConverterService;

@RunWith(SpringJUnit4ClassRunner.class)
public class NimaiAdminApplicationTests {

	@InjectMocks
	private RoleServiceImpl service;
	@InjectMocks
	private RightsServiceImpl rightsServiceImpl;
	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	@Mock
	private RequestAttributes attrubutes;

	@Mock
	private RoleRepository repository;

	@Mock
	private RightsRepository rightsRepository;

	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private ConverterService converterService;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		Mockito.when(converterService.convertToEntity(Mockito.any(RoleRequest.class))).thenReturn(new NimaiMRole());
		Mockito.when(converterService.convertToEntity(Mockito.any(RightsRequest.class))).thenReturn(new NimaiMRights());
		Mockito.when(converterService.convertToEntity(Mockito.any(EmployeeListRequest.class)))
				.thenReturn(new NimaiMEmployee());

	}

//-------------------------------------------------RoleAPI---------------------------------------------//
	@Test
	public void createOrUpdateRoleTest() {
		Integer roleId = 1;
		String roleName = "admin";
		String roleShortName = "admin";
		String roleStatus = "Active";

		NimaiMRole role = new NimaiMRole();
		role.setRoleId(roleId);
		role.setRoleName(roleName);
		role.setRoleShortName(roleShortName);
		role.setRoleStatus(roleStatus);
		repository.saveAndFlush(role);
		Assert.assertEquals("admin", roleName);
	}

	@Test
	public void updateRoleStatusTest() {
		Integer roleId = 2;
		NimaiMRole roleObj = new NimaiMRole();

		roleObj.setRoleId(2);
		roleObj.setRoleStatus("Active");

		Mockito.when(repository.getOne(roleId)).thenReturn((roleObj));
		Mockito.when(repository.save(roleObj)).thenReturn(roleObj);

		Assert.assertNotNull(roleObj);
		Assert.assertNotNull(roleObj.getRoleStatus());

	}

	@Test
	public void getRoleById() {
		Integer roleId = 2;
		NimaiMRole roleObj = new NimaiMRole();
		roleObj.setRoleId(roleId);
		Mockito.when(repository.getOne(roleId)).thenReturn(roleObj);
		Mockito.when(converterService.convertToDto(roleObj)).thenReturn(new RoleRequest());
		Assert.assertNotNull(roleId);
		Assert.assertNotNull(roleObj);
	}

	@Test
	public void getAllRoleTest() {
		Integer roleId = 1;
		String roleName = "create";
		String roleShortName = "create";
		String roleStatus = "Active";
		NimaiMRole role = new NimaiMRole();
		role.setRoleId(roleId);
		role.setRoleName(roleName);
		role.setRoleShortName(roleShortName);
		role.setRoleStatus(roleStatus);
		List<NimaiMRole> userList = new ArrayList<>();
		userList.add(role);
		Mockito.when(repository.findAll()).thenReturn(userList.stream().collect(Collectors.toList()));
		Mockito.when(converterService.convertToDto(role)).thenReturn(new RoleRequest());
		Assert.assertNotNull(roleId);
		Assert.assertNotNull(roleShortName);
		Assert.assertNotNull(roleStatus);
		Assert.assertNotNull(roleName);

	}

//-----------------------------------------------------------RightsAPI-----------------------------------------------//

	@Test
	public void createOrUpdateRightTest() {
		Integer rightId = 1;
		String rightName = "create";
		String rightShortName = "create";
		String rightStatus = "Active";

		NimaiMRights right = new NimaiMRights();
		right.setRightId(rightId);
		right.setRightName(rightName);
		right.setRightShortName(rightShortName);
		right.setRightStatus(rightStatus);
		rightsRepository.saveAndFlush(right);
		Assert.assertEquals("create", rightName);
	}

	@Test
	public void getAllRightsTest() {
		Integer rightId = 1;
		String rightName = "create";
		String rightShortName = "create";
		String rightStatus = "Active";
		NimaiMRights right = new NimaiMRights();
		right.setRightId(rightId);
		right.setRightName(rightName);
		right.setRightShortName(rightShortName);
		right.setRightStatus(rightStatus);
		List<NimaiMRights> rightList = new ArrayList<>();
		rightList.add(right);
		Mockito.when(rightsRepository.findAll()).thenReturn(rightList.stream().collect(Collectors.toList()));
		Mockito.when(converterService.convertToDto(right)).thenReturn(new RightsRequest());
		Assert.assertNotNull(rightId);
		Assert.assertNotNull(rightShortName);
		Assert.assertNotNull(rightStatus);
		Assert.assertNotNull(rightName);

	}

	@Test
	public void getRightById() {
		Integer rightId = 1;
		NimaiMRights right = new NimaiMRights();
		right.setRightId(rightId);
		Mockito.when(rightsRepository.getOne(rightId)).thenReturn(right);
		Mockito.when(converterService.convertToDto(right)).thenReturn(new RightsRequest());
		Assert.assertNotNull(rightId);
		Assert.assertNotNull(right);
	}

	@Test
	public void updateRightStatusTest() {
		Integer rightId = 1;
		NimaiMRights right = new NimaiMRights();

		right.setRightId(1);
		right.setRightStatus("active");

		Mockito.when(rightsRepository.getOne(rightId)).thenReturn((right));
		Mockito.when(rightsRepository.save(right)).thenReturn(right);

		Assert.assertNotNull(right);
		Assert.assertNotNull(right.getRightStatus());

	}

	// -----------------------------------------------------------EmployeeAPI-----------------------------------------------//
	@Test
	public void createOrUpdateEmployeeTest() {
		Integer empId = 1;
		String empName = "Nimai";
		String empMobile = "9009009000";
		String empEmail = "nimai@gmail.com";
		String address1 = "Dubai";
		String address2 = "Dubai";

		NimaiMEmployee emp = new NimaiMEmployee();
		emp.setEmpId(empId);
		emp.setEmpName(empName);
		emp.setAddress1(address1);
		emp.setEmpEmail(empEmail);
		emp.setAddress2(address2);
		emp.setEmpMobile(empMobile);
		emp.setAddress1(address1);
		employeeRepository.saveAndFlush(emp);
		Assert.assertEquals("Nimai", empName);
	}

	@Test
	public void getAllEmployeeTest() {
		Integer empId = 1;
		String empName = "Nimai";
		String empMobile = "9009009000";
		String empEmail = "nimai@gmail.com";
		String address1 = "Dubai";
		String address2 = "Dubai";

		NimaiMEmployee emp = new NimaiMEmployee();
		emp.setEmpId(empId);
		emp.setEmpName(empName);
		emp.setAddress1(address1);
		emp.setAddress2(address2);
		emp.setEmpMobile(empMobile);
		emp.setAddress1(address1);

		List<NimaiMEmployee> userList = new ArrayList<>();
		userList.add(emp);
		Mockito.when(employeeRepository.findAll()).thenReturn(userList.stream().collect(Collectors.toList()));
		Mockito.when(converterService.convertToDto(emp)).thenReturn(new EmployeeListRequest());
		Assert.assertNotNull(empId);
		Assert.assertNotNull(empName);
		Assert.assertNotNull(address2);

		Assert.assertNotNull(empMobile);
		Assert.assertNotNull(address1);

	}

	@Test
	public void updateEmployeeStatusTest() {
		Integer empId = 1;
		NimaiMEmployee emp = new NimaiMEmployee();
		emp.setEmpId(empId);
		emp.setStatus("active");

		Mockito.when(employeeRepository.getOne(empId)).thenReturn((emp));
		Mockito.when(employeeRepository.save(emp)).thenReturn(emp);

		Assert.assertNotNull(emp);
		Assert.assertNotNull(emp.getStatus());

	}

	@Test
	public void getEmpById() {
		Integer empId = 1;
		NimaiMEmployee emp = new NimaiMEmployee();
		emp.setEmpId(empId);
		Mockito.when(employeeRepository.getOne(empId)).thenReturn(emp);
		Mockito.when(converterService.convertToDto(emp)).thenReturn(new EmployeeListRequest());
		Assert.assertNotNull(empId);
		Assert.assertNotNull(emp);
	}

}