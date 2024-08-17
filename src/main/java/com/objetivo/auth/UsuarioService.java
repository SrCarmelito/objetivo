package com.objetivo.auth;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
@Slf4j
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenService tokenService;
    private int EXPIRATION_TIME_LOGIN = 120;
    private int EXPIRATION_TIME_NEW_PASSWORD = 5;

    public UsuarioService(UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, EmailService emailService, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    public void newUser(UsuarioDTO usuarioDTO) {
        validaUsuario(usuarioDTO);
        validaSenha(usuarioDTO.getSenha(), usuarioDTO.getSenhaConfirmacao());
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuarioDTO.getNome());
        novoUsuario.setEmail(usuarioDTO.getEmail());
        novoUsuario.setLogin(usuarioDTO.getLogin());
        novoUsuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        usuarioRepository.save(novoUsuario);
    }

    public void validaUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByLogin(usuarioDTO.getLogin()) != null) {
            throw new IllegalArgumentException("Usuário já existe, tente novamente!");
        }

        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado, tente novamente!");
        }

        Pattern patternEmail = Pattern.compile("^(.+)@(\\S+)$");
        Matcher matcherEmail = patternEmail.matcher(usuarioDTO.getEmail());
        if (!matcherEmail.find()) {
            throw new IllegalArgumentException("Não é um E-mail Válido!");
        }
    }

    public void validaSenha(String senha, String senhaConfirmacao) {
        Pattern patternSenha = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{6,150}$");
        Matcher matcherSenha = patternSenha.matcher(senha);
        if (!matcherSenha.find()) {
            throw new IllegalArgumentException
                ("Senha deve conter entre 6 e 150 números e letras e ao menos 1 letra maiúscula, 1 minúscula e 1 número!");
        }

        if (!senha.equals(senhaConfirmacao)){
            throw new IllegalArgumentException("Senha e Senha de Confirmação não Conferem, tente novamente!");
        }
    }

    public void resetPassword(HttpServletRequest request, String email)  {
        String userMail = email.replace("{\"email\":\"", "").replace("\"}", "");

        Usuario usuario = usuarioRepository.findByEmail(userMail).orElseThrow(
                () -> new IllegalArgumentException("Não é um e-mail válido"));

        String token = tokenService.gerarToken(usuario, EXPIRATION_TIME_NEW_PASSWORD);
        usuario.setResetToken(token);
        usuarioRepository.saveAndFlush(usuario);

        String appUrl = "http://127.0.0.1:5500/usuario/confirm-new-password.html?token=" + token;

        emailService.enviarEmailTexto(userMail, "Plataforma Spring App", "Foi solicitado a troca de sua senha " + " \n" + appUrl);
    }

    public void confirmResetPassword(NewPasswordDTO newPasswordDTO) {
        try {
            tokenService.getSubject(newPasswordDTO.getToken());
        } catch (Exception e) {
            throw new IllegalArgumentException("Token Inválido ou expirado, tente novamente!");
        }

        Usuario usuario = usuarioRepository.findByResetToken(newPasswordDTO.getToken()).orElseThrow(
                () -> new IllegalArgumentException("Token Não Encontrado, tente novamente!"));

        validaSenha(newPasswordDTO.getSenha(), newPasswordDTO.getSenhaConfirmacao());
        usuario.setSenha(passwordEncoder.encode(newPasswordDTO.getSenha()));
        usuario.setResetToken(null);
        usuarioRepository.save(usuario);
    }

    public String gerarToken(LoginDTO login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.getLogin(), login.getSenha());

        Authentication authentication = this.authenticationManager
                .authenticate(usernamePasswordAuthenticationToken);

        var usuario = (Usuario) authentication.getPrincipal();

        return tokenService.gerarToken(usuario, EXPIRATION_TIME_LOGIN);
    }

}
