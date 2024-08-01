import com.jenson.tool.http.auth.Http;

public class HttpTest {
    public static void main(String[] args) {
        String body = new Http().post("http://118.31.247.234:8888/api/user/page_all")
                .addBody("page",1)
                .execute().body();
        System.out.println(body);
    }
}
