package com.zexing.spittr.web;

import com.zexing.spittr.bean.Spittle;
import com.zexing.spittr.repository.SpittleRepository;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SpittleControllerTest {

    @Test
    public void shouldShowRecentSpittles() throws Exception {

        List<Spittle> expectedSpittles = createSpittles(20);

        //这里不关心findSpittles()方法的实现原理，只需要其返回结果，所以用mock
        SpittleRepository mockSpittleRepository = mock(SpittleRepository.class);
        when(mockSpittleRepository.findSpittles(anyLong(),anyInt())).thenReturn(expectedSpittles);

        SpittleController spittleController = new SpittleController(mockSpittleRepository);

        //mock springMVC
        MockMvc mockMvc = standaloneSetup(spittleController).setSingleView(new InternalResourceView("/WEB-INF/views/spittle.jsp"))
                .build();

        //发起请求
        mockMvc.perform(get("/spittles"))
                .andExpect(view().name("spittles"))     //断言视图的名称为spittles
                .andExpect(model().attributeExists("spittleList"))  //断言模型中包含名为spittleList的属性
                .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())))  //断言spittleList的属性包含期望的expectedSpittles
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void shouldShowPagedSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittles(50);
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findSpittles(238900L, 50))      //预期的max 和count 参数
                .thenReturn(expectedSpittles);

        SpittleController controller = new SpittleController(mockRepository);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();

        mockMvc.perform(get("/spittles?max=238900&count=50"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"))
                .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void testSpittle() throws Exception {

        Spittle expectedSpittle = new Spittle("hello",new Date());
        SpittleRepository mockSpittleRepository = mock(SpittleRepository.class);

        when(mockSpittleRepository.findOneSpittle(12345)).thenReturn(expectedSpittle);

        SpittleController controller = new SpittleController(mockSpittleRepository);
        MockMvc mockMvc = standaloneSetup(controller).build();

        ResultActions resultActions = mockMvc.perform(get("/spittles/12345"));
        resultActions.andExpect(view().name("spittle"));
        resultActions.andExpect(model().attributeExists("spittle"));
        resultActions.andDo(MockMvcResultHandlers.print()).andReturn();
    }

    private List<Spittle> createSpittles(int count) {

        List<Spittle> spittles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Spittle spittle = new Spittle("Spittle"+i,new Date());
            spittles.add(spittle);
        }
        return spittles;
    }


}