package com.lgp.filter;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 拦截请求信息，添加到日志
 *
 * @author lgp
 * @create 2018-04-30 20:09
 */
@Component
@Log4j2
public class MyLogFilter extends OncePerRequestFilter {

    protected static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            MDC.put("user", request.getRemoteUser());
            String query = request.getQueryString() != null ? "?" + request.getQueryString() : "";
            if (request.getMethod().equals(HttpMethod.POST.name())) {
                MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(request);
                log.info("++++++++ 1 filter ++++++++ IP:{}, Method:{}, URI:{} Body:{}", request.getRemoteAddr(), request.getMethod(), request.getRequestURI() + query, multiReadHttpServletRequest.getRequestBody());
                chain.doFilter(multiReadHttpServletRequest, response);
            } else {
                log.info("++++++++ 1 filter ++++++++ IP:{}, Method:{}, URI:{}", request.getRemoteAddr(), request.getMethod(), request.getRequestURI() + query);
                chain.doFilter(request, response);
            }
        } finally {
            MDC.clear();
        }
    }

    /**
     * HttpServletRequest 请求体多读
     */
    class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

        //缓存RequestBody
        private String requestBody;

        public MultiReadHttpServletRequest(HttpServletRequest request) {
            super(request);
            requestBody = "";
            try {
                StringBuilder stringBuilder = new StringBuilder();
                ServletInputStream inputStream = request.getInputStream();
                byte[] bs = new byte[1024];
                int len;
                while ((len = inputStream.read(bs)) != -1) {
                    stringBuilder.append(new String(bs, 0, len));
                }
                requestBody = stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {

            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes());

            return new ServletInputStream() {

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        String getRequestBody() {
            return requestBody.replaceAll("\n", "");
        }
    }
}
