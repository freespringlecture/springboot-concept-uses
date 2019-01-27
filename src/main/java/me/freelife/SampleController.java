package me.freelife;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @GetMapping("/hello")
    public String hello() {
        throw new SampleException();
    }

    /*
    // AppError: App에서 만든 커스텀한 에러정보를 담고 있는 클래스가 있다면
    @ExceptionHandler(SampleException.class)
    //메서드 파라메터로 해당하는 Exception 정보를 받아 올 수 있음
    public @ResponseBody AppError sampleError(SampleException e) {
        AppError appError = new AppError();
        appError.setMessage("error.app.key");
        appError.setReason("IDK IDK IDK");
        return appError;
    }
    */
}
