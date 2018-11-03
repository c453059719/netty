package nioCh9;

import java.io.Serializable;

/**
 * @Description: TODO
 * @Author: chris_cai@enable-ets.com
 * @Since: 2018年11月03日
 */
public class Response implements Serializable {
    private static final long serialVersionUID = -7321590708126852883L;

    public Response() {
    }

    public Response(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    private Integer age;

    private String name;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
