package daily.julizzzz.info.info.provider;

import com.alibaba.fastjson.JSON;
import daily.julizzzz.info.info.dto.AccessTokenDTO;
import daily.julizzzz.info.info.dto.GithubUser;
import org.springframework.stereotype.Component;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            String url = "https://github.com/login/oauth/access_token";
            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
            System.out.println(body.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            String[] split = string.split("&");
            String tokenStr = split[0];
            String token = tokenStr.split("=")[1];
            System.out.println(token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
            return null;

    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.github.com/user?access_token="+accessToken;
        Request request = new Request.Builder()
                    .url(url)
                    .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
