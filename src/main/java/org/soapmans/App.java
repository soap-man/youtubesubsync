package org.soapmans;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException, IOException {
        //手动登录
        System.out.println( "Hello World!" );
        System.setProperty("webdriver.chrome.driver", "C:\\SoftWare\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get("https://www.youtube.com/channel/UCMUnInmOkrWN4gof9KlhNmQ");
        Thread.sleep(60*1000);
        webDriver.navigate().refresh();

        //读取订阅列表
        File file = new File("C:\\test.json");
        String str = FileUtils.readFileToString(file);
        JSONArray jsonArray = JSON.parseArray(str);
        List<String> subList = new ArrayList<>();
        int size = 0;
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(o));
            String subStr = "https://www.youtube.com/channel/"+jsonObject.getJSONObject("snippet").getJSONObject("resourceId").get("channelId");
            subList.add(subStr);
        }
        System.out.println("总共需要订阅：" + subList.size());
        Collections.reverse(subList);
        for (String s : subList) {
            webDriver.get(s);
            webDriver.findElement(By.id("subscribe-button")).click();
            size++;
            System.out.println("已订阅：" + size);
        }
        System.out.println("订阅完成");
    }
}
