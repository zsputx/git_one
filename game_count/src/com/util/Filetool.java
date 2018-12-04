package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;



public class Filetool {

    public static int  k=0;
	
	
	public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
	

	//д���ļ�
	public  synchronized static void wirtefile(String path ,String content){
		
		
		FileOutputStream fop = null;
		  File file;
		
		
		
		  try {

		   file = new File(path);
		   if (!file.exists()) {
			    file.createNewFile();
			   }
		   fop = new FileOutputStream(file);

		   // if file doesnt exists, then create it
		   

		   // get the content in bytes
		   byte[] contentInBytes = content.getBytes();

		   fop.write(contentInBytes);
		   fop.flush();
		   fop.close();

		   System.out.println("Done");

		  } catch (IOException e) {
		   e.printStackTrace();
		  } finally {
		   try {
		    if (fop != null) {
		     fop.close();
		    }
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		  }
		
	
		
		
		
	}
	
	
	
	//д���ƶ����ļ�����
	public static void writeTo(String path,String contxet) {
		
		//"D:\\springwork\\game_count\\WebContent\\static\\player\\lib\\test0.js"
		//"function a() { alert('�����');}"
		  try {

		   String content =contxet ;

		   File file = new File(path);

		   // if file doesnt exists, then create it
		   if (!file.exists()) {
		    file.createNewFile();
		   }

		   FileWriter fw = new FileWriter(file.getAbsoluteFile());
		   BufferedWriter bw = new BufferedWriter(fw);
		   bw.write(content);
		   bw.close();

		   System.out.println("Done");

		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		 }
		
		
	public static boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                    return false;
            }
 
        }
        catch (Exception e)
        {
            return false;
        }
 
        return true;
    }

	
	public static void main(String[] args) {
		 Filetool.writeTo("D:\\springwork\\game_count\\WebContent\\static\\player\\lib\\test0.js","function a() { alert('�����');}\n����������");
	}
		
	}
	
	

