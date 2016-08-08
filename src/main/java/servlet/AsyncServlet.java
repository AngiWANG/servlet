package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "asyncServlet", urlPatterns = "/asyncServlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("receive a AsyncServlet request.");
		final AsyncContext asyncContext = req.startAsync();
		asyncContext.setTimeout(30 * 1000);
		asyncContext.addListener(new AsyncListener() {
			public void onComplete(AsyncEvent event) throws IOException {
				System.out.println("AsyncServlet complete.");
			}

			public void onTimeout(AsyncEvent event) throws IOException {
				System.out.println("AsyncServlet timeout.");
			}

			public void onError(AsyncEvent event) throws IOException {
				System.out.println("AsyncServlet error.");
			}

			public void onStartAsync(AsyncEvent event) throws IOException {
				System.out.println("AsyncServlet Start.");
			}
		});
		asyncContext.start(new Runnable() {
			public void run() {
				PrintWriter printWriter = null;
				try {
					printWriter = asyncContext.getResponse().getWriter();
				} catch (IOException e) {
					e.printStackTrace();
				}
				printWriter.println("Hello, AsyncServlet.");
				printWriter.flush();
				printWriter.close();
				asyncContext.complete();
			}
		});
	}
}
