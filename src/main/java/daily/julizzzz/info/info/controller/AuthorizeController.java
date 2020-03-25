package daily.julizzzz.info.info.controller;

import daily.julizzzz.info.info.dto.AccessTokenDTO;
import daily.julizzzz.info.info.dto.GithubUser;
import daily.julizzzz.info.info.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessToken = new AccessTokenDTO();
        accessToken.setCode(code);
        accessToken.setRedirect_uri(redirectUri);
        accessToken.setState(state);
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        String token = gitHubProvider.getAccessToken(accessToken);
        GithubUser user = gitHubProvider.getUser(token);
        System.out.println(user.getName());
//        System.out.println(user.getId());
        return "greeting";
    }
}
