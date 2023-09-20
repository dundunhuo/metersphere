package io.metersphere.project.controller;


import io.metersphere.project.domain.MessageTask;
import io.metersphere.project.domain.MessageTaskExample;
import io.metersphere.project.dto.MessageTaskDTO;
import io.metersphere.project.mapper.MessageTaskMapper;
import io.metersphere.sdk.constants.SessionConstants;
import io.metersphere.sdk.dto.request.MessageTaskRequest;
import io.metersphere.sdk.util.JSON;
import io.metersphere.system.base.BaseTest;
import io.metersphere.system.controller.handler.ResultHolder;
import io.metersphere.system.notice.constants.NoticeConstants;
import jakarta.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoticeMessageTaskControllerTests extends BaseTest {

    @Resource
    private MessageTaskMapper messageTaskMapper;

    @Test
    @Order(1)
    @Sql(scripts = {"/dml/init_project_message.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void addMessageTaskUserNoExistSuccess() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("project-message-user-1");
        userIds.add("project-message-user-2");
        userIds.add("project-message-user-del");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setRobotId("test_message_robot2");
        messageTaskRequest.setEnable(true);
        messageTaskRequest.setTemplate("发送消息测试");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        Assertions.assertEquals(102001, resultHolder.getCode());
    }

    @Test
    @Order(2)
    public void addMessageTaskCheckUserSuccess() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test-1");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("project-message-user-3");
        userIds.add("project-message-user-4");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setRobotId("test_message_robot2");
        messageTaskRequest.setEnable(true);
        messageTaskRequest.setTemplate("发送消息测试");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        Assertions.assertEquals(100200, resultHolder.getCode());
    }

    @Test
    @Order(3)
    public void addMessageTaskCheckUserFile() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test-1");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("project-message-user-X");
        userIds.add("project-message-user-Y");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setRobotId("test_message_robot2");
        messageTaskRequest.setEnable(true);
        messageTaskRequest.setTemplate("发送消息测试");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        Assertions.assertEquals(100500, resultHolder.getCode());
    }

    @Test
    @Order(4)
    public void addMessageTaskCheckRobotSuccess() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test-1");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("project-message-user-7");
        userIds.add("project-message-user-8");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setEnable(null);
        messageTaskRequest.setTestId("testId");
        messageTaskRequest.setTemplate("发送消息测试");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        Assertions.assertEquals(100200, resultHolder.getCode());
        MessageTaskExample messageTaskExample = new MessageTaskExample();
        messageTaskExample.createCriteria().andTaskTypeEqualTo(NoticeConstants.TaskType.API_DEFINITION_TASK)
                .andEventEqualTo(NoticeConstants.Event.CREATE).andReceiverEqualTo("project-message-user-8").andProjectIdEqualTo("project-message-test-1");
        List<MessageTask> messageTasks = messageTaskMapper.selectByExample(messageTaskExample);
        Assertions.assertEquals("test_message_robot1", messageTasks.get(0).getProjectRobotId());
        Assertions.assertEquals(false, messageTasks.get(0).getEnable());

    }

    @Test
    @Order(5)
    public void addMessageTaskCheckOldExistSuccess() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test-1");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("project-message-user-7");
        userIds.add("project-message-user-8");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setRobotId("test_message_robot1");
        messageTaskRequest.setEnable(true);
        messageTaskRequest.setTemplate("发送消息测试");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        Assertions.assertEquals(100200, resultHolder.getCode());
        MessageTaskExample messageTaskExample = new MessageTaskExample();
        messageTaskExample.createCriteria().andTaskTypeEqualTo(NoticeConstants.TaskType.API_DEFINITION_TASK)
                .andEventEqualTo(NoticeConstants.Event.CREATE).andReceiverEqualTo("project-message-user-8").andProjectIdEqualTo("project-message-test-1");
        List<MessageTask> messageTasks = messageTaskMapper.selectByExample(messageTaskExample);
        Assertions.assertEquals(1, messageTasks.size());
        Assertions.assertEquals(true, messageTasks.get(0).getEnable());
    }

    @Test
    @Order(6)
    public void addMessageTaskCheckProjectExistFail() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test-3");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("project-message-user-7");
        userIds.add("project-message-user-8");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setRobotId("test_message_robot1");
        messageTaskRequest.setEnable(true);
        messageTaskRequest.setTemplate("发送消息测试");
        mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Test
    @Order(7)
    public void getMessageListSuccess() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/notice/message/task/get/project-message-test-1")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        List<MessageTaskDTO> messageTaskDTOList = JSON.parseArray(JSON.toJSONString(resultHolder.getData()), MessageTaskDTO.class);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(messageTaskDTOList));
        Assertions.assertEquals(messageTaskDTOList.get(0).getTaskType(), NoticeConstants.TaskType.API_DEFINITION_TASK);
    }

    @Test
    @Order(8)
    public void getMessageListProjectFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/notice/message/task/get/project-message-test-3")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(9)
    public void getMessageListEmpty() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/notice/message/task/get/project-message-test-2")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        List<MessageTaskDTO> messageTaskDTOList = JSON.parseArray(JSON.toJSONString(resultHolder.getData()), MessageTaskDTO.class);
        Assertions.assertTrue(CollectionUtils.isEmpty(messageTaskDTOList));
    }

    @Test
    @Order(10)
    public void addMessageTaskCheckSpecialUserSuccess() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test-1");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("CREATOR");
        userIds.add("FOLLOW_PEOPLE");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setRobotId("test_message_robot2");
        messageTaskRequest.setEnable(true);
        messageTaskRequest.setTemplate("发送消息测试");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        Assertions.assertEquals(100200, resultHolder.getCode());
    }

    @Test
    @Order(11)
    public void addMessageTaskCheckSpecialUserTwoSuccess() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("CREATOR");
        userIds.add("FOLLOW_PEOPLE");
        userIds.add("project-message-user-5");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setRobotId("test_message_robot2");
        messageTaskRequest.setEnable(true);
        messageTaskRequest.setTemplate("发送消息测试");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        Assertions.assertEquals(100200, resultHolder.getCode());
    }

    @Test
    @Order(12)
    public void addMessageTaskCheckSpecialUserThreeSuccess() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("CREATOR");
        userIds.add("FOLLOW_PEOPLE");
        userIds.add("project-message-user-6");
        userIds.add("project-message-user-del");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setRobotId("test_message_robot2");
        messageTaskRequest.setEnable(false);
        messageTaskRequest.setTemplate("发送消息测试");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        Assertions.assertEquals(102001, resultHolder.getCode());
    }

    @Test
    @Order(13)
    public void closeMessageTaskSuccess() throws Exception {
        MessageTaskRequest messageTaskRequest = new MessageTaskRequest();
        messageTaskRequest.setProjectId("project-message-test");
        messageTaskRequest.setTaskType(NoticeConstants.TaskType.API_DEFINITION_TASK);
        messageTaskRequest.setEvent(NoticeConstants.Event.CREATE);
        List<String> userIds = new ArrayList<>();
        userIds.add("CREATOR");
        userIds.add("FOLLOW_PEOPLE");
        messageTaskRequest.setReceiverIds(userIds);
        messageTaskRequest.setRobotId("test_message_robot2");
        messageTaskRequest.setEnable(false);
        messageTaskRequest.setTemplate("发送消息测试");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/notice/message/task/save")
                        .header(SessionConstants.HEADER_TOKEN, sessionId)
                        .header(SessionConstants.CSRF_TOKEN, csrfToken)
                        .content(JSON.toJSONString(messageTaskRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        ResultHolder resultHolder = JSON.parseObject(contentAsString, ResultHolder.class);
        Assertions.assertEquals(100200, resultHolder.getCode());
    }
}