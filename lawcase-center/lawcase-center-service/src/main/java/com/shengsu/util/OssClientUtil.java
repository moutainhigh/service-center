package com.shengsu.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * 阿里云 OSS工具类
 * @author zxh
 */
@Service
public class OssClientUtil {
 
	public static final Logger logger = LoggerFactory.getLogger(com.shengsu.util.OssClientUtil.class);
	@Value("${oss.endpoint}")
	private String endpoint;
	@Value("${oss.accessKeyId}")
	private String accessKeyId;
	@Value("${oss.accessKeySecret}")
	private String accessKeySecret;
	@Value("${oss.bucketName}")
	private String bucketName;
	// 文件存储目录
	@Value("${oss.filedir}")
	private String filedir;
 
	private OSSClient ossClient;
 
	/**
	 * 初始化
	 */
	public void init() {
		ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	}
 
	/**
	 * 销毁
	 */
	public void destory() {
		ossClient.shutdown();
	}
 

	/**
	 * 上传到OSS服务器 如果同名文件会覆盖服务器上的
	 *
	 * @param instream
	 *            文件流
	 * @param fileName
	 *            文件名称 包括后缀名
	 * @return 出错返回"" ,唯一MD5数字签名
	 */
	public String uploadFile2OSS(InputStream instream, String fileName) {
		String ret = "";
		try {
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(instream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// 上传文件
			init();
			PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
			ret = putResult.getETag();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			destory();
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	/**
	 * 上传到OSS服务器 如果同名文件会覆盖服务器上的
	 *
	 * @param instream
	 *            文件流
	 * @param fileName
	 *            文件名称 包括后缀名
	 * @return 出错返回"" ,唯一MD5数字签名
	 */
	public String uploadFile2OSS(InputStream instream, String fileName,String contentType) {
		String ret = "";
		try {
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(instream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(contentType);
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// 上传文件
			init();
			PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
			ret = putResult.getETag();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			destory();
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
 
	/**
	 * Description: 判断OSS服务文件上传时文件的contentType
	 *
	 * @param filenameExtension
	 *            文件后缀
	 * @return String
	 */
	public static String getcontentType(String filenameExtension) {
		if (filenameExtension.equalsIgnoreCase("bmp")) {
			return "image/bmp";
		}
		if (filenameExtension.equalsIgnoreCase("gif")) {
			return "image/gif";
		}
		if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
				|| filenameExtension.equalsIgnoreCase("png")) {
			return "image/jpeg";
		}
		if (filenameExtension.equalsIgnoreCase("html")) {
			return "text/html";
		}
		if (filenameExtension.equalsIgnoreCase("txt")) {
			return "text/plain";
		}
		if (filenameExtension.equalsIgnoreCase("js")) {
			return "application/x-javascript";
		}
		if (filenameExtension.equalsIgnoreCase("vsd")) {
			return "application/vnd.visio";
		}
		if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
			return "application/msword";
		}
		if (filenameExtension.equalsIgnoreCase("xml")) {
			return "text/xml";
		}
		return "image/jpeg";
	}
 
	/**
	 * 获得url链接（默认路径）
	 *
	 * @param key
	 * @return
	 */
	public String getUrl(String key) {
		// 设置URL过期时间为10年 3600l* 1000*24*365*10
		Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
		// 生成URL
		init();
		URL url = ossClient.generatePresignedUrl(bucketName, filedir+key, expiration);
		destory();
		if (url != null) {
			return url.toString();
		}
		return null;
	}
	/**
	 * 获得url链接(指定路径）
	 *
	 * @param key
	 * @return
	 */
	public String getUrl(String filedir,String key) {
		// 设置URL过期时间为10年 3600l* 1000*24*365*10
		Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
		// 生成URL
		init();
		URL url = ossClient.generatePresignedUrl(bucketName, filedir+key, expiration);
		destory();
		if (url != null) {
			return url.toString();
		}
		return null;
	}

	/**
	 * 删除OSS对象
	 * @param key
	 */
	public void deleteOssFile(String key){
		init();
		ossClient.deleteObject(bucketName, filedir+key);
		destory();
	}

	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			filename = filename.substring(dot+1,filename.length());
		}
		return filename;
	}

    /**
     * 下载oss资源（目前废弃改为不经过服务器前端直接下载）
     */
	 public void ossDownLoad(){
         HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
         HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		 long size=0;
        try {
        	String osskey = filedir+request.getParameter("ossKey");
            String fileName=request.getParameter("fileName");
            // 从阿里云进行下载
            init();
            OSSObject ossObject = ossClient.getObject(bucketName,osskey);//bucketName需要自己设置
            // 已缓冲的方式从字符输入流中读取文本，缓冲各个字符，从而提供字符、数组和行的高效读取
            InputStream inputStream = ossObject.getObjectContent();
            size = ossObject.getObjectMetadata().getContentLength();
            //缓冲文件输出流
            BufferedOutputStream outputStream=new BufferedOutputStream(response.getOutputStream());
            //通知浏览器以附件形式下载
            // response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
			// 为防止 文件名出现乱码
            String contentType =ossObject.getObjectMetadata().getContentType();
			response.setContentType(contentType);
			final String userAgent = request.getHeader("USER-AGENT");
			if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
			    fileName = URLEncoder.encode(fileName,"UTF-8");
			}else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
			    fileName = new String(fileName.getBytes(), "ISO8859-1");
			}else{
			    fileName = URLEncoder.encode(fileName,"UTF-8");//其他浏览器
			}
			response.addHeader("Content-Disposition", "attachment;filename=" +fileName);//这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
			response.setHeader("Content-Length", ""+size);
			byte[] car = new byte[1024];
	                int L;
	                while((L = inputStream.read(car)) != -1){
	                    if (car.length!=0){
	                        outputStream.write(car, 0,L);
	                    }
	                }
	            if(outputStream!=null){
	                outputStream.flush();
	                outputStream.close();
	            }
	            destory();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (OSSException e){
	        }
	 }

    /**
     * oss资源打包下载
     * @return
     */
	 public void zipFilesDown(){
         HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
         HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		 JSONArray jsonArray = JSON.parseArray(request.getParameter("fileInfo"));
		 String zipFileName = request.getParameter("zipFileName");
		 long totalSize = 0;
		    try {
		        // 初始化
		        init();
		        // 创建临时文件
		        File zipFile = File.createTempFile("temp", ".zip");
		        FileOutputStream fos = new FileOutputStream(zipFile);
		        /**
		         * 作用是为任何OutputStream产生校验和
		         * 第一个参数是制定产生校验和的输出流，第二个参数是指定Checksum的类型 （Adler32（较快）和CRC32两种）
		         */
		        CheckedOutputStream csum = new CheckedOutputStream(fos, new Adler32());
		        // 用于将数据压缩成Zip文件格式
		        ZipOutputStream zos = new ZipOutputStream(csum);
		        List<String> listFileName = makeListFileName(jsonArray);
		        for (int i = 0; i < listFileName.size(); i++) {
		            // 获取Object，返回结果为OSSObject对象
		            OSSObject ossObject = ossClient.getObject(bucketName, filedir+ JSON.parseObject(jsonArray.get(i).toString()).get("ossKey").toString());
		            // 读去Object内容  返回
		            InputStream inputStream = ossObject.getObjectContent();
		            // 对于每一个要被存放到压缩包的文件，都必须调用ZipOutputStream对象的putNextEntry()方法，确保压缩包里面文件不同名
		            String fileName = listFileName.get(i);
		            zos.putNextEntry(new ZipEntry(fileName));
		            int bytesRead = 0;
		            // 向压缩文件中输出数据
		            while((bytesRead=inputStream.read())!=-1){
		                zos.write(bytesRead);
		            }
		            inputStream.close();
		            zos.closeEntry(); // 当前文件写完，定位为写入下一条项目
		        }
		        zos.close();
		        String header = request.getHeader("User-Agent").toUpperCase();
		        if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
		            zipFileName = URLEncoder.encode(zipFileName, "utf-8");
		            zipFileName = zipFileName.replace("+", "%20");    //IE下载文件名空格变+号问题
		        } else {
		            zipFileName = new String(zipFileName.getBytes(), "ISO8859-1");
		        }
		        totalSize=zipFile.length();
		       // response.reset();
		        response.setContentType("application/octet-stream; charset=utf-8");
		        //response.setHeader("Location", zipFileName);
		        response.setHeader("Cache-Control", "max-age=0");
		        response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName);
		        response.setHeader("Content-Length", ""+totalSize);
	
		        FileInputStream fis = new FileInputStream(zipFile);
		        BufferedInputStream buff = new BufferedInputStream(fis);
		        BufferedOutputStream out=new BufferedOutputStream(response.getOutputStream());
		        byte[] car=new byte[1024];
		        int l=0;
		        while (l < zipFile.length()) {
		            int j = buff.read(car, 0, 1024);
		            l += j;
		            out.write(car, 0, j);
		        }
		        // 关闭流
		        fis.close();
		        buff.close();
		        out.close();
		        // 删除临时文件
		        zipFile.delete();
		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally{
		    	destory();
		    }
	}
	 /**
	  * 包装有集合中相同文件名增加序号排序
	  * @param jsonArray
	  * @return
	  */
	private List<String> makeListFileName(JSONArray jsonArray) {
		List<String> listFileName = new ArrayList<>();
		List<String> result = new ArrayList<>();
		 for (int i = 0; i < jsonArray.size(); i++) {
			 JSONObject jsonObject = JSON.parseObject(jsonArray.get(i).toString());
	         String fileName = jsonObject.get("fileName").toString();
	         listFileName.add(fileName);
	        }
		List<String> listDuplicateRemoval  = new ArrayList<String>();
		List<String> listRemoval = new ArrayList<String>();
		for (int i=0; i<listFileName.size(); i++) {
		  if(!listDuplicateRemoval.contains(listFileName.get(i))) {
			  listDuplicateRemoval.add(listFileName.get(i));
		  }else {
			  listRemoval.add(listFileName.get(i));
		  	}
		}
		for (String duplicateRemoval : listDuplicateRemoval) {
			result.add(duplicateRemoval);
			int i = 1;
			for (String removal : listRemoval) {
				if (duplicateRemoval.equals(removal)) {
					String string = removal.substring(0,removal.lastIndexOf('.'))+"_"+i+++ removal.substring(removal.lastIndexOf('.'),removal.length());
					result.add(string);
				}
			}
		}
		return result;
		
	}

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        //String fileurl = "c:/unintall.log";
		/*String url = "c:/1.jpg";
		String filename = UUID.randomUUID().toString()+".log";
		OssClientUtil ossclient = new OssClientUtil();
		try {
			ossclient.uploadImg2Oss(url);
			//ossclient.uploadFile2OSS(new FileInputStream(new File(fileurl)), filename);
			ossclient.deleteOssFile("test/1.jpg");
			ossclient.deleteOssFile("test/8c86c62d-d899-4a70-8a57-0b72081ab825.log1");
		} catch (ImgException e) {
			e.printStackTrace();
		}
		System.out.println(ossclient.getUrl("test/"+filename));
		System.out.println(ossclient.getUrl("test/1.jpg"));*/
        getFileNameNoEx("123456.png");
    }
}
