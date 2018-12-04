package com.controller;

import java.io.File;
import java.io.IOException;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.entity.lookInfo;
import com.service.lookInfoService;
import com.util.Filetool;
import com.util.result;

@Controller
public class webController {

	private lookInfoService ls;

	public lookInfoService getLs() {
		return ls;
	}

	@Resource(name = "lookInfoServiceImpl")
	public void setLs(lookInfoService ls) {
		this.ls = ls;
	}

	@RequestMapping("/a.do")
	public String index() {
		return "lg";
	}

	@RequestMapping(value = "/add.do", method = RequestMethod.POST)
	@ResponseBody
	public result executeq(String username) {

		result res = new result();
		lookInfo ld = new lookInfo();
		ld.setAdName(username);
		ld.setGameName("good");
		ld.setLookerName("wo");
		ld.setLookTime("五月三日");
		ls.save(ld);
		res.setMsg("ok");
		res.setStatus(1);
		return res;
	}

	

	@RequestMapping(value = "/text.do", method = RequestMethod.POST)
	@ResponseBody
	public result execut(String text,String haha) {

		result res = new result();
	    System.out.println(text);
	    System.out.println(haha);
		res.setMsg("ok");
		res.setStatus(1);
		res.setData(text);
		return res;
	}

	
	
	/**
	 * 客户端是对象的请求
	 * @param d
	 * @return
	 */
	@RequestMapping(value = "/json.do", method = RequestMethod.POST)
	@ResponseBody
	public result execute(lookInfo d) {

		result res = new result();
		System.out.println(d);
		res.setData(d);
		res.setMsg("ok");
		res.setStatus(1);
		return res;
	}

	@RequestMapping(value = "/jsonk.do", method = RequestMethod.POST)
	@ResponseBody
	public result executek(lookInfo d, HttpServletRequest rd) {

		// rd.
		result res = new result();
		System.out.println(d);
		res.setData(d);
		res.setMsg("ok");
		res.setStatus(1);
		return res;
	}

	@RequestMapping(value = "/make.do", method = RequestMethod.POST)
	@ResponseBody
	public result executemake(HttpServletRequest res, String szbd) {
		String kk = "";
		Filetool.k++;
		result r = new result();
		System.out.println(szbd.toString());
		if (szbd != null && szbd != "") {
			kk = res.getRealPath("/") + "\\" + Filetool.k + "jsfile.js";

			Filetool.wirtefile(kk, szbd);
		}
		r.setMsg("" + Filetool.k);
		r.setStatus(1);
		return r;
	}

	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	@ResponseBody
	public result executew(HttpServletRequest res, String szbd) {
		String kk = "";
		System.out.println(szbd);
		Filetool.k++;
		result r = new result();
		System.out.println(szbd.toString());
		if (szbd != null && szbd != "") {
			kk = "D:\\springwork\\game_count\\WebContent\\static\\player\\lib\\" + "jsfile.js";
			Filetool.wirtefile(kk, szbd);
		}
		r.setMsg("" + Filetool.k);
		r.setStatus(1);
		return r;
	}

	// 下载js
	@ResponseBody
	@RequestMapping(value = "/download_js.do", method = RequestMethod.GET)
	public HttpEntity<byte[]> fileDownload(HttpServletRequest request, HttpServletResponse response, String k) {

		try {

			// String path=request.getRealPath("/")+"\\"+k+"jsfile.js";

			String path = "D:\\springwork\\game_count\\WebContent\\static\\player\\lib\\" + Filetool.k + "jsfile.js";
			// System.out.println(path);
			File file = new File(path);
			HttpHeaders headers = new HttpHeaders();
			String fileName = new String("jsfile.js".getBytes("UTF-8"), "iso-8859-1");//
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (IOException e) {
			System.out.println("file not find!");
			e.printStackTrace();
		}
		return null;

	}

   
	
    /**
     * 上传图片
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
	@ResponseBody
	@RequestMapping("/upload_pic.do")
	public result fileUpload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request)
			throws IOException {
		long startTime = System.currentTimeMillis();
		System.out.println("fileName：" + file.getOriginalFilename());
		result rs = new result();
		String path = "D:\\springwork\\game_count\\WebContent\\static\\res\\" + file.getOriginalFilename();
		if (!Filetool.fileIsExists(path)) {
			File newFile = new File(path);
			System.out.println(path);

			// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
			file.transferTo(newFile);
			long endTime = System.currentTimeMillis();
			System.out.println("写入文件运行时间：" + String.valueOf(endTime - startTime) + "ms");

			rs.setStatus(200);
			rs.setMsg("ok");
			return rs;
		} else {

			rs.setStatus(200);
			rs.setMsg("have");
			return rs;
		}

	}

}
