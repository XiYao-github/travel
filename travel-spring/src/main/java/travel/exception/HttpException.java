package travel.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class HttpException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * 消息内容
     */
    private String msg = "error";

    /**
     * 响应数据
     */
    private Object data;

    public HttpException() {
    }

    public HttpException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public HttpException(String msg, Integer code) {
        this.code = code;
        this.msg = msg;
    }

    public HttpException(String msg, Integer code, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public HttpException(Throwable e, String msg) {
        super(msg, e);
        this.msg = msg;
    }
}