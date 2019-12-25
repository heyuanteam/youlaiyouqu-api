package spring.enums;

import lombok.Getter;

/**用户中心错误码描述
 */
public enum UserErrorCodeEnum {
    SUCCESS(10000,"成功"),
    MOBILE_UNREGISTER(10001,"用户未注册"),
    RECHARGE_SCORE_FINISH(10002,"本次充值已完成"),
    RECHARGE_SCORE_PARAMS_FAIL(10003,"必填参数不能为空"),
    RECHARGE_SCORE_SIGN_FAIL(10004,"签名失败"),

    RECHARGE_SCORE_SIGN_ERROR(11111,"信息已失效，请重新登录"),


    FAIL(99999,"系统繁忙，请稍后重试"),
    PARAMS_FAIL(99998,"参数注入不符合要求"),// 请求类中的值不能为空
    LOGIC_FAIL(99997,"业务异常"),



    DATA_INSERT_FAIL(11001,"添加数据失败"),
    DATA_DELETE_FAIL(11002,"删除数据失败"),
    DATA_UPDATE_FAIL(11003,"更新数据失败"),
    DATA_SELECT_FAIL(11004,"查询数据失败"),

    MESSAGE_SEND_FAIL(30001,"发送短信失败"),

    CODEFAIL(20001,"验证码错误"),
    SEND_MESSAGE_FAIL(20002,"发送短信验证码失败"),
    SIGN_FAIL(20003,"签名校验失败"),
    SEND_MESSAGE_ISOVERTIME(20004,"已经发送过验证码"),
    VERIFICATIONCODE_ISOVERTIME(20005,"验证码过期"),
    LOGIN_FAIL(20006,"账号/密码错误"),
    LOGIN_IN_OTHER(20007,"该账号已在其他设备上登录"),
    MEMBER_STATUS_EXCEPTION(20012,"会员账号状态异常"),
    PASSWORD_WRONG(20013,"密码错误"),
    UPLOAD_FAIL(20014,"上传失败"),
    DISMISSLOGIN_FAIL(20015,"该用户没有设置免登陆功能"),
    LOGINTOKEN_OVERTIME(20016,"免登陆已过期"),
    LOGINTOKEN_FAIL(20015,"token错误/设备id不对"),
    MOBLIE_REPEAT(20016,"该手机号码已经注册过了"),
    HAS_NOT_LAST_VERSION(20017,"没有最新版本app"),

    //用户相关信息
    USER_LOGIN_ACCOUNT_NON_EXIST(60001, "该账号不存在"),
    USER_LOGIN_ACCOUNT_DISABLE(60002, "该账号被禁用"),
    USER_LOGIN_FAIL(60003, "用户名或密码错误"),
    USER_LOGIN_PASSWORD(60031, "密码不能为空"),
    USER_LOGIN_PASSWORD_NULL(60088, "未设置密码,请使用验证码登录"),

    USER_LOGIN_OVERDUE(60004, "登录过期，请重新登录"),
    USER_LOGIN_OFFLINE(60005, "您的账号在别的地方登录，您被迫下线了"),
    
