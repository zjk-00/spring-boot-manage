package com.common.constant;

/**
 * 常量
 * 
 * @author zjk
 * 2018年3月1日 下午4:43:20
 */
public class Constant {

	/**
	 * 菜单类型
	 * 
	 * @author zjk
	 * 2018年10月19日 上午10:55:07
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author zjk
     * 2018年10月19日 上午10:55:28
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
}
