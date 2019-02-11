
package springbootTest;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class Test02 {
	
	@Test
	public void test1() throws Exception{
		//构造一个BufferedReader类来读取文件
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader("D:/aaa.txt"));
		String s = null;
		String[] strArr = {};
        while((s = br.readLine()) != null){	//使用readLine方法，一次读一行
        	strArr = s.split(" - - \\[",2);
        	System.out.println(strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split("\\] \"",2);
        	System.out.println(strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split(" ",2);
        	System.out.println(strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split(" ",2);
        	System.out.println(strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split("\" ",2);
        	System.out.println(strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split(" ",2);
        	System.out.println(strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split(" \"",2);
        	System.out.println(strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split("\" \"",2);
//        	System.out.println(strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split("\" \"",2);
        	System.out.println(strArr[0].equals("_") ? null : strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split("\" \"",2);
//        	System.out.println(strArr[0]);
        	s = strArr[1];
        	
        	strArr = s.split("\"",2);
        	System.out.println(strArr[0]);
        	
        	System.out.println("===================================");
        	System.out.println();
        }
	}
	
	@Test
	public void test2(){
		/*short a = 1;
		a += 2;
		System.out.println(a);
		
		char b = '是';
		System.out.println(b);
		
		final StringBuffer sb = new StringBuffer("我是凡人！");
		sb.append("我是神人！");
		
		sb.toString().equals("");*/
		
		/*
		System.out.println("================="+ Math.round(11.2) + "=======================");  //11
		System.out.println("================="+ Math.round(11.8) + "=======================");  //12
		System.out.println("================="+ Math.round(-11.2) + "======================="); //-11
		System.out.println("================="+ Math.round(-11.5) + "======================="); //-11
		System.out.println("================="+ Math.round(-11.8) + "======================="); //-12
		*/
		
		short i = 54;
		switch(i){
			case 12:
				System.out.println();
				break;
			case 21:
				System.out.println();
				break;
			default:
				System.out.println();
				break;
		}
		
	}
	
}
