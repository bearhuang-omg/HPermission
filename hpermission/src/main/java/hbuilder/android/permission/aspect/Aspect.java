package hbuilder.android.permission.aspect;

import android.app.Service;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import hbuilder.android.permission.PermissionCallBack;

/**
 * Created by huangbei on 19-3-15.
 */
@org.aspectj.lang.annotation.Aspect
public class Aspect {

    @Around("execution(@hbuilder.android.permission.aspect.Permission * *(..))")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint) {
        try {
            //1.获取注解
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            Permission annotation = method.getAnnotation(Permission.class);
            //2.获取注解参数
            final String[] permissions = annotation.permissions();
            final int[] rationales = annotation.reject();
            final int[] rejects = annotation.deadReject();
            final List<String> permissionList = Arrays.asList(permissions);

            //3.获取上下文
            Object object = joinPoint.getThis();
            Context context = null;
            if (object instanceof FragmentActivity) {
                context = (FragmentActivity) object;
            } else if (object instanceof Fragment) {
                context = ((Fragment) object).getContext();
            } else if (object instanceof Service) {
                context = (Service) object;
            }

            //4.申请权限
            final Context finalContext = context;
            AspectPermission.with(context)
                    .permissions(permissions)
                    .callBack(new PermissionCallBack() {
                        @Override
                        public void granted() {
                            try {
                                // 权限申请通过，执行原方法
                                joinPoint.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }

                        @Override
                        public void reject(String permission) {
                            Toast.makeText(finalContext,"拒绝了",Toast.LENGTH_SHORT).show();
                            if(AspectPermission.aspectPermission() != null){
                                AspectPermission.aspectPermission().reject(permission);
                            }
                        }

                        @Override
                        public void deadReject(String permission) {
                            Toast.makeText(finalContext,"完全拒绝了",Toast.LENGTH_SHORT).show();
                            if(AspectPermission.aspectPermission() != null){
                                AspectPermission.aspectPermission().deadReject(permission);
                            }
                        }
                    }).request();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
