package io.github.xpeteliu.constant;

public class CommonConstant {

    public static final String UTF8 = "UTF-8";

    public static final String GBK = "GBK";

    public static final String HTTP = "http://";

    public static final String HTTPS = "https://";

    public static final Integer SUCCESS = 200;

    public static final Integer FAILURE = 500;

    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    public static final long CAPTCHA_EXPIRATION = 2;

    public static final Integer HTTP_PORT = 80;

    public static final Integer HTTPS_PORT = 443;


    /**
     * 数值类型精度
     */
    public static final int SCALE = 8;

    public static final int AGENT_TYPE = 2;

    public static final String BCRYPT_SALT = "!waihui!_+waihui+-";

    /**
     * 首页Banner图
     */
    public static final String CONFIG_WEB_BANNER = "WEB_BANNER";

    public static final String TOKEN = "token";

    /*********************************** MATCH ********************************/
    public static final String MATCH_RESULT_DEAL_ORDER = "dealOrder";
    public static final String MATCH_RESULT_BUY_ORDER = "buyOrder";
    public static final String MATCH_RESULT_SELL_ORDER = "sellOrder";
    public static final String MATCH_RESULT_NEW_BUY_LOCK = "buyLock";
    public static final String MATCH_RESULT_NEW_SELL_LOCK = "sellLock";
    public static final String MATCH_RESULT_PART_MATCH = "partMatch";
    /*********************************** MATCH ********************************/


    /*********************************** CONFIG TABLE CODE ********************************/
    public static final String CONFIG_TYPE_SYSTEM = "SYSTEM";
    public static final String CONFIG_TYPE_CNY = "CNY";
    public static final String CONFIG_TYPE_SMS = "SMS";
    public static final String CONFIG_TYPE_REGISTER = "REGISTER_REWARD";
    public static final String CONFIG_TYPE_REWARD = "INVITE_REWARD";

    /**
     * 最小提现额度（USDT）
     */
    public static final String CONFIG_WITHDRAW_MIN_AMOUNT = "WITHDRAW_MIN_AMOUNT";

    /**
     * 最大提现额度（USDT）
     */
    public static final String CONFIG_WITHDRAW_MAX_AMOUNT = "WITHDRAW_MAX_AMOUNT";

    /**
     * 最小取现手续费（USDT）
     */
    public static final String CONFIG_WITHDRAW_MIN_POUNDAGE = "WITHDRAW_MIN_POUNDAGE";

    /**
     * 取现手续费率（USDT）
     */
    public static final String CONFIG_WITHDRAW_POUNDAGE_RATE = "WITHDRAW_POUNDAGE_RATE";

    /**
     * 取现基数（USDT），取现值必须是基数的倍数，基数如果是100，那么取现值只能是100的倍数，例如：200, 300等
     */
    public static final String CONFIG_WITHDRAW_BASEAMOUNT = "CONFIG_WITHDRAW_BASEAMOUNT";

    /**
     * 每日最大提现额（USDT）
     */
    public static final String CONFIG_WITHDRAW_DAY_MAX_AMOUNT = "WITHDRAW_DAY_MAX_AMOUNT";

    /**
     * 提现状态（USDT）
     */
    public static final String CONFIG_WITHDRAW_STATUS = "WITHDRAW_STATUS";

    /**
     * 币币交易状态
     */
    public static final String CONFIG_TRADE_STATUS = "TRADE_STATUS";

    /**
     * 人民币充值USDT换算费率
     */
    public static final String CONFIG_CNY2USDT = "CNY2USDT";

    /**
     * 人民币提现USDT换算费率
     */
    public static final String CONFIG_USDT2CNY = "USDT2CNY";

    /**
     * 提现审核级数
     */
    public static final String CONFIG_CASH_WITHDRAW_AUDIT_STEPS = "CASH_WITHDRAW_AUDIT_STEPS";

    /**
     * 充值审核级数
     */
    public static final String CONFIG_CASH_RECHARGE_AUDIT_STEPS = "CASH_RECHARGE_AUDIT_STEPS";

    /**
     * 提币审核级数
     */
    public static final String CONFIG_COIN_WITHDRAW_AUDIT_STEPS = "COIN_WITHDRAW_AUDIT_STEPS";

    public static final String C2C_ADMIN_USER_ID = "C2C_ADMIN_USER_ID";
    /*********************************** CONFIG TABLE CODE ********************************/


    /************************************* CACHE KEY ************************************/
    /**
     * 交易区域
     */
    public static final String CACHE_KEY_TRADE_AREA = "tradeArea:";

    /**
     * 交易区域
     */
    public static final String CACHE_KEY_TRADE_AREAS = "tradeAreas:";

