package com.spro.service.bonita;

import com.spro.service.BaseService;
import org.bonitasoft.engine.api.*;
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.util.APITypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bonita实现类
 */
@Service
public class BonitaService extends BaseService{

    private Logger logger = LoggerFactory.getLogger(BonitaService.class);
    
    // process variables key
    public final static String PROCESS_VAR_KEY_RPOCESS_NAME = "processName"; //流程名称
    public final static String PROCESS_VAR_KEY_SLIP_ID = "slipId"; //单据id
    public final static String PROCESS_VAR_KEY_SLIP_TYPE = "slipType"; //单据类型（相当于流程编码）
    public final static String PROCESS_VAR_KEY_CREATOR = "creator"; //申请人
    public final static String PROCESS_VAR_KEY_USER_TYPE = "userType"; //根据角色分用户类型（philips  povos）
    public final static String PROCESS_VAR_KEY_OPER_USER_LOGIN_NAME = "operUserLoginName"; //异步信息的操作人

    /**
     * @param userName
     * @return
     * @throws Exception
     */
    public APISession getAPISession(String userName) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("server.url", resourceMap.get("bonita_url"));
        map.put("application.name", resourceMap.get("bonita_app_name"));
        APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, map);
        LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();
        return loginAPI.login(userName, resourceMap.get("bonita_user_password"));
    }

    /**
     * @return
     * @throws Exception
     */
    public APISession getAdminAPISession() throws Exception {
        return getAPISession(resourceMap.get("bonita_admin_user").toString());
    }

    /**
     * @return
     * @throws Exception
     */
    public APISession getAPISession() throws Exception {
        try {
            return getAPISession("loginName");
        } catch (Exception e) {
            return getAdminAPISession();
        }
    }

    /**
     * 根据用户名获取APISession
     * @param userName
     * @return
     * @throws Exception
     */
    public APISession getAPISessionByLoginName(String userName) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("server.url", resourceMap.get("bonita_url"));
        map.put("application.name", resourceMap.get("bonita_app_name"));
        APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, map);
        LoginAPI loginAPI = TenantAPIAccessor.getLoginAPI();
        return loginAPI.login(userName, resourceMap.get("bonita_user_password"));
    }

    /**
     * 启动流程
     * <p>
     * paraMap中必须包含的参数有：
     * PROCESS_VAR_KEY_RPOCESS_NAME（流程名称）
     * PROCESS_VAR_KEY_SLIP_ID（单据ID）
     * PROCESS_VAR_KEY_SLIP_TYPE（单据类型（相当于流程编码））
     * PROCESS_VAR_KEY_CREATOR（创建人（申请人））
     * PROCESS_VAR_KEY_USER_TYPE（用户类型：philips povos）
     * <p>
     * 根据具体流程paraMap中需要包含以下一个或多个参数：
     * PROCESS_VAR_KEY_IS_MANAGER（是否客户经理发起流程：维修站变更申请专用）
     * @param paraMap
     * @return
     * @throws Exception
     */
    public ProcessInstance startProcess(Map paraMap) throws Exception {

        //启动流程必须的几个参数
        String creator = (String) paraMap.get(PROCESS_VAR_KEY_CREATOR);
        String processName = (String) paraMap.get(PROCESS_VAR_KEY_RPOCESS_NAME);
        Long slipId = (Long) paraMap.get(PROCESS_VAR_KEY_SLIP_ID);
        String slipType = (String) paraMap.get(PROCESS_VAR_KEY_SLIP_TYPE);
        String operUserLoginName = (String) paraMap.get(PROCESS_VAR_KEY_OPER_USER_LOGIN_NAME);

        Map<String, Serializable> map = new HashMap<String, Serializable>();
        map.put(PROCESS_VAR_KEY_SLIP_ID, slipId);
        map.put(PROCESS_VAR_KEY_SLIP_TYPE, slipType);
        map.put(PROCESS_VAR_KEY_CREATOR, creator);

        APISession apiSession = getAPISessionByLoginName(operUserLoginName);
        ProcessAPI processAPI = TenantAPIAccessor.getProcessAPI(apiSession);
        Long defId = processAPI.getLatestProcessDefinitionId(processName);
        ProcessInstance pi = processAPI.startProcess(defId, map);
        return pi;
    }

    /**
     * take task
     *
     * @param taskid
     * @return
     * @throws Exception
     */
    public void takeTask(Long taskid, String loginName) throws Exception {
        APISession apiSesssion = getAPISessionByLoginName(loginName);
        IdentityAPI identityAPI = TenantAPIAccessor.getIdentityAPI(apiSesssion);
        ProcessAPI processAPI = TenantAPIAccessor.getProcessAPI(apiSesssion);
        try {
            //processAPI.assignUserTask(taskid, identityAPI.getUserByUserName("loginName").getId());
        } catch (Exception e) {
            //processAPI.assignUserTask(taskid, identityAPI.getUserByUserName(GlobalConstant.ROOT_USER).getId());
        }
    }

    /**
     * @param taskId
     * @return
     * @throws Exception
     */
    public Boolean isGroupTask(Long taskId, String loginName) throws Exception {
        APISession apiSesssion = getAPISessionByLoginName(loginName);
        ProcessAPI processAPI = TenantAPIAccessor.getProcessAPI(apiSesssion);
        HumanTaskInstance task = processAPI.getHumanTaskInstance(taskId);
        // it's a group task if assignee id is zero
        if (task.getAssigneeId() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * approve task and update process variables
     *
     * @param taskId
     * @param processInstanceId
     * @param paraMap
     * @throws Exception
     */
    public void approveTask(Long taskId, Long processInstanceId, Map paraMap) throws Exception {

        if (isGroupTask(taskId, "operUserLoginName")) {
            takeTask(taskId, "operUserLoginName");
        }
        APISession apiSesssion = getAPISessionByLoginName("operUserLoginName");
        ProcessAPI processAPI = TenantAPIAccessor.getProcessAPI(apiSesssion);
        processAPI.updateProcessDataInstances(processInstanceId, paraMap);
        processAPI.executeFlowNode(taskId);
    }



    /**
     * end process
     *
     * @param processId
     * @return
     * @throws Exception
     */
    public void endProcess(Long processId) throws Exception {
        APISession apiSession = getAPISession();
        ProcessAPI processAPI = TenantAPIAccessor.getProcessAPI(apiSession);
        processAPI.cancelProcessInstance(processId);
    }

    /**
     * 获取下个审批人
     *
     * @param rootInstanceId
     * @return
     * @throws Exception
     */
    public List<String> getCurrentApproverByProcessInstanceId (String rootInstanceId) {
        /*Date Time_CurrentApproverByProcessInstanceIdBegin = new Date();

        List<String> currentApprovers = new ArrayList<String>();
        APISession session = getAPISession();
        ProcessAPI processAPI = TenantAPIAccessor.getProcessAPI(session);
        IdentityAPI identityAPI = TenantAPIAccessor.getIdentityAPI(session);
        // query task by root instance id
        SearchOptions taskSearch = new SearchOptionsBuilder(0, 100).filter(HumanTaskInstanceSearchDescriptor.PROCESS_INSTANCE_ID, rootInstanceId).done();
        List<HumanTaskInstance> tasks = processAPI.searchHumanTaskInstances(taskSearch).getResult();

        Date Time_CurrentApproverByProcessInstanceIdOnlyQuery = new Date();

        logger.info("Time_CurrentApproverOnlyQuery >>>>>>> = " + new Long(Time_CurrentApproverByProcessInstanceIdOnlyQuery.getTime() - Time_CurrentApproverByProcessInstanceIdBegin.getTime()) + " ms");

        logger.info("List<HumanTaskInstance.size() = >>>>>>>> " + tasks.size());

        for (HumanTaskInstance task : tasks) {
            Long assigneeId = task.getAssigneeId();
            // if assigneeId ==0 , it's a group task
            if (task.getAssigneeId() == 0) {
                Date Time_SearchUserBegin = new Date();
                Date Time_SearchUserEnd = new Date();

                logger.info("Time_identityAPI.searchUsers >>>>>>> = " + new Long(Time_SearchUserEnd.getTime() - Time_SearchUserBegin.getTime()) + " ms");
                List<User> users = processAPI.getPossibleUsersOfPendingHumanTask(task.getId(), 0, 100);
                logger.info("users.size()= >>>>> " + users.size());
                for (User user : users) {
                    if (user.isEnabled()) {
                        currentApprovers.add(user.getUserName());
                    }
                }

                Date Time_users_process = new Date();

                logger.info("Time_users_process >>>>>>> = " + new Long(Time_users_process.getTime() - Time_SearchUserEnd.getTime()) + " ms");

            } else {
                // it's an either consign task or singer approver task
                currentApprovers.add(identityAPI.getUser(assigneeId).getUserName());
            }

        }

        Date Time_CurrentApproverByProcessInstanceIdEnd = new Date();

        logger.info("Time_CurrentApprover_Process_HumanTaskInstance >>>>>>> = " + new Long(Time_CurrentApproverByProcessInstanceIdEnd.getTime() - Time_CurrentApproverByProcessInstanceIdOnlyQuery.getTime()) + " ms");

        logger.info("Time_CurrentApproverTotal >>>>>>> = " + new Long(Time_CurrentApproverByProcessInstanceIdEnd.getTime() - Time_CurrentApproverByProcessInstanceIdBegin.getTime()) + " ms");
*/
        return null;
    }
}
