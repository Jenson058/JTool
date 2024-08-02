import com.google.gson.reflect.TypeToken;
import com.jenson.tool.http.auth.Http;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

public class HttpTest {
    public static void main(String[] args) {
        Result<List<User>> result = new Http()
                .post("http://118.31.247.234:8888/api/user/list_all")
                .addBody("page", 1)
                .execute()
                .body(new TypeToken<Result<List<User>>>() {});
    }
}

@ToString
@Data
class Result<T> {
    Integer code;
    String msg;
    T content;
}

@Data
class User {
    Integer id;
    String name;
    String code;
    String nickname;
    Date birthday;
    Integer age;
    String sex;
    String avatar;
    String phone;
    String email;
    List<Integer> roleList;
    List<String> authList;
}


