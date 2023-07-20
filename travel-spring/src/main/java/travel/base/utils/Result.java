package travel.base.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private Integer code = HttpStatus.OK.value();

    /**
     * 消息内容
     */
    private String msg = "success";

    /**
     * 响应数据
     */
    private Object data;

    public Result() {
    }

    public Result success(Object data) {
        this.data = data;
        return this;
    }

    public Result error() {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return this;
    }

    public Result error(String msg) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.msg = msg;
        return this;
    }

    public Result error(String msg, Integer code) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result error(String msg, Integer code, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }
}