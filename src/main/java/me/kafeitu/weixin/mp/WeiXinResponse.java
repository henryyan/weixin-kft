package me.kafeitu.weixin.mp;

public class WeiXinResponse {
    private String Ret;
    private String ErrMsg;
    private String ShowVerifyCode;
    private String ErrCode;

    public String getRet() {
        return Ret;
    }

    public void setRet(String ret) {
        Ret = ret;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    public String getShowVerifyCode() {
        return ShowVerifyCode;
    }

    public void setShowVerifyCode(String showVerifyCode) {
        ShowVerifyCode = showVerifyCode;
    }

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }


}