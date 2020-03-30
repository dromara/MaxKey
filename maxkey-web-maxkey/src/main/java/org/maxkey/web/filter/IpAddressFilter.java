package org.maxkey.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.maxkey.config.ApplicationConfig;
import org.maxkey.domain.IpAddrFilter;
import org.maxkey.web.WebContext;
import org.maxkey.web.filter.ipaddress.IpAddressCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class IpAddressFilter implements Filter {
    private static final Logger _logger = LoggerFactory.getLogger(IpAddressFilter.class);

    @Autowired
    @Qualifier("applicationConfig")
    private ApplicationConfig applicationConfig;

    boolean whiteList = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (applicationConfig == null) {
            _logger.info("applicationConfig init .");
            applicationConfig = WebApplicationContextUtils.getWebApplicationContext(
                            request.getServletContext())
                                .getBean("applicationConfig", ApplicationConfig.class);
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String ipAddress = WebContext.getRequestIpAddress(httpServletRequest);
        _logger.trace("IpAddress " + ipAddress);
        // 黑名单地址
        if (IpAddressCache.ipAddressBlackListMap.containsKey(ipAddress)) {
            IpAddrFilter ipAddrFilter = IpAddressCache.ipAddressBlackListMap.get(ipAddress);

            _logger.info("You IpAddress in Black List  " + ipAddrFilter);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/accessdeny");
            dispatcher.forward(request, response);
            return;

        }
        // 白名单地址
        if (whiteList && !IpAddressCache.ipAddressWhiteListMap.containsKey(ipAddress)) {
            _logger.info("You IpAddress not in White List  " + ipAddress);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/accessdeny");
            dispatcher.forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
