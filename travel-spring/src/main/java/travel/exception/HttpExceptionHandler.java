package travel.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import travel.base.utils.Result;

@Slf4j
@RestControllerAdvice
public class HttpExceptionHandler {

    public HttpExceptionHandler() {
    }

    //自定义异常
    @ExceptionHandler({HttpException.class})
    public Result httpException(HttpException e) {
        return new Result().error(e.getMsg(), e.getCode(), e.getData());
    }

    /*//404
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return new Result().error("路径不存在，请检查路径是否正确", HttpStatus.NOT_FOUND.value());
    }

    //参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return new Result().error(e.getMessage(), HttpStatus.PRECONDITION_FAILED.value());
    }

    //未知异常
    @ExceptionHandler({Exception.class})
    public Result exception(Exception e) {
        return new Result().error(e.getMessage());
    }

    //无权限异常
    @ExceptionHandler({UnauthorizedException.class})
    public Result unauthorizedException(HttpException e) {
        return new Result().error("无权限", HttpStatus.UNAUTHORIZED.value());
    }

    //权限认证失败异常
    @ExceptionHandler({AuthorizationException.class})
    public Result authorizationException(HttpException e) {
        return new Result().error("权限认证失败", HttpStatus.FORBIDDEN.value());
    }*/
}
