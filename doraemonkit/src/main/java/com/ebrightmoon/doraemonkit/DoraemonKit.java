package com.ebrightmoon.doraemonkit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.ebrightmoon.doraemonkit.kit.Category;
import com.ebrightmoon.doraemonkit.kit.IKit;
import com.ebrightmoon.doraemonkit.kit.alignruler.AlignRuler;
import com.ebrightmoon.doraemonkit.kit.blockmonitor.BlockMonitorKit;
import com.ebrightmoon.doraemonkit.kit.colorpick.ColorPicker;
import com.ebrightmoon.doraemonkit.kit.crash.CrashCapture;
import com.ebrightmoon.doraemonkit.kit.custom.Custom;
import com.ebrightmoon.doraemonkit.kit.dataclean.DataClean;
import com.ebrightmoon.doraemonkit.kit.fileexplorer.FileExplorer;
import com.ebrightmoon.doraemonkit.kit.gpsmock.GpsMock;
import com.ebrightmoon.doraemonkit.kit.gpsmock.GpsMockManager;
import com.ebrightmoon.doraemonkit.kit.gpsmock.ServiceHookManager;
import com.ebrightmoon.doraemonkit.kit.layoutborder.LayoutBorder;
import com.ebrightmoon.doraemonkit.kit.logInfo.LogInfo;
import com.ebrightmoon.doraemonkit.kit.network.NetworkKit;
import com.ebrightmoon.doraemonkit.kit.parameter.cpu.Cpu;
import com.ebrightmoon.doraemonkit.kit.parameter.frameInfo.FrameInfo;
import com.ebrightmoon.doraemonkit.kit.parameter.ram.Ram;
import com.ebrightmoon.doraemonkit.kit.sysinfo.SysInfo;
import com.ebrightmoon.doraemonkit.kit.temporaryclose.TemporaryClose;
import com.ebrightmoon.doraemonkit.kit.timecounter.TimeCounterKit;
import com.ebrightmoon.doraemonkit.kit.timecounter.instrumentation.HandlerHooker;
import com.ebrightmoon.doraemonkit.kit.viewcheck.ViewChecker;
import com.ebrightmoon.doraemonkit.kit.weaknetwork.WeakNetwork;
import com.ebrightmoon.doraemonkit.kit.webdoor.WebDoor;
import com.ebrightmoon.doraemonkit.kit.webdoor.WebDoorManager;
import com.ebrightmoon.doraemonkit.ui.FloatIconPage;
import com.ebrightmoon.doraemonkit.ui.UniversalActivity;
import com.ebrightmoon.doraemonkit.ui.base.FloatPageManager;
import com.ebrightmoon.doraemonkit.ui.base.PageIntent;
import com.ebrightmoon.doraemonkit.ui.kit.KitItem;
import com.ebrightmoon.doraemonkit.util.DoraemonStatisticsUtil;
import com.ebrightmoon.doraemonkit.util.PermissionUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangweida on 2018/6/22.
 */

public class DoraemonKit {
    private static final String TAG = "DoraemonKit";

    private static SparseArray<List<IKit>> sKitMap = new SparseArray<>();

    private static List<ActivityLifecycleListener> sListeners = new ArrayList<>();

    private static boolean sHasRequestPermission;

    private static boolean sHasInit = false;

    private static WeakReference<Activity> sCurrentResumedActivity;

    private static boolean sShowFloatingIcon = true;

    private static boolean sEnableUpload = true;

    public static void install(final Application app) {
        install(app, null);
    }

    public static void setWebDoorCallback(WebDoorManager.WebDoorCallback callback) {
        WebDoorManager.getInstance().setWebDoorCallback(callback);
    }

