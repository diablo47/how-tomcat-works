package chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleSocket {

	public static void main(String[] args) throws UnknownHostException,
			IOException, InterruptedException {

		Socket socket = new Socket("127.0.0.1", 8080);
		OutputStream os = socket.getOutputStream();
		boolean autoFlush = true;
		PrintWriter out = new PrintWriter(socket.getOutputStream(), autoFlush);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		// send an HTTP request to the web server
		out.println("GET /index.html HTTP/1.1");
		out.println("Host: localhost:8080");
		out.println("Connection: Close");
		out.println();

		// read the response
		boolean loop = true;
		StringBuffer sb = new StringBuffer(8096);
		while (loop) {
			if (in.ready()) {
				int i = 0;
				while (i != -1) {
					i = in.read();
					sb.append((char) i);
				}
				loop = false;
			}
			Thread.currentThread().sleep(50);
		}

		// display the response to the out console
		System.out.println(sb.toString());
		socket.close();
	}
}
