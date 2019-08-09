package com.pine.kasa.config.shiro;

import com.pine.kasa.common.ServiceResult;
import com.pine.kasa.enums.ResultCodeEnum;
import com.pine.kasa.utils.CommonUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class MyPermissionFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyPermissionFilter.class.getCanonicalName());

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        HttpServletRequest httpRequest = ((HttpServletRequest) request);
//        String uri = httpRequest.getRequestURI();
//        logger.info("访问url2：" + httpRequest.getContextPath());
//
//        logger.info("访问url1：" + httpRequest.getServletPath());
//        String tmpUri = uri.substring(1, uri.length());
//        String url = tmpUri.substring(tmpUri.indexOf("/"), tmpUri.length());
        String url = httpRequest.getServletPath();

        logger.info("访问url：" + url);
        //设置过滤.do结尾的
        if (url.endsWith(".do")) {
            return Boolean.TRUE;
        }
        if (url.contains("/loginout")) {
            return Boolean.TRUE;
        }
        //登陆德鲁伊看监控情况
        if (url.contains("/druid")) {
            return Boolean.TRUE;
        }

        Subject subject = getSubject(request, response);
//        Session session = subject.getSession();

//		if(sessionUser==null){
//			todo 如果sessionUser为空这里通过cookie模拟登录
//			logger.info("cookie 登入 CookieToken(httpServletRequest)" );
//			CookieToken token = new CookieToken(httpServletRequest);
//			if(token.getSessionUser() == null)
//				return Boolean.FALSE;
//			subject.login(token);
//		}

        //验证用户是否有访问权限
        if (subject.isPermitted(url)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String url = ((HttpServletRequest) request).getServletPath();
        //先退出
        Subject subject = getSubject(request, response);
//        subject.logout();

        if (subject.isAuthenticated()) {
            //判断是不是Ajax请求
            if (CommonUtils.isAjax(request)) {
                logger.error("当前用户访问\t{}\t权限不足，并且是Ajax请求！", ((HttpServletRequest) request).getRequestURI());
                CommonUtils.out(response, request, ServiceResult.failure(ResultCodeEnum.PERMISSION_DENIED));
            } else {
                logger.error("执行{}权限不足", url);

                CommonUtils.out(response, request, ServiceResult.failure(ResultCodeEnum.PERMISSION_DENIED));
//					throw new UnauthorizedException("访问["+url+"]权限不足,请联系管理员");
            }
        } else {
            logger.error("执行[{}]未登录", url);
            CommonUtils.out(response, request, ServiceResult.failure(ResultCodeEnum.TRY_AGIN));
        }
        /**
         * 保存Request，用来保存当前Request，然后登录后可以跳转到当前浏览的页面。
         * 比如：
         * 我要访问一个URL地址，/admin/index.html，这个页面是要登录。然后要跳转到登录页面，但是登录后要跳转回来到/admin/index.html这个地址，怎么办？
         * 传统的解决方法是变成/user/login.shtml?redirectUrl=/admin/index.html。
         * shiro的解决办法不是这样的。需要：<code>WebUtils.getSavedRequest(request);</code>
         * 							 然后：{@link UserLoginController.submitLogin(...)}中的<code>String url = WebUtils.getSavedRequest(request).getRequestUrl();</code>
         * 如果还有问题，请咨询我。
         */
//        WebUtils.getSavedRequest(request);
        //再重定向  重定向地址
//        WebUtils.issueRedirect(request, response, "/user/unauth");
        //WebUtils.issueRedirect(request, response, ((HttpServletRequest)request).getRequestURL().toString());
        /*if (StringUtils.hasText(ShiroFilterUtils.UNAUTHORIZED)) {//如果有未授权页面跳转过去
            WebUtils.issueRedirect(request, response, ShiroFilterUtils.UNAUTHORIZED);
		} else {//否则返回401未授权状态码
			WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}*/
        return false;
    }


}
