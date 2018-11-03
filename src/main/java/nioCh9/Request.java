package nioCh9;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: chris_cai@enable-ets.com
 * @Since: 2018年11月03日
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 5973933584052511436L;

    public Request() {
    }

    public Request(String url) {
        Url = url;
    }

    private String Url;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
