package dev.bura.jetty.df.web.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.ee10.servlets.DoSFilter;
import org.springframework.http.MediaType;

/**
 * @author a.bloshchetsov
 */
@Slf4j
public class CustomDoSFilter extends DoSFilter {

    private static ByteArrayOutputStream createTimeoutResponse() {
        final var responseBody = "{\"result\":99,\"message\":\"Internal processing timeout\"}";
        final var response = new ByteArrayOutputStream(responseBody.length());
        response.writeBytes(responseBody.getBytes(StandardCharsets.UTF_8));

        return response;
    }

    private static final ByteArrayOutputStream TIMEOUT_RESPONSE = createTimeoutResponse();

    private static void writeTimeoutResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setContentLength(TIMEOUT_RESPONSE.size());
        final var out = response.getOutputStream();
        TIMEOUT_RESPONSE.writeTo(out);
        out.flush();
    }

    @Override
    protected void onRequestTimeout(HttpServletRequest request, HttpServletResponse response, Thread handlingThread) {
        handlingThread.interrupt();
        try {
            writeTimeoutResponse(response);
        } catch (IOException e) {
            log.error("Error on write response", e);
        }
    }



}
