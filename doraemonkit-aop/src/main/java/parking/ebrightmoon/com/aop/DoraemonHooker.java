package parking.ebrightmoon.com.aop;

import android.app.Application;

import com.ebrightmoon.doraemonkit.DoraemonKit;
import com.ebrightmoon.doraemonkit.kit.timecounter.TimeCounterManager;
import com.ebrightmoon.doraemonkit.util.LogHelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 通用的hooker
 */
@Aspect
public class DoraemonHooker {
    public static final String TAG = "DoraemonHooker";

    @Around("execution(* android.app.Application.onCreate(..))")
    public void install(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LogHelper.d(TAG, "hook application.onCreate start");
        TimeCounterManager.get().onAppCreateStart();
        proceedingJoinPoint.proceed();
        Application app = (Application) proceedingJoinPoint.getTarget();
        DoraemonKit.install(app);
        TimeCounterManager.get().onAppCreateEnd();
        LogHelper.d(TAG, "hook application.onCreate end");
    }
}