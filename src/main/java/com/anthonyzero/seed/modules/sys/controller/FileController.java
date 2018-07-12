/**
 * 
 */
package com.anthonyzero.seed.modules.sys.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.anthonyzero.seed.common.core.Result;
import com.anthonyzero.seed.common.utils.DateUtils;
import com.anthonyzero.seed.modules.sys.dto.FileInfo;
import com.anthonyzero.seed.modules.sys.service.SmFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


/**
 * 文件上传下载
 * @author pingjin create 2018年7月12日
 *
 */
@Api(tags = "文件相关")
@RestController
@RequestMapping("/file")
public class FileController {
	protected final static Logger logger = LoggerFactory.getLogger(FileController.class);
	
	public static String FILE_PATH; // 文件存储路径
	@Value("${file.filepath}")
	private String filePath;
	@Autowired
	private SmFileService smfileService;

	@PostConstruct
	public void init() {
		FILE_PATH = this.filePath;
		logger.debug("-----> FILE_PATH : " + FILE_PATH);
	}

	/**
	 * 获取文件路径
	 * 
	 * @param fileId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/getFilePath")
	@ApiOperation(value = "获取文件路径")
	@ApiImplicitParam(name = "fileId", value = "文件Id", required = true, dataType = "long")
	public Result getFilePath(long fileId) {
		String path = smfileService.getFilePath(fileId);
		Result result = new Result();
		result.setCode(HttpStatus.OK.value());
		result.setData(path);
		return result;
	}

	/**
	 * 获取MINE文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getMimeMappings() {
		Map<String, String> mimes = new HashMap<String, String>();

		mimes.put(".txt", "text/plain");
		mimes.put(".jpg", "image/JPEG");
		mimes.put(".png", "image/PNG");
		mimes.put(".gif", "image/GIF");
		mimes.put(".doc", "application/msword");
		mimes.put(".xls", "application/vnd.ms-excel");
		mimes.put(".xlsx", "application/vnd.ms-excel");
		mimes.put(".pdf", "application/pdf");
		mimes.put(".zip", "application/zip");
		mimes.put(".rar", "application/octet-stream");
		mimes.put(".apk", "application/vnd.android.package-archive");
		mimes.put("*", "application/octet-stream");

		return mimes;
	}

	/**
	 * 根据文件ID获取图片
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/downloadImage")
	public Result downloadImage(Long fileId, HttpServletRequest request, HttpServletResponse response) {
		return downloadFile(fileId, null, null, request, response);
	}
	
	/**
	 * 根据文件ID下载文件
	 * 
	 * @param fileId
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping("/downloadFile")
	@ApiOperation(value = "根据文件Id下载文件")
	@ApiImplicitParam(name = "fileId", value = "文件Id", required = true, dataType = "long")
	public Result downloadFileById(Long fileId, HttpServletRequest request, HttpServletResponse response) {
		return downloadFile(fileId, null, null, request, response);
	}

	/**
	 * 文件下载
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/download")
	public Result downloadFile(Long fileId, String fileName, Integer down, HttpServletRequest request,
			HttpServletResponse response) {
		if (fileId != null && fileId > 0) {
			fileName = smfileService.getFilePath(fileId);
		}
		File file = new File(FILE_PATH, fileName);
		if (!file.exists() || file.isDirectory() || file.isHidden()) {
			return Result.error("file not find.");
		}

		Map<String, String> mimes = getMimeMappings();
		String name = file.getName();
		int pos = name.lastIndexOf(".");
		String suffix = pos >= 0 ? name.substring(pos) : "";
		suffix = suffix.toLowerCase();

		String contentType = mimes.get(suffix);
		if (contentType == null) {
			contentType = mimes.get("*");
		}

		response.setContentType(contentType);

		if (down != null && down > 0) {
			response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());// 设置文件名
		}

		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			OutputStream os = response.getOutputStream();
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}

		return Result.success();
	}

	private static String trimeStorePath(String path) {
		String storePath = StringUtils.hasText(path) ? path : "";
		if (storePath.startsWith("/")) {
			storePath = storePath.substring(1);
		}

		if (storePath.endsWith("/")) {
			storePath = storePath.substring(0, storePath.length() - 1);
		}

		return storePath;
	}

	private String getFileSuffix(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos >= 0) {
			return fileName.substring(pos);
		}

		return "";
	}

	/**
	 * 多文件上传，form中的字段名如果没有设置，则默认为file, 文件上传后会直接存储
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Result handleFileUpload(String path, String formName, HttpServletRequest request) {
		// 路径
		File dirFile = new File(FILE_PATH);
		String storePath = trimeStorePath(path) + "/" + DateUtils.format(new Date(), "yyyyMMdd");
		storePath = trimeStorePath(storePath);

		File parentFile = StringUtils.hasText(storePath) ? new File(dirFile, storePath) : dirFile;

		formName = StringUtils.hasText(formName) ? formName : "file";

		List<FileInfo> succFiles = new ArrayList<>();

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(formName);
		MultipartFile file = null;
		for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();

				String storeName = UUID.randomUUID().toString() + getFileSuffix(fileName);

				File upFile = new File(parentFile, storeName);
				if (!upFile.getParentFile().exists()) {
					upFile.getParentFile().mkdirs();
				}

				// 获取上传的路径
				String dirPath = dirFile.getAbsolutePath();
				String upPath = upFile.getAbsolutePath();

				String relaPath = upPath.substring(dirPath.length());
				relaPath = relaPath.replaceAll("\\\\", "/");
				if (relaPath.startsWith("/")) {
					relaPath = relaPath.substring(1);
				}

				// 保存
				BufferedOutputStream stream = null;
				try {
					byte[] bytes = file.getBytes();
					stream = new BufferedOutputStream(new FileOutputStream(upFile));
					stream.write(bytes);

					try {
						stream.flush();
						stream.close();
					} catch (Exception e) {
					}

					FileInfo fileInfo = smfileService.saveFile(fileName, relaPath, upFile);

					// 上传 成功的路径
					succFiles.add(fileInfo);
				} catch (Exception e) {
					return Result.error("failed to upload " + i + " => " + e.getMessage());
				} finally {
					if (stream != null) {
						try {
							stream.flush();
							stream.close();
						} catch (Exception e) {
						}
					}
				}
			} else {
				return Result.error("failed to upload " + i + " because the file was empty.");
			}
		}
		return Result.success(succFiles);
	}

}
