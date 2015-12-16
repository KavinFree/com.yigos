package com.yigos.framework.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogInterceptor {

	@Before(value="@annotation(com.yigos.framework.annotation.MethodLog)")
	//@Before(value="execution(public* com.yigos.auth.user.controller..*.update*(..))")
	public void methodCachePointcut(JoinPoint joinPoint) {
		joinPoint.getArgs();
		
	}
	//@Before("methodCachePointcut()")
	public void around(JoinPoint point) throws Throwable {
		String packages = point.getThis().getClass().getName();
		if (packages.indexOf("$$EnhancerByCGLIB$$") > -1) { // 如果是CGLIB动态生成的类
			try {
				packages = packages.substring(0, packages.indexOf("$$"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		try {
//			Object[] method_param = point.getArgs(); // 获取方法参数
		} catch (Exception e) {
			throw e;
		}
	}

	// 获取方法的中文备注____用于记录用户的操作日志描述
	/*public static String getMthodRemark(ProceedingJoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();

		Class<?> targetClass = Class.forName(targetName);
		Method[] method = targetClass.getMethods();
		String methode = "";
		for (Method m : method) {
			if (m.getName().equals(methodName)) {
				Class<?>[] tmpCs = m.getParameterTypes();
				if (tmpCs.length == arguments.length) {
					MethodLog methodCache = m.getAnnotation(MethodLog.class);
					if (methodCache != null) {
						methode = methodCache.remark();
					}
					break;
				}
			}
		}
		return methode;
	}*/

}