    /**
     * 币种
     */
    public static final String CACHE_KEY_COIN = "coin:";

    /**
     * 交易对列表
     */
    public static final String CACHE_KEY_MARKETS = "markets:";

    /**
     * 交易对列表
     */
    public static final String CACHE_KEY_AREA_MARKETS = "areaMarkets:";

    /**
     * 交易对
     */
    public static final String CACHE_KEY_MARKET = "market:";

    /**
     * 基础配置
     */
    public static final String CACHE_KEY_CONFIG = "config:";

    /**
     * 系统配置列表
     */
    public static final String CACHE_KEY_CONFIG_LIST = "config_list:";

    /**
     * 管理员
     */
    public static final String CACHE_KEY_ADMIN_USER = "user_admin";
    public static final String CACHE_KEY_C2C_ADMIN_USER = "user_c2c_admin";
    public static final String CACHE_KEY_AGENT_ADMIN_USER = "user_agent_admin";
    /************************************* CACHE KEY ************************************/


    /**
     * 币币市场行情Socket推送
     */
    public static final String CH_MARKETS_TICKER = "market.ticker";
    /**
     * 未成交委托订阅通道
     */
    public static final String CH_ORDER_PENDING = "order.pending.update";
    /**
     * 历史委托订阅通道
     */
    public static final String CH_ORDER_FINISHED = "order.finished.update";
    /**
     * 持仓汇总订阅通道
     */
    public static final String CH_POSITION_SUMMARY_UPDATE = "position.summary.update";
    /**
     * 今日平仓订阅通道
     */
    public static final String CH_CLOSEPOSITION_ORDERS_UPDATE = "closeposition.orders.update";
    /**
     * 今日委托订阅通道
     */
    public static final String CH_ENTRUST_ORDERS_UPDATE = "entrust.orders.update";
    /**
     * 今日成交订阅通道
     */
    public static final String CH_TURNOVER_ORDERS_UPDATE = "turnover.orders.update";


    /************************************* REDIS KEY ************************************/
    /**
     * 验证码redis存储Key
     */
    public static final String REDIS_KEY_CAPTCHA_KEY = "CAPTCHA:";

    /**
     * 短信验证码redis存储Key
     */
    public static final String REDIS_KEY_SMS_CODE_KEY = "SMSCODE:";

    /**
     * 登录设备存储key
     */
    public static final String REDIS_KEY_DEVICES_KEY = "DEVICES";

    /**
     * 最大缓存数据量
     */
    public static final long REDIS_MAX_CACHE_KLINE_SIZE = 10000L;

    /**
     * 币币交易K线 redis存储Key
     */
    public static final String REDIS_KEY_TRADE_KLINE = "TRADE_KLINE:";


    /**
     * 币币交易撮合队列 redis存储Key
     */
    public static final String REDIS_KEY_TRADE_MATCH = "TRADE_MATCH:";

    /**
     * 币币交易对
     */
    public static final String REDIS_KEY_TRADE_MARKET = "TRADE_MARKET";

    /**
     * 法币充值审核锁
     */
    public static final String REDIS_KEY_CASH_RECHARGE_AUDIT_LOCK = "CASH_RECHARGE_AUDIT_LOCK:";

    /**
     * 法币提现审核锁
     */
    public static final String REDIS_KEY_CASH_WITHDRAW_AUDIT_LOCK = "CASH_WITHDRAW_AUDIT_LOCK:";

    /**
     * 数字货币提现审核锁
     */
    public static final String REDIS_KEY_COIN_WITHDRAW_AUDIT_LOCK = "COIN_WITHDRAW_AUDIT_LOCK:";

    /**
     * 币币交易撤单锁
     */
    public static final String REDIS_KEY_TRADE_ORDER_CANCEL_LOCK = "TRADE_ORDER_CANCEL_LOCK:";

    /**
     * 币币交易撮合锁
     */
    public static final String REDIS_KEY_TRADE_ORDER_MATCH_LOCK = "TRADE_ORDER_MATCH_LOCK:";

    /**
     * 创新交易资金账户锁
     */
    public static final String REDIS_KEY_TRADE_ACCOUNT_LOCK = "TRADE_ACCOUNT_LOCK:";
    /************************************* REDIS KEY ************************************/


    /************OAUTH*********************/
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER = "Bearer ";
    /************OAUTH*********************/

    /************STREAM*********************/
    public static final String CHANNEL_SENDTO_USER = "user";
    public static final String CHANNEL_SENDTO_GROUP = "group";
    public static final String CHANNEL_TICKER_UPDATE = "ticker";




    /**
     * 钱包币
     */
    public static final String COIN_TYPE_QBB = "qbb";
    /**
     * 认购币
     */
    public static final String COIN_TYPE_RGB = "rgb";
}
