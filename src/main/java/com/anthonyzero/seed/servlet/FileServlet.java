/**
 * 
 */
package com.anthonyzero.seed.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import com.anthonyzero.seed.modules.sys.controller.FileController;

/**
 * 文件管理
 * @author pingjin create 2018年7月13日
 *
 */
@WebServlet(urlPatterns = { "/image/*", "/dlfile/*" }, description = "图片、文件")
public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文件相对路径 
		String fileName = getRequestFileName(request);

		File file = new File(FileController.FILE_PATH, fileName);

		if (!file.exists() || !file.isFile()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Map<String, String> mimes = FileController.getMimeMappings();
		String name = file.getName();
		int pos = name.lastIndexOf(".");
		String suffix = pos >= 0 ? name.substring(pos) : "";
		suffix = suffix.toLowerCase();

		String contentType = mimes.get(suffix);
		if (contentType == null) {
			contentType = mimes.get("*");
		}

		response.setContentType(contentType);

		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			os = response.getOutputStream();
			int i = bis.read(buffer);
			while (i != -1) {
				os.write(buffer, 0, i);
				i = bis.read(buffer);
			}
			os.flush();
			return;
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

			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}

	protected String getRequestFileName(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();

		if (StringUtils.hasText(contextPath)) {
			uri = uri.substring(uri.indexOf(contextPath) + contextPath.length());
		}

		if (StringUtils.hasText(servletPath)) {
			uri = uri.substring(uri.indexOf(servletPath) + servletPath.length());
		}

		if (uri.startsWith("/")) {
			uri = uri.substring(1);
		}

		return uri;
	}

}
