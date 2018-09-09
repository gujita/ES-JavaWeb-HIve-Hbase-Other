package ipl.sso.enums;

/**
 * <p>Descirption:</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.sso.enums
 * @date 2018/3/20 10:49
 * @since api1.0
 */
public enum UserValidatiEnum {

    USER_NAME("该用户名", 1),
    USER_PHONE("该用户手机号", 2),
    USER_EMAIL("该用户邮箱", 3);

    private int type;
    private String desc;

    UserValidatiEnum(String desc, int type) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
