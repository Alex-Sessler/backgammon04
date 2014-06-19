package actions;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.SimpleResult;
import service.UserService;
import backgammon04.model.User;

@Service
public class BasicAuthAction extends Action<BasicAuthAction> {

	private static final String AUTHORIZATION = "Authorization";
	private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
	private static final String REALM = "Basic realm=\"SeSe Backgammon\"";

	@Autowired
	private UserService userService;

	@Override
	public Promise<SimpleResult> call(Http.Context context) throws Throwable {

		String authHeader = context.request().getHeader(AUTHORIZATION);
		if (authHeader == null) {
			context.response().setHeader(WWW_AUTHENTICATE, REALM);
			return F.Promise.pure((SimpleResult) unauthorized());
		}

		String auth = authHeader.substring(6);
		byte[] decodedAuth = new Base64().decode(auth.getBytes());
		String[] credString = new String(decodedAuth, "UTF-8").split(":");

		if (credString == null || credString.length != 2) {
			return F.Promise.pure((SimpleResult) unauthorized());
		}

		String username = credString[0];
		String password = credString[1];

		User authUser = userService.authenticate(username, password);

		if (authUser != null) {
			context.session().clear();
			context.session().put("username", authUser.getUsername());
		}
		return ((authUser == null) ? F.Promise
				.pure((SimpleResult) unauthorized()) : delegate.call(context));
	}
}