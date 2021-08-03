package eu.assignment.project.erate.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.assignment.project.erate.common.gen.model.Error;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {


	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
		Error errorResponse = new Error().code(403).message("Request Access denied, validate the roles.");
		httpServletResponse.setHeader("Content-Type", "application/json");
		httpServletResponse.setStatus(403);

		OutputStream out = httpServletResponse.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, errorResponse);
		out.flush();
	}
}