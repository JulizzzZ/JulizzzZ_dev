package daily.julizzzz.info.info.controller;

import daily.julizzzz.info.info.dto.AccessTokenDTO;
import daily.julizzzz.info.info.dto.GithubUser;
import daily.julizzzz.info.info.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessToken = new AccessTokenDTO();
        accessToken.setCode(code);
        accessToken.setRedirect_uri("http://localhost:8887/callback");
        accessToken.setState(state);
        accessToken.setClient_id("f6f1409bc5982be0dda6");
        accessToken.setClient_secret("5ca1f274252e1c800a898444f651ecb34e2770d5");
        String token = gitHubProvider.getAccessToken(accessToken);
        GithubUser user = gitHubProvider.getUser(token);
        System.out.println(user.getName());
//        System.out.println(user.getId());
        return "greeting";
    }
}
