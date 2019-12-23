package responsetimelogging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            filterChain.doFilter(request, response);
        } finally {
            stopWatch.stop();
            long elapsedTime = stopWatch.getTotalTimeMillis();
            log.info("[{} {}] response time is [{}] ms",
                     request.getMethod(),
                     request.getRequestURI(),
                     elapsedTime);
        }
    }
}