    USER_UPDATE_PASSWORD_DIFF_PASSWORD(60006, "两次输入的密码不符"),
    USER_UPDATE_PASSWORD_PASSWORD_FAIL(60007, "原密码不正确"),
    USER_AGENT_EXITS(60008, "该区域已存在代理商"),
    USER_AGENT_CODE_NOEXITS(60009, "邀请码不存在"),
    USER_LOGIN_USER_TYPE_FAIL(60010, "账号类型错误"),
    USER_REGISTER_MOBILE_EXITS(60011, "该手机号已被注册"),
    USER_REGISTER_MOBILE_BANDING(60015, "该手机号已被绑定"),
    USER_REGISTER_MOBILE_TYPE_NOT(60016, "类型不存在"),
    USER_REGISTER_CODE_FAIL(60012, "验证码错误"),
    USER_LOGIN_CODE_FAIL(60013, "验证码错误"),
    USER_AGENT_AUDIT_MODIFIED(60014, "审核过程中，代理商数据已经被修改"),
    USER_REGISTER_SUPPLIER_EXITS(60015, "该手机号已注册成商家"),
    USER_REGISTER_BACK_EXITS(60016, "该手机号已注册成后台人员"),
    USER_REGISTER_AGENT_EXITS(60017, "该手机号已注册成代理商"),
    USER_FIND_PASSWORD_SUPPLIER_EXITS(60018, "该账号为商家"),
    USER_FIND_PASSWORD_BACK_EXITS(60019, "该账号为后台人员"),
    USER_FIND_PASSWORD_AGENT_EXITS(60020, "该账号为代理商"),
    USER_ADD_EXITS(60021, "已存在该账号"),
    USER_NO_PERMISSION(60022, "权限不足"),
    USER_AGENT_CONTRACT_EXPIRED(60023, "合同已过期"),
    USER_NO_LOGIN(60024, "请先登录"),
    USER_NO_ACCESS(60025, "没访问权限"),
    USER_TOKEN_CACHE_NULL(60029,"订单token缓存为空"),
    SUPPLIER_CERTIFICATIONS_NOT_NULL(60030,"商家相关资质不能为空"),
    SUPPLIER_TAG_NOT_NULL(60031,"商家类目不能为空"),
    SUPPLIER_BRAND_INFO_NULL(60032,"授权商标信息为空"),
    USER_AGENT_AUDIT_NOT_EXIST(60033, "不存在该代理商"),
    USER_AGENT_AUDIT_TYPE_ERROR(60034, "代理商审核类型不匹配"),
    USER_AGENT_AUDIT_STATUS_ERROR(60035, "代理商审核状态不对"),
    USER_AGENT_AREA_CODE_ERROR(60036, "所在地区不能为空"),
    USER_AGENT_LEGAL_IDCARD_COPIES_FRONT_ERROR(60037, "法人身份证复印件正面不能为空"),
    USER_AGENT_LEGAL_IDCARD_COPIES_REVERSE_ERROR(60038, "法人身份证复印件反面不能为空"),
    USER_AGENT_AGENT_LICENSE_ERROR(60039, "代理商营业执照不能为空"),
    USER_SIZE_IMAGE_WIDTH_LIMIT_ERROR(60040, "宽度不能大于1200px"),
    USER_SIZE_IMAGE_HEIGHT_LIMIT_ERROR(60041, "高度不能大于400px"),
    USER_SUPPLIER_EXITS(60043, "商家不存在"),
    USER_SUPPLIER_ADD_STAFF(60044, "添加员工失败"),
    USER_STAFF_MOBILE_EXITS(60045, "该手机号已被使用"),
    USER_SUPPLIER_EDIT_STAFF(60046, "修改员工失败"),
    USER_STAFF_EXITS(60047, "该员工不存在"),
    USER_STAFF_ACCOUNT_EXITS(60048, "该手机号已被使用"),
    USER_STAFF_LIST_NO_EXITS(60049, "该手机号已被使用"),
    USER_LOGIN_IP_CHANGE(60050, "登陆ip地址改变"),

    USER_THRID_PARTY_BINDED(60051, "已绑定其他账号，请解绑后操作"),



    REDIS_LOCK_FAIL(88801,"Redis锁被占用"),


    //解析ecexl数据异常
    ANALYSIS_ECEXL_FILE(99989,"ecexl解析异常");


    @Getter
    private Integer code;
    @Getter
    private String msg;

    /**
     * 构造方法
     * @param errorCode 错误码
     * @param errorDesc 错误描述
     */
    UserErrorCodeEnum(Integer errorCode, String errorDesc){
        this.code = errorCode;
        this.msg = errorDesc;
    }

    /**
     * 获取错误描述
     * @param errorCode 错误码
     * @return
     */
    public static String getDesc(Integer errorCode){
        for (UserErrorCodeEnum code : UserErrorCodeEnum.values()){
            if(code.getCode().equals(errorCode)){
                return code.msg;
            }
        }
        return Integer.toString(errorCode);
    }

}
