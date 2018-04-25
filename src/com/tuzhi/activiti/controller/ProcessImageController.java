package com.tuzhi.activiti.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tuzhi.activiti.util.PropertiesUtil;

/**
 * @author codeZ
 * @date 2018年4月24日 下午2:13:56
 * 
 */
@Controller
public class ProcessImageController {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;
	@Autowired
	private ProcessEngineFactoryBean processEngine;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;

	/**
	 * 读取带跟踪的图片
	 */
	@RequestMapping(value = "/processImage")
	public void test(HttpServletResponse response,String taskId,String processInstanceId) throws Exception {
		// 部署流程，只要是符合BPMN2规范的XML文件，理论上都可以被ACTIVITI部署
		//repositoryService.createDeployment().addClasspathResource("com/pzr/demo/diagrams/MyProcess.bpmn").deploy();
		// 开启流程，myprocess是流程的ID
		//System.out.println("流程【启动】，环节推动到【一次审批】环节");
		//runtimeService.startProcessInstanceByKey("myProcess");
		// 查询历史表中的Task
		//List<Task> task = taskService.createTaskQuery().list();
		//Task task1 = task.get(task.size() - 1);
		// 解开注释就推动到下一环节，对应的在流程图上看到
		//taskService.complete(task1.getId());
		//System.out.println("执行【一次审批】环节，流程推动到【二次审批】环节");
		//task1 = taskService.createTaskQuery().executionId(task1.getExecutionId()).singleResult();

		// 解开注释就推动到下一环节，对应的在流程图上看到
		//taskService.complete(task1.getId());
		//System.out.println("执行【二次审批】环节，流程推动到【结束】环节");

		// processInstanceId
		if(processInstanceId==null||processInstanceId==""){
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			processInstanceId = task.getProcessInstanceId();
		}
		// 获取历史流程实例
		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 获取流程图
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		processEngineConfiguration = processEngine.getProcessEngineConfiguration();
		Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

		ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processInstance.getProcessDefinitionId());

		List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).list();
		// 高亮环节id集合
		List<String> highLightedActivitis = new ArrayList<String>();
		// 高亮线路id集合
		List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitList);

		for (HistoricActivityInstance tempActivity : highLightedActivitList) {
			String activityId = tempActivity.getActivityId();
			highLightedActivitis.add(activityId);
		}

		// 中文显示的是口口口，设置字体就好了
		InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,
				highLightedFlows, "宋体", "宋体", null, null, 1.0);
		// 单独返回流程图，不高亮显示
		// InputStream imageStream =
		// diagramGenerator.generatePngDiagram(bpmnModel);
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	/**
	 * 获取需要高亮的线
	 * 
	 * @param processDefinitionEntity
	 * @param historicActivityInstances
	 * @return
	 */
	private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
			List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
		for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
			ActivityImpl activityImpl = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i).getActivityId());// 得到节点定义的详细信息
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
			ActivityImpl sameActivityImpl1 = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i + 1).getActivityId());
			// 将后面第一个节点放在时间相同节点的集合里
			sameStartTimeNodes.add(sameActivityImpl1);
			for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
				HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
				HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点
				if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
					// 如果第一个节点和第二个节点开始时间相同保存
					ActivityImpl sameActivityImpl2 = processDefinitionEntity
							.findActivity(activityImpl2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				} else {
					// 有不相同跳出循环
					break;
				}
			}
			List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
			for (PvmTransition pvmTransition : pvmTransitions) {
				// 对所有的线进行遍历
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
				// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
				if (sameStartTimeNodes.contains(pvmActivityImpl)) {
					highFlows.add(pvmTransition.getId());
				}
			}
		}
		return highFlows;
	}
	
	@RequestMapping("processDefineImage")
	 public String viewImage(HttpServletResponse resp,String processKey){
		//leaveKey页面写死的,如果不死,需要从配置中获取键为leaveKey
			String proDefId = 
					repositoryService.createProcessDefinitionQuery()
					.processDefinitionKey(PropertiesUtil.getValue(processKey))
					.latestVersion().singleResult().getId();
		
	        InputStream  in = repositoryService.getProcessDiagram(proDefId);//此处方法实际项目应该放在service里面
	        try {
	            OutputStream out = resp.getOutputStream();
	            // 把图片的输入流程写入resp的输出流中
	            byte[] b = new byte[1024];
	            for (int len = -1; (len= in.read(b))!=-1; ) {
	                out.write(b, 0, len);
	            }
	            // 关闭流
	            out.close();
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

}
