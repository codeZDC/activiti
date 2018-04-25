package com.tuzhi.activiti.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tuzhi.activiti.base.BaseController;
import com.tuzhi.activiti.domain.Buy;
import com.tuzhi.activiti.domain.Leave;
import com.tuzhi.activiti.domain.User;
import com.tuzhi.activiti.mapper.IUserMapper;
import com.tuzhi.activiti.util.Contants;
import com.tuzhi.activiti.util.PropertiesUtil;

/**
 * @author codeZ
 * @date 2018年4月23日 下午2:09:21 流程控制器
 */

@Controller
@RequestMapping("process")
public class ProcessControlller extends BaseController {

	@Autowired
	private IUserMapper userMapper;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;

	// 部署流程
	@RequestMapping(value = "deploy")
	@ResponseBody
	public String deploy(MultipartFile file, String deployName) throws IOException {
		// 文件流为zip格式
		Deployment deploy = repositoryService.createDeployment().name(deployName)
				.addZipInputStream(new ZipInputStream(file.getInputStream())).deploy();
		return deployName + " 部署成功!";
	}

	// 请假流程提交,此步骤开始流程
	@RequestMapping("startLeaveProcess")
	@ResponseBody
	public String startLeaveProcess(Leave leave) {
		// 请假流程key
		String processKey = PropertiesUtil.getValue("leaveKey");
		Map<String, Object> map = new HashMap<>();
		// 流程开始
		String userId = getUserId();
		map.put("user", userId);// 设置提交处理人
		map.put("type", "leave");// 标识为请假申请
		map.put("form", leave);
		map.put("applyPerson", leave.getLeavePerson());

		// 获取所有用户信息 Map(用户名,用户ID)键值对
		List<User> users = userMapper.getUsers();
		for (User user : users) {
			map.put(user.getUsername(), user.getId());
		}
		runtimeService.startProcessInstanceByKey(processKey, map);
		System.err.println(processKey + " 流程已经开始...");
		return processKey + " 请假流程已经开始...";
	}
	//采购流程
	@RequestMapping("startBuyProcess")
	@ResponseBody
	public String startBuyProcess(Buy buy) {
		// 请假流程key
		String processKey = PropertiesUtil.getValue("buyKey");
		Map<String, Object> map = new HashMap<>();
		// 流程开始
		String userId = getUserId();
		map.put("user", userId);// 设置提交处理人
		map.put("type", "buy");// 标识为请假申请
		map.put("form", buy);
		map.put("applyPerson", buy.getPerson());
		
		// 获取所有用户信息 Map(用户名,用户ID)键值对
		List<User> users = userMapper.getUsers();
		for (User user : users) {
			map.put(user.getUsername(), user.getId());
		}
		runtimeService.startProcessInstanceByKey(processKey, map);
		System.err.println(processKey + " 流程已经开始...");
		return processKey + " 采购流程已经开始...";
	}

	// 获取用户id
	private String getUserId() {
		return ((User) session.getAttribute(Contants.SESSION_USER)).getId().toString();
	}