    public static void install(final Application app, List<IKit> selfKits) {
        if (sHasInit) {
            if (selfKits != null) {
                List<IKit> biz = sKitMap.get(Category.BIZ);
                if (biz != null) {
                    biz.clear();
                    biz.addAll(selfKits);
                    for (IKit kit : biz) {
                        kit.onAppInit(app);
                    }
                }
            }
            return;
        }
        sHasInit = true;
        HandlerHooker.doHook(app);
        ServiceHookManager.getInstance().install(app);
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            int startedActivityCounts;

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (startedActivityCounts == 0) {
                    FloatPageManager.getInstance().notifyForeground();
                }
                startedActivityCounts++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (PermissionUtil.canDrawOverlays(activity)) {
                    if (sShowFloatingIcon) {
                        showFloatIcon(activity);
                    }
                } else {
                    requestPermission(activity);
                }
                for (ActivityLifecycleListener listener : sListeners) {
                    listener.onActivityResumed(activity);
                }
                sCurrentResumedActivity = new WeakReference<>(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                for (ActivityLifecycleListener listener : sListeners) {
                    listener.onActivityPaused(activity);
                }
                sCurrentResumedActivity = null;
            }

            @Override
            public void onActivityStopped(Activity activity) {
                startedActivityCounts--;
                if (startedActivityCounts == 0) {
                    FloatPageManager.getInstance().notifyBackground();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        sKitMap.clear();
        List<IKit> tool = new ArrayList<>();
        List<IKit> biz = new ArrayList<>();
        List<IKit> ui = new ArrayList<>();
        List<IKit> performance = new ArrayList<>();
        List<IKit> exit = new ArrayList<>();

        tool.add(new SysInfo());
        tool.add(new FileExplorer());
        if (GpsMockManager.getInstance().isMockEnable()) {
            tool.add(new GpsMock());
        }
        tool.add(new WebDoor());
        tool.add(new CrashCapture());
        tool.add(new LogInfo());
        tool.add(new DataClean());
        tool.add(new WeakNetwork());

        performance.add(new FrameInfo());
        performance.add(new Cpu());
        performance.add(new Ram());
        performance.add(new NetworkKit());
        performance.add(new BlockMonitorKit());
        performance.add(new TimeCounterKit());
        performance.add(new Custom());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ui.add(new ColorPicker());
        }

        ui.add(new AlignRuler());
        ui.add(new ViewChecker());
        ui.add(new LayoutBorder());

        exit.add(new TemporaryClose());

        if (selfKits != null && !selfKits.isEmpty()) {
            biz.addAll(selfKits);
        }

        for (IKit kit : biz) {
            kit.onAppInit(app);
        }
        for (IKit kit : performance) {
            kit.onAppInit(app);
        }
        for (IKit kit : tool) {
            kit.onAppInit(app);
        }
        for (IKit kit : ui) {
            kit.onAppInit(app);
        }

        sKitMap.put(Category.BIZ, biz);
        sKitMap.put(Category.PERFORMANCE, performance);
        sKitMap.put(Category.TOOLS, tool);
        sKitMap.put(Category.UI, ui);
        sKitMap.put(Category.CLOSE, exit);

        FloatPageManager.getInstance().init(app);
        if (sEnableUpload) {
            DoraemonStatisticsUtil.uploadUserInfo(app);
        }
    }

    private static void requestPermission(Context context) {
        if (!PermissionUtil.canDrawOverlays(context) && !sHasRequestPermission) {
            Toast.makeText(context, context.getText(R.string.dk_float_permission_toast), Toast.LENGTH_SHORT).show();
            PermissionUtil.requestDrawOverlays(context);
            sHasRequestPermission = true;
        }
    }

    private static void showFloatIcon(Context context) {
        if (context instanceof UniversalActivity) {
            return;
        }
        PageIntent intent = new PageIntent(FloatIconPage.class);
        intent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(intent);
    }

    public static List<IKit> getKitList(int catgory) {
        if (sKitMap.get(catgory) != null) {
            return new ArrayList<>(sKitMap.get(catgory));
        } else {
            return null;
        }
    }

    public static List<KitItem> getKitItems(int catgory) {
        if (sKitMap.get(catgory) != null) {
            List<KitItem> kitItems = new ArrayList<>();
            for (IKit kit : sKitMap.get(catgory)) {
                kitItems.add(new KitItem(kit));
            }
            return kitItems;
        } else {
            return null;
        }
    }

    public interface ActivityLifecycleListener {
        void onActivityResumed(Activity activity);

        void onActivityPaused(Activity activity);
    }

    public static void registerListener(ActivityLifecycleListener listener) {
        sListeners.add(listener);
    }

    public static void unRegisterListener(ActivityLifecycleListener listener) {
        sListeners.remove(listener);
    }

    public static void show() {
        if (!isShow()) {
            showFloatIcon(null);
        }
        sShowFloatingIcon = true;
    }

    public static void hide() {
        FloatPageManager.getInstance().removeAll();
        sShowFloatingIcon = false;
    }

    /**
     * 禁用app信息上传开关，该上传信息只为做DoKit接入量的统计，如果用户需要保护app隐私，可调用该方法进行禁用
     */
    public static void disableUpload() {
        sEnableUpload = false;
    }

    public static boolean isShow() {
        return sShowFloatingIcon;
    }

    public static Activity getCurrentResumedActivity() {
        if (sCurrentResumedActivity != null && sCurrentResumedActivity.get() != null) {
            return sCurrentResumedActivity.get();
        }
        return null;
    }

    public static void enableRequestPermissionSelf() {
        sHasRequestPermission = true;
    }
}
