package utils;

/**
 * LOG信息打印工具类，需要和代码提示模版配套使用 java专用
 * 
 * @author Administrator
 */
public class LogUtil {

	public static final String TAG = "LogUtil";

	public static final boolean debug = true; // ture,开启日志打印，true关闭日志打印

	public static final int log_level1 = 1;
	public static final int log_level2 = 2;
	public static final int log_level3 = 3;
	public static final int log_level4 = 4;

	public static final int LEVEL_1 = 1; // ERROR日志
	public static final int LEVEL_2 = 2; // INFO日志
	public static final int LEVEL_3 = 3; // DEBUG日志

	public static final int log_level_default = log_level4;

	private static final int[] log_level = {1, 2, 3, 4};

	private static final String[] log_tag = {"SymbolPopupWindow.TAG",
			"SymbolAdapter.TAG", "KeyboardAnimation.TAG"};
	// private static final String[]
	// log_tag={SymbolPopupWindow.TAG,SymbolAdapter.TAG,
	// KeyboardAnimation.TAG};

	public static final int log_level_disable = 0xff;
	private static final int LOGE = 0;
	private static final int LOGI = 1;

	public static void i(String msg) {
		i(TAG, msg, log_level_default);
	}

	public static void i(String msg, int level) {
		i(TAG, msg, level);
	}

	public static void i(String TAG, String msg, int level) {
		log(TAG, msg, "", level, LOGI);
	}

	public static void e(String error) {
		log(TAG, "", error, log_level1, LOGE);
	}

	public static void e(String TAG, String msg, String error) {
		log(TAG, msg, error, log_level1, LOGE);
	}
	public static final boolean DEGUG_MODE = false;
	public static void log(String TAG, String msg, String error, int level,
			int type) {
		if (debug) {
			for (int i = 0; i < log_level.length; i++) {
				if (level == log_level[i]) {
					String out_tag = TAG;
					for (int j = 0; j < log_tag.length; j++) {
						if (TAG.equals(log_tag[j])) {
							out_tag = LogUtil.TAG;
							msg = TAG + " " + msg;
							break;

						}
					}
					if (type == LOGE) {
						LogUtil.e(out_tag, msg);
						LogUtil.e(out_tag, error);
					} else {
						LogUtil.i(out_tag, msg);
					}
					break;
				}
			}
		}

	}

	/**
	 * 只有在传入的debug =true & LogUtil.DEBUG_MODE 为true 的情况下才会打印LOG
	 * 
	 * @param debug
	 *            :true ,
	 * @param tag
	 * @param info
	 */
	public static void i(boolean debug, String tag, String info) {
		if (debug) {
			i(tag, info);
		}
	}

	/**
	 * 只有在传入的debug =true & LogUtil.DEBUG_MODE 为true 的情况下才会打印LOG
	 * 
	 * @param debug
	 *            :true ,
	 * @param tag
	 * @param info
	 */
	public static void e(boolean debug, String tag, String info) {
		if (debug) {
			e(tag, info);
		}
	}

	/**
	 * 只有在传入的debug =true & LogUtil.DEBUG_MODE 为true 的情况下才会打印LOG
	 * 
	 * @param debug
	 *            :true ,
	 * @param tag
	 * @param info
	 */
	public static void d(boolean debug, String tag, String info) {
		if (debug) {
			d(tag, info);
		}
	}
	public static void i(String tag, String value) {
		System.out.println(value);
	}
	public static void d(String tag, String value) {
		i(tag, value);
	}
	public static void e(String tag, String value) {
		System.err.println(value);
	}
}