	// 获取自己的任务
	@RequestMapping("myTask")
	@ResponseBody
	public List<Map<String, String>> getMyTask() {
		String userId = getUserId();
		// 将申请人(变量) , 申请类型, 和任务id 传到页面
		List<Map<String, String>> myTasks = new LinkedList<>();
		Map<String, String> map = null;

		List<Task> list = taskService.createTaskQuery().taskAssignee(userId).list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(task.getProcessDefinitionId()).singleResult();
				// 获取表单变量
				Deployment deployment = repositoryService.createDeploymentQuery()
						.deploymentId(processDefinition.getDeploymentId()).singleResult();
				// 初始化map,将所要的信息放入其中
				map = new HashMap<>();
				// 申请人姓名和申请类型
				Map<String, Object> variables = taskService.getVariables(task.getId());
				map.put("applyPerson", variables.get("applyPerson").toString());
				map.put("type", variables.get("type").toString());// 根据不同类型加载不同表单
				map.put("user", userMapper.findUsernameById(variables.get("user").toString()));
				map.put("applyTypeName", deployment.getName());
				map.put("taskId", task.getId());
				myTasks.add(map);
				System.out.print("任务id: " + task.getId());
				System.out.println("任务执行人: " + task.getAssignee());
			}
		} else {
			System.err.println("没有需要处理的任务!");
		}
		return myTasks;
	}
	// 获取所有正在执行的申请
	@RequestMapping("allApply")
	@ResponseBody
	public List<Map<String, String>> getAllApply() {
		// 将申请人(变量) , 申请类型, 和任务id 传到页面
		List<Map<String, String>> myTasks = new LinkedList<>();
		Map<String, String> map = null;
		
		List<Task> list = taskService.createTaskQuery().list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(task.getProcessDefinitionId()).singleResult();
				// 获取表单变量
				Deployment deployment = repositoryService.createDeploymentQuery()
						.deploymentId(processDefinition.getDeploymentId()).singleResult();
				// 初始化map,将所要的信息放入其中
				map = new HashMap<>();
				// 申请人姓名和申请类型
				Map<String, Object> variables = taskService.getVariables(task.getId());
				map.put("applyPerson", variables.get("applyPerson").toString());
				map.put("type", variables.get("type").toString());// 根据不同类型加载不同表单
				map.put("user", userMapper.findUsernameById(variables.get("user").toString()));
				map.put("applyTypeName", deployment.getName());
				map.put("taskId", task.getId());
				myTasks.add(map);
				System.out.print("任务id: " + task.getId());
				System.out.println("任务执行人: " + task.getAssignee());
			}
		} else {
			System.err.println("没有需要处理的任务!");
		}
		return myTasks;
	}

	// 根据taskId获取下一个节点信息(主要是看有几个处理人)
	@RequestMapping("nextProcess")
	@ResponseBody
	public Map<String, Object> getNextProcess(String taskId) {
		// String roleId = "1";
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String definitionId = task.getProcessDefinitionId();
		System.err.println("当前流程定义iD: " + definitionId);
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(definitionId);

		List<ActivityImpl> activitiList = def.getActivities();

		// 3、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
		String excId = task.getExecutionId();
		System.err.println("executionId : " + excId);
		ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
				.singleResult();
		String activitiId = execution.getActivityId();
		System.err.println("activitiId: " + activitiId);

		// 4、然后循环activitiList 并判断出当前流程所处节点，然后得到当前节点实例，
		// 根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			System.err.println("id: " + id);
			if (activitiId.equals(id)) {
				// 输出某个节点的某种属性
				System.err.println("当前任务：" + activityImpl.getProperty("name"));
				// 获取从某个节点出来的所有线路
				List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
				for (PvmTransition tr : outTransitions) {
					// 获取线路的终点节点
					PvmActivity ac = tr.getDestination();
					System.err.println("下一步任务任务：" + ac.getProperty("name"));
					System.err.println(ac.getId());
					String string = ac.getId();
					// 判断下一级是一个人处理还是两个人处理
					Map<String, Object> map = new HashMap<>();
					if (string.contains("_")) {// 说明是两个人,返回一个数组,前端select选择
						String[] arr = string.split("_");
						List<Map<String, Object>> rolesMap = userMapper.getNextRoles(arr);
						map.put("nextCount", arr.length);
						map.put("roles", rolesMap);
						return map;
					} else {
						map.put("nextCount", 1);
						return map;
					}
				}
				break;
			}
		}
		return null;
	}

	// 通过任务,,实际业务要获取下一个办理部门
	@RequestMapping("accept")
	@ResponseBody
	public String accept(String taskId, String comment,String next) {
		taskService.setVariableLocal(taskId, "comment", comment);
		if(next!=null&&next!=""){
			Map<String, Object> map = new HashMap<>();
			map.put("next", next);
			taskService.complete(taskId,map);
		}else{
			taskService.complete(taskId);
		}
		return "已通过";
	}
	// 驳回,没整
	/*@RequestMapping("refuse")
	@ResponseBody
	public String refuse(String taskId,String comment) {
		String userId = taskService.getVariable(taskId, "user").toString();
		taskService.delegateTask(taskId, userId);
		return "已驳回";
	}*/

	// 获取表单form的数据
	@RequestMapping("formMsg")
	@ResponseBody
	public Object getFormMsg(String taskId,String processInstanceId) {
		if(taskId!=null&&taskId!="")
			return taskService.getVariables(taskId).get("form");
		else {
			HistoricVariableInstance variableInstance = historyService.createHistoricVariableInstanceQuery()
			.processInstanceId(processInstanceId).variableName("form").singleResult();
			return variableInstance.getValue();
		}
	}

	// 获取流程历史执行记录
	@RequestMapping("history")
	@ResponseBody
	public List<Map<String, Object>> getHistory(String taskId,String processInstanceId) {
		
		List<Map<String, Object>> maps = new LinkedList<>();
		Map<String, Object> map;

		if(taskId!=null&&taskId!="")
			processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();

		List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).finished().orderByTaskCreateTime().asc().list();
		List<HistoricVariableInstance> vas = historyService.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId).list();
		for (HistoricTaskInstance historicTaskInstance : list) {
			map = new HashMap<>();
			map.put("assignee", historicTaskInstance.getAssignee());
			map.put("assigneeName", historicTaskInstance.getName());
			// System.err.print("
			// 当前执行人assignee:"+historicTaskInstance.getAssignee());
			// System.err.print(" 申请人职能 :"+historicTaskInstance.getName());
			// System.err.print(" 任务ID:" +historicTaskInstance.getId() );
			// System.err.print(" 申请信息:");
			for (HistoricVariableInstance historicVariableInstance : vas) {
				if (historicVariableInstance.getTaskId() != null
						&& historicVariableInstance.getTaskId().equals(historicTaskInstance.getId())) {
					// System.err.println(" 备注 :"
					// +historicVariableInstance.getVariableName()
					// + "=" +historicVariableInstance.getValue() );
					map.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue());
				}
			}
			maps.add(map);
		}
		return maps;
	}

	//获取我的申请
	@RequestMapping("getMyApply")
	@ResponseBody
	public List<Map<String, Object>> getMyApply(){
		String userId = getUserId();
		List<Map<String, Object>> maps = new LinkedList<>();
		Map<String, Object> map = null;
		List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()
		.variableValueEquals(PropertiesUtil.getValue("currentUserMapKey"),userId).list();
		for (HistoricVariableInstance historicVariableInstance : list) {
			String processInstanceId = historicVariableInstance.getProcessInstanceId();//获取流程实例id
			//显示需要保存的参数
			//流程实例id
			map = new HashMap<>();
			map.put("processInstanceId", processInstanceId);
			//根据实例id获取所需要参数
			List<HistoricVariableInstance> variables = 
					historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
			//申请人applyPerson ,类型type ,  
			for (HistoricVariableInstance variable : variables) {
				if("type".equals(variable.getVariableName())||"applyPerson".equals(variable.getVariableName()))
					map.put(variable.getVariableName(), variable.getValue());
			}
			//处理状态,,已完成  ,, 流程中,,根据实例id获取任务,,,有任务说明流程中,,没有任务说明已经处理完成
			Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
			if(task==null)
				map.put("status", "已结束");
			else
				map.put("status", "流程中("+userMapper.findUsernameById(task.getAssignee())+")");
			//根据实例id获取,
			String deploymentId = historyService.createHistoricProcessInstanceQuery().
					processInstanceId(processInstanceId).singleResult().getDeploymentId();
			String deployName = repositoryService.createDeploymentQuery()
					.deploymentId(deploymentId).singleResult().getName();
			map.put("deployName", deployName);
			maps.add(map);
		}
		return maps;
	}
	
	
}
