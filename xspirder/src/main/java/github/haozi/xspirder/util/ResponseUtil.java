package github.haozi.xspirder.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-17 19:15
 */
public class ResponseUtil {
    public static  <T> ResponseEntity<T> fail(T body) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(body);
    }
}
