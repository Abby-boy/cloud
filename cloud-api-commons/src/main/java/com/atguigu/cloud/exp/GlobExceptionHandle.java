package com.atguigu.cloud.exp;


import com.atguigu.cloud.resp.R;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 全局异常处理
@Slf4j
@ResponseBody
public class GlobExceptionHandle {


    // 处理参数异常
   /* @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 获取到绑定结果对象
        BindingResult result = e.getBindingResult();
        HashMap<String, String> errorMap = new HashMap<>();
        for( FieldError fieldError: result.getFieldErrors() ){
            //1.获取到属性名
            String name = fieldError.getField();
            //2.获取到错误提示
            String message = fieldError.getDefaultMessage();
            errorMap.put(name,message);
            //System.out.println("属性:"+name + " 错误提示:"+message );
        }
        //System.out.println("全局异常处理器：参数校验异常"+e.getClass());
        return R.fail(String.valueOf(401),"参数校验异常");
    }*/


    // ArithmeticException.class 表示只处理算术异常
    /*@ExceptionHandler(ArithmeticException.class)
    public R handleArithmeticException(ArithmeticException e) {
        System.out.println("全局异常处理器：算术异常");
        return R.fail(String.valueOf(401),e.getMessage());
    }*/

    // spring 对异常兜底处理
   /* @ExceptionHandler(Throwable.class)
    public R error(Throwable e){
        System.out.println("全局异常处理器：运行时异常"+e.getClass());
        return R.fail(String.valueOf(555),e.getMessage());
    }*/


    /**
     * 自定义业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> exception(Exception e){
        System.out.println("全局异常处理");
        log.error("全局异常处理",e.getMessage(),e);
        return R.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
    }

}
