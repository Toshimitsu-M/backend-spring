package com.example.config;

import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseTokenFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String idToken = authHeader.substring(7);
      try {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        // 必要ならユーザー情報をリクエスト属性に追加
        request.setAttribute("firebaseUser", decodedToken);
        filterChain.doFilter(request, response);
      } catch (FirebaseAuthException e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Firebase token");
      }
    } else {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Authorization header");
    }
  }
}
