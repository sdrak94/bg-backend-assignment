package bg.assignment.bg.backend.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTSecurityFilter extends OncePerRequestFilter
{

	@Autowired
	private JWTUtil util;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		// Reading Token from Authorization Header
		String token = request.getHeader("Authorization");
		if (token != null) 
		{
			try
			{
				final String username = util.getSubject(token);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
				{
					UserDetails user = userDetailsService.loadUserByUsername(username);
					boolean isValid = util.isValidToken(token, user.getUsername());
					if (user != null && isValid) 
					{
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);

					}
				}
			}
			catch (Exception e) //supress this exception
			{
				System.out.println("Unauthorized JWT was denied access.");
			}
		}
		filterChain.doFilter(request, response);
	}

}