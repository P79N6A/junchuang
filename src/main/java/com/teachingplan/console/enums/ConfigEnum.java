package com.teachingplan.console.enums;

/**
 * 枚举类型
 * Created by v_yanfzhang on 2017/4/27.
 */
public interface ConfigEnum {

    /**
     * 消费类型
     */
    public enum ConsumeEnum {

        CONSUME_A("网购"), CONSUME_B("超市"), CONSUME_C("电子产品"), CONSUME_D("衣服"),
        CONSUME_E("首饰"), CONSUME_F("旅游"), CONSUME_G("年货"), CONSUME_H("房租"),
        CONSUME_I("贷款"),CONSUME_J("通讯"), CONSUME_K("其他");
        private final String type;

        private ConsumeEnum(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum ExpenseEnum
    {
        Expense_A("工资"),Expense_B("奖金"),Expense_C("借贷");
        private final String type;

        private ExpenseEnum(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
