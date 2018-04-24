package com.tuzhi.test;
/**
 * @author codeZ
 * @date 2018年4月20日 上午9:04:29
 * 
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
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
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class Test1 {

	private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
	private TaskService taskService = engine.getTaskService();
	private RuntimeService runtimeService = engine.getRuntimeService();
	private RepositoryService repositoryService = engine.getRepositoryService();

	@Test // 部署流程
	public void deploy() {
		Deployment deploy = repositoryService.createDeployment().name("文件申请流程").category("办公类别")
				.addClasspathResource("flow/buy.bpmn")
				.addClasspathResource("flow/buy.png").deploy();
		System.out.println("部署id : " + deploy.getId());
		System.out.println("部署名称 : " + deploy.getName());
	}

	@Test // 开始流程
	public void startProcess() {
		String personId = "525";
		String pkey = "applyId";
		Map<String, Object> map = new HashMap<>();
		map.put("boss", "1");
		map.put("manager", "2");
		map.put("applyPerson", "3");
		map.put("personId", personId);
		runtimeService.startProcessInstanceByKey(pkey, map);
		System.err.println(pkey + " 流程已经开始...");
	}

	
	@Test // 获取任务
	public void getTask() {
		String roleId = "3";
		List<Task> list = taskService.createTaskQuery().taskAssignee(roleId).list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.print("任务id: " + task.getId());
				System.out.println("  任务执行人: " + task.getAssignee());
				ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(task.getProcessDefinitionId()).singleResult();
				// 获取表单变量
				Deployment deployment = repositoryService
						.createDeploymentQuery().processDefinitionKey(processDefinition.getKey())
						.singleResult();
				System.out.println(deployment.getName());
			}
		} else {
			System.err.println("没有需要处理的任务!");
		}
	}
	
	@Test // 获取所有正在执行任务
	public void getAllTask() {
		List<Task> list = taskService.createTaskQuery().list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.print("任务id: " + task.getId());
				System.out.println("  任务执行人: " + task.getAssignee());
				// 获取表单变量
				//Purchase purchase = (Purchase) taskService.getVariables(task.getId()).get("form");
				//if (purchase != null)
				//	System.err.println(purchase);
				System.err.println("***************************************");
			}
		} else {
			System.err.println("没有需要处理的任务!");
		}
	}

	@Test
	public void completeTask() {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> map2 = new HashMap<>();
		map.put("manage", "2");
		map2.put("remarks", "我是总经理,,我同意了!");
		String taskId = "10003";
		taskService.setVariablesLocal(taskId,map2);
		taskService.complete(taskId,map);
		System.err.println("任务 " + taskId + " 已完成");
	}

	@Test
	public void test() {
		//String roleId = "1";
		Task task = taskService.createTaskQuery().taskId("27507").singleResult();
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
			System.err.println("id: " + id );
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
				}
				break;
			}
		}
	}
	
	//获取历史完成流程
	@Test
	public void getHistory(){
		List<HistoricTaskInstance> list = engine.getHistoryService().createHistoricTaskInstanceQuery()
		.executionId("20001").finished().list();
		List<HistoricVariableInstance> vas = engine.getHistoryService().createHistoricVariableInstanceQuery()
				.executionId("20001").list() ;
		for (HistoricTaskInstance historicTaskInstance : list) {                     
			
			System.err.print("  当前执行人assignee:"+historicTaskInstance.getAssignee());
			System.err.print("  申请人职能 :"+historicTaskInstance.getName());
			System.err.print("  任务ID:" +historicTaskInstance.getId()   );
			System.err.print("  申请信息:");
			for (HistoricVariableInstance historicVariableInstance : vas) {
				if(historicVariableInstance.getTaskId()!=null
						&&historicVariableInstance.getTaskId() .equals(historicTaskInstance.getId() ) ){
					System.err.println("  备注 :" +historicVariableInstance.getVariableName()
					+ "=" +historicVariableInstance.getValue()	 );
				}
			}
		}
	}
	//获取历史完成流程
	@Test
	public void getHistoryVariable(){
		List<HistoricVariableInstance> list = engine.getHistoryService().createHistoricVariableInstanceQuery()
				.executionId("20001").taskId("25003").list();
		for (HistoricVariableInstance historicVariableInstance : list) {                     
			System.err.println(historicVariableInstance.getTaskId()
			 +" ;"+historicVariableInstance.getVariableName()+" = "+historicVariableInstance.getValue() 
			);
		}
	}
	
	//得到流程的历史处理人和评论
	@Test
	public void getHistoryComment(){
	}
	

}
