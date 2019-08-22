package com.spro;

import com.spro.util.FTPUtils;
import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.FileInputStream;

@EnableScheduling
@SpringBootApplication
//@ComponentScan(basePackages = {"com.controller","com.spro.service"})
@MapperScan(basePackages = {"com.spro.dao"})
@Configuration
@ComponentScan(useDefaultFilters = true)
public class SproApplicationTests {

	@Test
	public void uploadFile() {

	    File file = new File("D:\\1234.jpg");
	    try{
            FTPUtils.getInstance().uploadFile("/www/","1346.jpg",new FileInputStream(file));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSendEmail() throws Exception{
       // EmailSender.sendMail("yahui.xie@erry.com",null,null,"第一封邮件","邮件主题",null);
    }

    @Test
    public void process(){
	    int i=1000;
	    i = ++i/3;
        System.out.println("i="+i++);
        System.out.println("i="+i);
    }

    @Test
    public void exec(){
        int[] array={1,3,9,8,76,3,0,5,9};
        int tmp=0; int pwd=1;
        {for(int i=0;i<array.length;i++)
        { for(int j=0;j<array.length-i-1;j++){
            if(array[j]>array[j+1]){
                pwd=0; tmp=array[j]; array[j]=array[j+1]; array[j+1]=tmp; } } if(pwd==1){ break; }
        }

            System.out.println("排序之后的array-->" + array);

        int[] sz = array;
                int begin = 0;
                int end = sz.length -1;
                while(begin<end){ while(sz[begin] % 2 !=0 && begin <end){ begin++; }
                    while(sz[end] % 2 ==0 && begin <end){ end--; }
                    int temp = sz[begin]; sz[begin] = sz[end]; sz[end] = temp; }
                for(int i=0;i<sz.length;i++){ System.out.print(sz[i]+" "); }
            }
        }
}

