package com.newcoder.toutiao;

import com.newcoder.toutiao.service.LikeService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * created by zsj in 13:42 2018/6/2
 * description:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
public class LikeServiceTests {

    //@BeforeClass -> [@Before -> @test -> @after]... ->@AfterClass
    /**
     * 跑测试用例
     */

    @Autowired
    LikeService likeService;

    @Test
    public void testLike() {
        likeService.getLikeStatus(123,1,1);
        Assert.assertEquals(0, likeService.getLikeStatus(123,1,1));
    }
    /**
     * 跑测试用例
     */
    @Test
    public void testDisLike() {
        likeService.getLikeStatus(123,1,1);
        Assert.assertEquals(0, likeService.getLikeStatus(123,1,1));
    }

    /**
     * 初始化数据
     */
    @Before
    public void setUp() {
        System.out.println("setUp");
    }

    /**
     * 清理数据
     */
    @After
    public void tearDown() {
        System.out.println("tearDown");
    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("beforeClass");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass");
    }
}
